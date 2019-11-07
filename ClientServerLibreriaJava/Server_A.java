//----------------------------------------------//
//    Esercizio Sistemi Distribuiti 18/10/19    //
//            Server_A.java                     //
//           Nicolas Nucifora                   //
//----------------------------------------------//

import java.io.*;
import java.net.*;

public class Server_A{
	public static final int PORT = 9999;
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket serveSock = new ServerSocket(PORT);
		Socket clientSock = null;
		BufferedReader in = null;
		PrintWriter out = null;
		String msg = null;
		System.out.println("---Server started---");
		
		try {
			while(true) {
				clientSock = serveSock.accept();
				System.out.println("Connection established with client: " + clientSock);
				in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSock.getOutputStream())), true);
				
				while((msg = in.readLine()) != null) {
					System.out.println("Received message from " + clientSock.getInetAddress() + ": " + msg);
				}

				if(msg == null){
					System.out.println("Client closed connection");
				}

				in.close();
				out.close();
				clientSock.close();
			}
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}

		serveSock.close();
	}
}