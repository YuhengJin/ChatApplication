package Application;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;

public class User {

		//Variables locales
	private boolean connected;
	private String pseudo; 
	private InetAddress addressUser;
	private int portClientUser;
	private int portServerUser;	
	private Server server;
	private Client client;
	private List_users usersList ;
    private BroadCastClient broadCast ;
    
	public User (String pseudo,InetAddress address, int portServer)  {
		this.connected = false;
		this.pseudo = pseudo;			//A faire : Verification de pseudo dispo
		this.addressUser = address;
		this.portServerUser = portServer;
        this.usersList = new List_users(this) ;

	}

	public void connect_appli() throws Exception {
		this.server = new Server(portServerUser,this.pseudo);
        server.Startlistenning(); //Launch the thread
        String connexion = "Connexion de "+pseudo ;
        //this.broadCast = new BroadCastClient() ;
        //this.broadCast.broadcast(connexion, InetAddress.getByName("255.255.255.255"),pseudo);
        //this.connected = true ;
        //usersList.User_Connected(this) ;
        
	}

	public void connect_with (int numPortServ) throws UnknownHostException, Exception {
		
		this.client = new Client(numPortServ,this.pseudo);// 启动客户端
		this.client.startClient();
        //String connexion = "Connexion de "+pseudo ;
	    //this.broadCast = new BroadCastClient() ;
	    //this.broadCast.broadcast(connexion, InetAddress.getByName("255.255.255.255"),pseudo);
	}

	public void sendMsg(String msg) {
		//server.sendMesFromServer(msg) ;
		client.sendMessage(msg);
	}
	
	//Quand un user est crée, il a le droit de modifier le pseudo
	public void set_Name (String pseudo) {
		this.pseudo = pseudo;
		//Verification de pseudo dispo
	}
	
		/* Getteurs */ 
	
	public String get_Name() {
		return this.pseudo;
	}
	public InetAddress get_Address() {
		return this.addressUser;
	}	
	public int get_Port() {
		return this.portServerUser;
	}
	public boolean get_StatusConnec() {
		return this.connected;
	}


	
	//Shut down the system
	public void disconnect() throws Exception {
		String connexion = "Deconnexion de "+pseudo ;
	    this.broadCast = new BroadCastClient() ;
	    this.broadCast.broadcast(connexion, InetAddress.getByName("255.255.255.255"),pseudo);
		
	    //this.server.closeAll();
		//this.client.closeAll();
		//this.connected=false ; //A déplacer après le BC
		//System.out.println("La session de"+this.pseudo+" est fini");
		
	}
	
	public static void main(String[] args) {}

}
