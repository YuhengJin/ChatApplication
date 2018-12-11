package Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;


import Application.Server;
import Application.Client ;
import java.lang.String;

public class User {

	//Variables locales
	private boolean connected;
	private String pseudo; 
	private Server server;
	private Client client;
	
	

	
	public User (String new_pseudo) {
		this.connected = false;
		this.pseudo = new_pseudo;	//Verification de pseudo dispo
		
	}
	
	
	//Quand un user est créer, il a le droit de modifier le pseudo
	public void modify_Pseudo (String name) {
		this.pseudo = name;
		//Verification de pseudo dispo
	}
	
	
	public void connect(int numPort) throws Exception {
		this.server = new Server(numPort);
		this.connected=true ; //A déplacer après le BC
		//this.serverSocket.communication(serverSocket);
		
	}
	
	public void disconnect() throws Exception {
		this.server.serverSocket.close();
		this.connected=false ; //A déplacer après le BC
	}
	
	public void send_Msg(String msg) {
		try {
			this.server.send(server,msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	

	
	
	

	
	public static void main(String[] args) {

	}

}
