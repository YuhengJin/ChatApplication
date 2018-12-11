package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Communication {
	
	ServerSocket serverSocket;
	Socket clientSocket;

	
	public Communication(int numPort,int numPort2) {
		//ServerSocket class
		try {
				this.serverSocket = new ServerSocket(numPort);
				this.clientSocket = new Socket("127.0.0.1",numPort2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send (String msg) throws IOException {
		//Put the server into a waiting state, listen for incoming connection
		
		Socket link = this.serverSocket.accept();
		PrintWriter out = new PrintWriter(link.getOutputStream(),true);
		//out.println(new Date().toString());
		out.println(msg);
		try {
			link = this.serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void receive () throws IOException {
		Client client = new Client(1234);

		
		BufferedReader input =  new BufferedReader(new InputStreamReader(client.clientSocket.getInputStream())); 
		String answer = input.readLine() ;
		System.out.println(answer);
		
		client.clientSocket.close();

	}
	

}
