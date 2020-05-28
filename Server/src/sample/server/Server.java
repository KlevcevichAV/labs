package sample.server;

import org.xml.sax.SAXException;
import sample.controller.Controller;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class Server {
    public Socket clientSocket; //сокет для общения
    public ServerSocket server; // серверсокет
    public BufferedReader in; // поток чтения из сокета
    public BufferedWriter out; // поток записи в сокет
    private boolean check;
    public Thread thread;
    Controller controller;

    public boolean getCheck() {
        return check;
    }

    public void incCheck() {
        check = !check;
    }

    public void createServer() throws IOException {
        thread = new AnotherThread();
        thread.start();
//        System.exit(0);
    }

    public void closeServer() throws IOException {
        System.out.println("Сервер закрыт!");
        server.close();
    }

    public Server(Controller controller) {
        check = false;
        this.controller = controller;
    }

    public class AnotherThread extends Thread {

        public List<Integer> list;

        @Override
        public void interrupt() {
            try {
                closeServer();
                super.interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.interrupt();
        }

        @Override
        public void run() {
            try {
                try {
                    server = new ServerSocket(8000); // серверсокет прослушивает порт 4004
                    while (check) {
                        clientSocket = server.accept(); // accept() будет ждать пока
                        controller.addText("Client connected\n");
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        while (true) {
                            String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                            System.out.println(word);
                            if (word.equals("/exit")) {
                                out.flush();
                                controller.addText("Client disconnected\n");
                                controller.loadCheck = false;
                                in.close();
                                out.close();
                                clientSocket.close();
                                break;
                            }
                            controller.function(word);
                        }
                    }
                } catch (SocketException se) {
                    System.out.println(11);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Сервер запущен!"); // хорошо бы серверу
        }
    }
}
