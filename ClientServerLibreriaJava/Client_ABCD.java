//----------------------------------------------//
//    Esercizio Sistemi Distribuiti 18/10/19    //
//            Client_ABCD.java                  //
//           Nicolas Nucifora                   //
//----------------------------------------------//

import java.io.*;
import java.net.*;

public class Client_ABCD{
    private static final int PORT = 9999;

    public static void main(String[] args) throws IOException{
        InetAddress addr = null;
        if(args.length == 0) addr = InetAddress.getByName(null);
        else addr = InetAddress.getByName(args[0]);
        Socket remoteSocket = null;
        BufferedReader in = null;
        BufferedReader input = null;
        PrintWriter out = null;
        String msg = null;

        try{
            System.out.println("---Client started (CTRL-C to exit)---");

            remoteSocket = new Socket(addr, PORT);
            System.out.println("Connected with server: " + remoteSocket);

            in = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(remoteSocket.getOutputStream())), true);
            input = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("What poetry would you like to read today?\n-Speranza\n-Mare\n-Autunno\n-Ode al giorno felice\n-Il calamaio\n\n");
            while((msg = input.readLine()) != null){
                System.out.println("---Title: " + msg + "---");
                out.println(msg);
                String testo = in.readLine();
                System.out.println("---Text: " + testo + "---");
            }
        }
        catch(UnknownHostException e){
            System.err.println("Unknow host");
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("Error on I/O");
            System.exit(1);
        }

        out.close();
        in.close();
        input.close();
        remoteSocket.close();
    }
}