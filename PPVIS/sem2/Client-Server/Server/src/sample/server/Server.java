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
    public Socket clientSocket;
    public ServerSocket server;
    public BufferedReader in;
    public BufferedWriter out;
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
    }

    public void closeServer() throws IOException {
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
                    server = new ServerSocket(8000);
                    while (check) {
                        clientSocket = server.accept();
                        controller.addText("Client connected\n");
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        while (true) {
                            String word = in.readLine();
                            if (word.equals("/exit")) {
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
        }
    }
}
