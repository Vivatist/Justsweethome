package com.example.andrey.justsweethome;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Andrey on 23.11.2017.
 */

class Transport {

    private String IPAdress;
    private int port;
    private String login;
    private String password;

    Transport(String _IPAdress, int _port, String _login, String _password) {

        IPAdress = _IPAdress;
        port = _port;
        login = _login;
        password = _password;

    }


    // <------ ВСТАВИТЬ СОБЫТИЯ------------

    void setIPAdress(String _IPAdress) {
        IPAdress = _IPAdress;
    }

    public String getIPAdress() {
        return IPAdress;
    }

    void setPort(int _port) {
        port = _port;
    }

    public int getPort() {
        return port;
    }

    void setLogin(String _login) {
        login = _login;
    }

    public String getLogin() {
        return login;
    }

    void setPassword(String _password) {
        password = _password;
    }

    public String getPassword() {
        return password;
    }


    boolean sendTest() {

        try {
            InetAddress ipAddress = InetAddress.getByName(IPAdress); // создаем объект который отображает вышеописанный IP-адрес.
            //System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, port); // создаем сокет используя IP-адрес и порт сервера.
            //System.out.println("Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            // Создаем поток для чтения с клавиатуры.
           // BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            //System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            //System.out.println();

            while (true) {
                line = "This line sended by client " + login; // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                //System.out.println("Sending this line to the server...");
                out.writeUTF(line); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                line = in.readUTF(); // ждем пока сервер отошлет строку текста.
               // System.out.println("The server was very polite. It sent me this : " + line);
               // System.out.println("Looks like the server is pleased with us. Go ahead and enter more lines.");
               // System.out.println();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    return true;
    }


}
