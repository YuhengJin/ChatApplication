package Application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Application.Server;

public class Server {
	
	ServerSocket serverSocket;
	Socket link;
	
	public Server(int numPort) {
		//ServerSocket class
		try {
				System.out.println(numPort);
				serverSocket = new ServerSocket(1111);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send (Server server,String msg) throws IOException {
		//Put the server into a waiting state, listen for incoming connection
		
		this.link = server.serverSocket.accept();
		PrintWriter out = new PrintWriter(link.getOutputStream(),true);
		//out.println(new Date().toString());
		out.println(msg);
		try {
			link = server.serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeAll() {
		try {
			this.serverSocket.close();
			this.link.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
