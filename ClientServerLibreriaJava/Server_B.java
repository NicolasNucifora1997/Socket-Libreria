//----------------------------------------------//
//    Esercizio Sistemi Distribuiti 18/10/19    //
//            Server_B.java                     //
//           Nicolas Nucifora                   //
//----------------------------------------------//

import java.net.*;
import java.io.*;

public class Server_B{
    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String msg = null;

        System.out.println("---Server started---");

        try{
            while(true){
                clientSocket = serverSocket.accept();
                System.out.println("Connection established with: " + clientSocket.getInetAddress());
                
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

                while( (msg = in.readLine()) != null ){
                    System.out.println("Received message from " + clientSocket.getInetAddress() + ": " + msg);
                    out.println(msg);
                    System.out.println("---Message sent---");
                }

                if(msg == null)
                    System.out.println("Client closed connection");

                out.close();
                in.close();
                clientSocket.close();
            }
        }
        catch(IOException e){
            System.err.println("Error on accept");
            System.exit(1);
        }

        serverSocket.close();
    }
}