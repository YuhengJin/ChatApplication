package Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;


//import Application.Server;
//import Application.Client ;
//import java.lang.String;

public class User {

	//Variables locales
	private boolean connected;
	private String pseudo; 
	//private Server server;
	//private Client client;
	private Communication com ;
	//ServerSocket serverSocket;
	//Socket clientSocket;
	
	
	public User (String i) {
		this.connected = false;
		this.pseudo = i;	//Verification de pseudo dispo
		
	}
	
	//Quand un user est créer, il a le droit de modifier le pseudo
	public void modify_Pseudo (String name) {
		this.pseudo = name;
		//Verification de pseudo dispo
	}
	
	
	public void connect(int numPort, int numPort2) throws Exception {
		this.com.create_socket_serveur(numPort);	
		this.com.create_socket_client(numPort2);
		this.connected=true ; //A déplacer après le BC
		//this.serverSocket.communication(serverSocket);
		
	}
	
	public void disconnect() throws Exception {
		this.com.serverSocket.close();
		this.connected=false ; //A déplacer après le BC
	}
	
	public void send_Msg(String msg) {
		try {		
			System.out.println("envoi msg A depuis user");
			this.com.send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void receive_Msg() {
		try {
			this.com.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

	}

}
