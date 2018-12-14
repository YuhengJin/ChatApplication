package Application;

public class Main_Test {

	public static void main(String[] args) throws Exception {

		User agentA = new User ("Agent_A") ;
		User agentB = new User ("Agent_B") ;
		System.out.println("A et B crées");

		
		agentA.connect_user(999);
		System.out.println("User A conected");
		//agentA.disconnect();
		
		
		agentB.connect_user(1220);
		//System.out.println("B co");
//
//		System.out.println("avant envoi msg A depuis main");
//		agentA.send_Msg("Salut !");
//		System.out.println("apres envoi msg A depuis main");
//		agentB.receive_Msg();
	}
		
		

}
