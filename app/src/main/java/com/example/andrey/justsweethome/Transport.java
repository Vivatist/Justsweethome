package com.example.andrey.justsweethome;



        import android.os.AsyncTask;
        import java.net.*;
        import java.util.Date;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.io.*;

/**
 * Created by Andrey on 23.11.2017.
 */

class Transport {


    private String IPAdressController;
    private int portController;
    private String UserLogin;
    private String UserPassword;

    Transport(String _IPAdress, int _port, String _login, String _password) {

        IPAdressController = _IPAdress;
        portController = _port;
        UserLogin = _login;
        UserPassword = _password;

    }



    //~~~~~~~~~~~~~ СОБЫТИЯ ~~~~~~~~~~~~~~~~~

    // создаем интерфейс слушателя событий
    public interface ListenerOfTransport {
        void onAcceptingTCPPackage(NetworkPackage np);
    }

    //создаем список обработчиков
    private ArrayList<ListenerOfTransport> listeners = new ArrayList<ListenerOfTransport>();

    public void addListener(ListenerOfTransport listener) {
        listeners.add(listener);
    }

    public void removeListener(ListenerOfTransport listener) {
        listeners.remove(listener);
    }

    private void fireListenersWhenAcceptingTCPPackage(NetworkPackage np) {
        // перебираем массив подписчиков и каждому подписчику сообщаем что произошло событие (и передаем параметр заодно)
        for(ListenerOfTransport listener : listeners) {
            listener.onAcceptingTCPPackage(np);
        }
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



    void setIPAdressController(String _IPAdress) {
        IPAdressController = _IPAdress;
    }

    public String getIPAdressController() {
        return IPAdressController;
    }

    void setPortController(int _port) {
        portController = _port;
    }

    public int getPortController() {
        return portController;
    }

    void setUserLogin(String _login) {
        UserLogin = _login;
    }

    public String getUserLogin() {
        return UserLogin;
    }

    void setUserPassword(String _password) {
        UserPassword = _password;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void sendTest(){
        NetworkPackage np = new NetworkPackage();
        np.UIN = 646464;
        np.responseToUIN = 111111;
        np.data = "!TEST PACKET! - !ТЕСТОВЫЙ ПАКЕТ!";
        new send().execute(np); //отдаем тестовый пакет на передачу в поток
    }



    //создаем отдельный поток для отправки донных по сети
    private class  send extends AsyncTask <NetworkPackage, Integer, Boolean>{


        @Override
        protected Boolean doInBackground(NetworkPackage[] params) {
            try {
                while (true) {

                    // String str = keyboard.readLine();
                    Date curTime = new Date();
                    SimpleDateFormat parsedDate = new SimpleDateFormat("HH:mm:ss SSSS");
                    //String str = "";
                    Socket s = new Socket("89.169.58.253", 7777);
                    //params[0] = parsedDate.format(curTime) + " -> " +params[0]+ "\n" + s.getInetAddress().getHostAddress() + ":" + s.getLocalPort() + "\n";



                    ObjectOutputStream oos = new ObjectOutputStream( s.getOutputStream() );
                    oos.writeObject(params[0]);

                    // читаем ответ
                    byte buf[] = new byte[64 * 1024];
                    int r = s.getInputStream().read(buf);
                    String data = new String(buf, 0, r);

                    // выводим ответ в консоль
                    System.out.println(data);

                    // генерируем событие и передаем через него принятую информацию
                    fireListenersWhenAcceptingTCPPackage(params[0]);

                    s.close();
                    oos.close();

                    return true;
                }
            } catch (Exception e) {
                //не забыть добавить сюда событие
                System.out.println("Ошибка в модуле передачи и приема через socket: " + e);
                return false;
            }

        }



    }
}
