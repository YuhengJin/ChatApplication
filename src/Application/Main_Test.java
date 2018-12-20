package Application;

import java.net.InetAddress;
import java.net.Socket;

public class Main_Test {

	public static void main(String[] args) throws Exception {
		
        	//Création de 2 Users
		User agentA = new User ("Agent_A",InetAddress.getByName("localhost"),1502) ;
		User agentB = new User ("Agent_B",InetAddress.getByName("localhost"),1503) ;
		User agentC = new User ("Agent_C",InetAddress.getByName("localhost"),1504) ;
		User agentD = new User ("Agent_D",InetAddress.getByName("localhost"),1505) ;

		System.out.println("A,B , C et D crées, avec chacun un seveur");
		
			//Connexion des Users : Création d'un Server pour chacun + Listening
		agentA.connect_appli() ; 
		agentB.connect_appli() ;
		agentC.connect_appli();
		agentD.connect_appli();

	       //Connexion avec un autre User (en créant un Client relié avec le Serveur de l'autre)
		agentA.connect_with(agentB.get_Port());
		agentA.connect_with(agentC.get_Port());
		agentB.connect_with(agentA.get_Port());
		agentC.connect_with(agentA.get_Port());
		
			//Envoi msg
		agentA.sendMsg("Hello UserB! How are u ! ");
		//agentB.sendMsg("Hello userA! I'm fine bro !");
		//agentA.sendMsg("Hi !");

		agentC.disconnect() ;
		
			//Test2 
		/*User userA = new User ("Pierre",InetAddress.getByName("127.0.0.1"), 2023); 
		User userB = new User ("Stephan",InetAddress.getByName("127.0.0.1"), 2024); 
		User userC = new User ("Yuheng",InetAddress.getByName("127.0.0.1"), 2025); 
		
		userA.connect_with(2024);
		userA.sendMsg("Hello!"+userB.get_Name()+"I'm User "+userA.get_Name());
		
		userA.connect_with(2025);
		userA.sendMsg("Hello!"+userC.get_Name()+"I'm User "+userA.get_Name());				
		
		userB.connect_with(2023);	
		userA.disconnect(); */
	
	}
		
		

}
