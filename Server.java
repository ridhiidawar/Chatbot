package source.Project_ChatBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    //constructor
    ServerSocket server;
    Socket socket;
    BufferedReader br; //data read
    PrintWriter out; // for writing data
    public Server(){
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("Waiting....");
            socket = server.accept();//connection accept
            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public  void startReading(){
        //thread -read kr ke deta rehega
        Runnable r1=()->{
            System.out.println("Reader started..");
            while(true){
                String msg= null;
                try {
                    msg = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(msg.equals("exit")){
                    System.out.println("Client  terminated the chat :(");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                System.out.println("Client :"+ msg);
            }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //thread-data user lega and then send krega client tk
       Runnable r2=()->{
           System.out.println("Writer started!..");
           try{
           while(true){
                   BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                   String content= br1.readLine();
                   out.println(content);
                   out.flush();
               }
           }
           catch (Exception e){
               e.printStackTrace();
           }
       };
       new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is server...Going to start server");
        new Server();
    }
}
