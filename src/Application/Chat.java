package Application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Session.Session;

public class Chat {
	private String username;
	private ArrayList<User> users;
	private int listeningport;
	
	
	
	
	
	
	// faire chat avec quequn
	public void chatWithOne(String nom) {

		int destinationport = 0;
		for (User user : users) {

			if (user.get_Name().equals(nom)) {
				destinationport = user.get_Port();
			}
		}

		if (destinationport != 0) {
			Session s;
			try {
				s = new Session(InetAddress.getByName("localhost"), destinationport);
				s.sendmesssage("*chat/" + username + "/" + listeningport);

			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	
	
	
	public void printUserConnecte() {
		// TODO Auto-generated method stub

		for(int i=0;i<users.size();i++) {
			System.out.println("*******************");
			System.out.println("["+users.get(i).get_Name()+"]");
			System.out.print("  IP"+users.get(i).get_Address()+"]");
			System.out.print("  Port "+users.get(i).get_Port()+"]");
		}

	}
	
	
	
	
	public String getUsername() {
		return username;
	}

	public int getListeningport() {
		return listeningport;
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	
	
	

}
