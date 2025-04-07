import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

class Server {

    ServerSocket server;
    Socket socket;

    //For Reading and Writing
    BufferedReader br;
    PrintWriter out;

    //Constructor

    public Server(){

        try {
            server = new ServerSocket(7777);
            System.out.println("Server started. Listening for a client...");
            System.out.println("Waiting....");

            socket = server.accept();

            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out= new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
           e.printStackTrace();
        }


    }

    public void startReading() {
        //Thread - continuous Reading the data
        Runnable r1=()->{
            System.out.println("Reading ...");
            while(true){
                try {
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client Terminated the Chat");
                        break;
                    }
                    System.out.println("Client: "+msg);
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            }
        };
        
        //Making Thread object and then Start and pass the reference of Runnable
        new Thread(r1).start();
    }

    public void startWriting() {
        //Thread - continuous Writing the data
        Runnable r2 =()->{

            System.out.println("Writing...");
            while(true){
                try {

                    BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));

                    String content =br1.readLine();
                    out.println(content);
                    out.flush();
                    
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }

        };

        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("This is Server Going to Start server...");

        //Creating Server Object
        new Server();
    }
    
}
