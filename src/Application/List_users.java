package Application;

import java.util.ArrayList;



public class List_users {
	
		//Variables
		private ArrayList<User> users;
		private User user_Owner ;
		
		/*public List_users(User user) {
			this.user_Owner = user ;
		}*/
		
		public List_users() {
			users = new ArrayList<User>();
			//this.user_Owner = user;
			//this.users.add(user) ;
		}
		
		//Modification de la liste : Ajout d'un element qui s'est connecté ou deconnection
		public void add_User(User user) {
			/*int test=0 ;
			for (User u : users) {
				if (u.equals(user)) {
					test=1 ;
					System.out.println("Element exist déja ds la liste! test= "+test);		
				}
			}
			if (test==0) {
				this.users.add(user) ;
			}	
			//System.out.println("test= "+test);		
			System.out.println(user_Owner.get_Name()+"add the user: "+user.get_Name()+"  test= "+test);		*/
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
		


}
