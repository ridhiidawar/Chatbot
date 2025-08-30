package source.Project_ChatBot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client
{
    Socket socket ;
    BufferedReader br; //data read
    PrintWriter out; // for writing data
    public  Client(){
        try {
            System.out.println("Sending request to server ");
            socket =new Socket("127.0.0.1",7777);
            System.out.println("Connection done..");

            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        }
        catch (Exception e){
            //TODO : handle exception

        }
    }
        public void startReading() {
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
                    System.out.println("Server  terminated the chat :(");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                System.out.println("Server :"+ msg);
            }
        };
        new Thread(r1).start();
    }
    public void startWriting() {
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

        System.out.println("This is client...");
        new Client();
    }
}
