import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket; 

class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Client(){

        try {

            System.out.println("Sending Request to the Server");
            
            socket = new Socket("192.168.149.1",7777);

            System.out.println("Connection Done...");


             br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out= new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
           
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
                    System.out.println("Server: "+msg);
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
        System.out.println("This is Client....");

        //Calling Constructor
        new Client();
    }
    
}
