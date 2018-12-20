package Application;

import java.util.ArrayList;

public class List_users {

		//Variables
	private ArrayList<User> users;
	private User user_Owner ;
	
		//Constructeur
	public List_users(User user) {
		this.user_Owner = user ;
	}
	
		//Modification de la liste : Ajout d'un element qui s'est connecté ou deconnection
	public void User_Connected(User user) {
		this.users.add(user) ;
	}
	
	public void User_DisConnected(User user) {
		this.users.remove(user) ;
	}
	
	public static void main(String[] args) {

	}

}
