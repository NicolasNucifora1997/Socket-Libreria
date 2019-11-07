//----------------------------------------------//
//    Esercizio Sistemi Distribuiti 18/10/19    //
//            Server_C.java                     //
//           Nicolas Nucifora                   //
//----------------------------------------------//

import java.io.*;
import java.net.*;

public class Server_C{
    public static final int PORT = 9999;

    private static String InizioFine(String s){
        return s;
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String msg = null;

        System.out.println("---Server Started---");

        try{
            while(true){
                clientSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

                System.out.println("Connection established with: " + clientSocket.getInetAddress());

                while((msg = in.readLine()) != null){
                    System.out.println("Received message from " + clientSocket.getInetAddress() + ": " + msg);
                    String outMsg = Server_C.InizioFine(msg);
                    out.println(outMsg);
                    System.out.println("---Message Sent---");
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