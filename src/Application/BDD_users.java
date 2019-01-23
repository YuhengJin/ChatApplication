package Application;

import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;


public class BDD_users {
	
		//Variables
		ArrayList<User> users;
		private User user_Owner ;
		
		/*public List_users(User user) {
			this.user_Owner = user ;
		}*/
		
		public BDD_users() throws SQLException, UnknownHostException {
			users = new ArrayList<User>();
			this.add_User(new User("Test2", InetAddress.getByName("localhost"), 1999));

			//this.user_Owner = user;
			//this.users.add(user) ;
			
			/*
			Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=rootpassword"); 
			Statement s = Conn.createStatement();
			int Result=s.executeUpdate("CREATE DATABASE databasename");
		*/	
			
		}
		
		//Modification de la liste : Ajout d'un element qui s'est connecté ou deconnection
		public void add_User(User user) {
			this.users.add(user) ;
		}
		
		public void User_DisConnected(User user) {
			this.users.remove(user) ;
		}
		
		
		public void printUserConnecte() {
			// TODO Auto-generated method stub

			for(int i=0;i<users.size();i++) {
				System.out.println("*******************");
				System.out.println("["+users.get(i).get_Name()+"]  "+"  IP"+users.get(i).get_Address()+"]"+"  Port "+users.get(i).get_Port()+"]");
			}
		}
		
		public User getUsername() {
			return user_Owner;
		}
		
		
		public ArrayList<User> getUsers() {
			return users;
		}
		
		/*public boolean containsPseudo(String name){
		    return users.stream().filter(user -> user.get_Name().equals(name)).findFirst().isPresent();
		}

		public boolean containsPort(int port){
		    return users.stream().filter(user -> user.get_Port() == port).findFirst().isPresent();
		}*/

		public boolean containsPseudo(String name){
			//return users.stream().map(User::get_Name).filter(name::equals).findFirst().isPresent(); 
			boolean res=false ;
			for (User u : users) {
				//System.out.println(u.get_Name());
				if (u.get_Name().equals(name)) {
					res=true ;
				}
			}
			//System.out.println("Test si le nom "+name+" existe ? "+res);
			return res ;
		}
		
		public boolean containsPort(int port){
			boolean res=false ;
			for (User u : users) {
				// Find the position of offlineuser
				if (u.get_Port()==port) {
					res=true ;
				}
			}
			//System.out.println("Test si le port "+port+" existe ? "+res);
			return res ;
		}
		
		
		public void maj_user(String old_pseudo, String new_pseudo) {
			for (User u : users) {
				if (u.get_Name().equals(old_pseudo)) {
					u.maj_pseudo(new_pseudo);
				}
			}
		}
		
		public void clear_users() {
			users.clear();
		}
		
		public void show() {
			
		}
		public User getUserFromPseudo(String pseudo) {
			User res = null ;
			for (User u : users) {
				if (u.get_Name().equals(pseudo)) {
					res=u;
				}
			}
			return res ;
		}
		
		public int onlyme () {
			int res=0 ;
			if (users.contains(user_Owner) && users.size()==2) {
				res=1 ;
			}
			System.out.println("Fonction Onlyme, res="+res);
			return res ;
		}
		
}
