package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Communication {
	
	ServerSocket serverSocket;
	Socket clientSocket;

	
	public void create_socket_client(int numPort) throws java.net.UnknownHostException, IOException  {
		//ServerSocket class
		System.out.println("constructeur");

		try {
			this.serverSocket = new ServerSocket(numPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("constructeur création serversocket");
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		}

	public void create_socket_serveur(int numPort2) throws java.net.UnknownHostException, IOException  {

		try {
			this.clientSocket = new Socket( InetAddress.getLoopbackAddress(),numPort2);
		} catch (IOException e) {
		    System.out.println("Can't connect");
		    System.exit(1);
		}
		
		System.out.println("constructeur création clientsocket");
		
	}
	
	public void send (String msg) throws IOException {
		System.out.println("envoi msg A depuis com");

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
		
		BufferedReader input =  new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream())); 
		String answer = input.readLine() ;
		System.out.println(answer);
		this.clientSocket.close();

	}
	

}
