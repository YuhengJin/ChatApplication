package Application;

import java.net.Socket;

public class Main_Test {

	public static void main(String[] args) throws Exception {
		
		//Test1
		/*Socket socket =  new Socket();
		System.out.println(socket.getInetAddress().toString());
		System.out.println(socket.getLocalAddress().toString());
		

		User agentA = new User ("Agent_A") ;
		User agentB = new User ("Agent_B") ;
		System.out.println("A et B crées");

		
		agentA.connect_user();
		System.out.println("User A conected");
		//agentA.disconnect();
		
		
		agentB.connect_user(1220);*/
		//System.out.println("B co");
//
//		System.out.println("avant envoi msg A depuis main");
//		agentA.send_Msg("Salut !");
//		System.out.println("apres envoi msg A depuis main");
//		agentB.receive_Msg(); */
		
		//Test2
		Client c1 = new Client(1502);// 启动客户端
		System.out.println("[Client c1 : Connexion with the server...]");
		c1.startClient();	//Démarre le client (création d'un socket, ...)
		
		c1.sendMessage("Salut serveur" + c1.getPortClient());
		
	}
		
		

}
 