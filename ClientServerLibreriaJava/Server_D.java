//----------------------------------------------//
//    Esercizio Sistemi Distribuiti 18/10/19    //
//            Server_D.java                     //
//           Nicolas Nucifora                   //
//----------------------------------------------//

import java.net.*;
import java.io.*;

public class Server_D{
    public static final int PORT = 9999;
    private static String[] titoli = {"Speranza", "Mare", "Autunno", "Ode al giorno felice", "Il calamaio"};
    private static String[] testi = {"Se io avessi una totteguccia/senza fargliela pagare", "M'affaccio alla finestra, e vedo il mare/per chi dunque sei fatto e dove meni?",
                                     "Il gatto rincorre le foglie/al tramonto un lieto fuoco", "Questa volta lasciate che sia felice/essere felice",
                                     "che belle parole/col piu' nero inchiostro"};

    private static String InizioFine(String s){
        for(int i = 0; i < 5; i++)
            if(s.equals(titoli[i]))
                return testi[i];
        return "Not found!";
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
                System.out.println("Connection established with " + clientSocket.getInetAddress());
                
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

                while((msg = in.readLine()) != null){
                    System.out.println("Received message from " + clientSocket.getInetAddress() + ": " + msg);
                    String outMsg = Server_D.InizioFine(msg);
                    out.println(outMsg);
                    System.out.println("---Message sent---");
                }

                if(msg == null)
                    System.out.println("---Client closed connection---");

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
