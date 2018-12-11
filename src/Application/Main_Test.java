package Application;

public class Main_Test {

	public static void main(String[] args) {

		User agentA = new User ("Agent_A") ;
		User agentB = new User ("Agent_B") ;
		
		try {
			agentA.connect(1238,1234);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			agentB.connect(1239,1226);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		agentA.receive_Msg();
		agentA.send_Msg("Salut mon pote");
	}
		
		

}
