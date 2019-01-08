package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Application.Chat;
import Application.User;


public class Dialogue {
	
	private Chat chat;
	private User ou;
	private	String his = "";
	private Graphique graphique;
	
	private String typeString = "";
	
	
	
	public Dialogue(Chat u1, User u2, String type) {

		chat = u1;
		ou = u2;
		typeString = type;
		//setTitle(chat.getUserName() + " chatting with " + ou.get_Name());
		
		
		/*graphique.get_btnDmarrerChat().addActionListener(new ActionListener() {

	
			public void actionPerformed(ActionEvent e) {
				String info = graphique.get_jtaSendMessage().getText();
				his = his + chat.getUserName() + " to " + ou.get_Name() + ":  "
						+ info + "\n";
				graphique.get_jtaReceivedMessage().setText(his);
				if (typeString.equals("Server")) {
					chat.getServer().sendMesFromServer(info);
				} else if (typeString.equals("Client")) {
					chat.getClient().sendMessage(info);
				}

			}
		});	*/
		
	}
	
	
	public String getType() {
		return this.typeString;
	}
		
		
	
	
	
	public  void showmessagerecu(String mes) {
		his = his + mes + "\n";
		graphique.get_jtaReceivedMessage().setText(his);
		
	}
	
	
	public  Dialogue() {
		// TODO Auto-generated constructor stub
	}
	
//	public  static void  showmessageialog(String msg){
//		
//		JOptionPane.showMessageDialog(Graphique., msg);
//		
//	}
	
	
}




