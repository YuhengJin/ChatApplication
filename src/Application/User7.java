/*
package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;

public class User7 {

	//Variables locales
	private boolean connected;
	private String pseudo;
	private Socket clientSocket;
	private BufferedReader input;
	
	
	public User7 (String i)  {
		this.connected = false;
		this.pseudo = i;	//Verification de pseudo dispo
	}
	

	public Client(int numPort) throws UnknownHostException, IOException {
		//ServerSocket class

		System.out.println(numPort);

		this.clientSocket = new Socket(InetAddress.getLocalHost(),numPort);
	
	}	
	

	//Quand un user est créer, il a le droit de modifier le pseudo
	public void modify_Pseudo (String name) {
		this.pseudo = name;
		//Verification de pseudo dispo
	}
	
	
	public void connect_user(int numPort, int numPort2) throws Exception {
		System.out.println("Entrée connect user");
		this.server = new Server(numPort);
		System.out.println("Création socketserv");

		this.client = new Client(numPort);
		System.out.println("Création socketclient");

	}




}
*/