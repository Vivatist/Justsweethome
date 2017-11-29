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
        void onAcceptingTCPPackage(String str);
    }

    //создаем список обработчиков
    private ArrayList<ListenerOfTransport> listeners = new ArrayList<ListenerOfTransport>();

    public void addListener(ListenerOfTransport listener) {
        listeners.add(listener);
    }

    public void removeListener(ListenerOfTransport listener) {
        listeners.remove(listener);
    }

    private void fireListenersWhenAcceptingTCPPackage(String str) {
        // перебираем массив подписчиков и каждому подписчику сообщаем что произошло событие (и передаем параметр заодно)
        for(ListenerOfTransport listener : listeners) {
            listener.onAcceptingTCPPackage(str);
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
        Log.print("Создается поток для передачи пакета");
        new send().execute("!TEST PACKET! - !ТЕСТОВЫЙ ПАКЕТ!"); //отдаем тестовый пакет на передачу в поток

    }



    //создаем отдельный поток для отправки донных по сети
    private class  send extends AsyncTask <String, Integer, Boolean>{


        @Override
        protected Boolean doInBackground(String[] params) {
            try {
                while (true) {

                    String str = params[0];

                    Socket s = new Socket("89.169.58.253", 7777);
                    Log.print("------- Сокет открыт -------");
                    str = str+ "\n" + s.getInetAddress().getHostAddress() + ":" + s.getLocalPort() + "\n";
                    Log.print("К передаче подготовлен пакет: " + str);
                    s.getOutputStream().write(params[0].getBytes());
                    Log.print("Пакет отправлен" + str);
                    // читаем ответ
                    Log.print("Ждем ответ" + str);
                    byte buf[] = new byte[64 * 1024];
                    int r = s.getInputStream().read(buf);
                    String data = new String(buf, 0, r);

                    Log.print("Ответ сервера: " + data);

                    // генерируем событие и передаем через него принятую информацию
                    fireListenersWhenAcceptingTCPPackage(params[0]);

                    s.close();
                    Log.print("------- Сокет закрыт -------");
                    return true;
                }
            } catch (Exception e) {
                Log.print("Ошибка при отправке либо получении пакета: " + e);
                return false;
            }

        }



    }
}
