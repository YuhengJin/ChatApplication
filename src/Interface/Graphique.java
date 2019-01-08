package Interface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import Application.Chat;


import javax.swing.SwingConstants;
import java.awt.Color;

public class Graphique extends JFrame{
	private static JPanel contentPane;
	private Dialogue dialogue = new Dialogue();
	static Graphique frame;
	private String pseudo;
	private int port;
	private JButton btnDmarrerChat;
	private JTextField textField_port;
	private JTextField textField_pass;
	private JTextField textField_name;
	private JTextArea jtaSendMessage;
	private JTextArea jtaReceivedMessage;
	private JTextField jtfPort;
	private JTextField jtfIP;
	private static DefaultListModel<String> defaultListModel;
	private static JList<String> clientList;
	private static Chat chat;
	private	static String his = "";
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Graphique();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Graphique(){
		//Global interface
		setTitle("Imesssager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 430);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		//Panel login
		JPanel panel_login = new JPanel();
		panel_login.setBorder(new EmptyBorder(5, 5, 5, 5));
		//Panel chat
		final CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		final JPanel panel_chat = new JPanel();

		// Label titre
		JLabel label_title = new JLabel("Veuillez connecter");
		label_title.setForeground(Color.DARK_GRAY);
		label_title.setFont(new Font("Garuda", Font.BOLD | Font.ITALIC, 17));
		label_title.setBounds(250, 29, 177, 46);
		//label_title.setLocation(90, y);;
		panel_login.add(label_title);
		
	
		JLabel label_name = new JLabel("Username");
		label_name.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		label_name.setBounds(171, 99, 69, 32);
		
		textField_name = new JTextField();
		textField_name.setBounds(355, 97, 112, 34);
		textField_name.setColumns(10);
		panel_login.setLayout(null);
		panel_login.add(label_name);
		panel_login.add(textField_name);
		
		
		
		JLabel label_pass = new JLabel("Password:");
		label_pass.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
        label_pass.setBounds(171,157,80,25);
        panel_login.add(label_pass);
        
        
		JLabel label_port = new JLabel("N° de port");
		label_port.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		label_port.setBounds(171, 209, 76, 32);
		panel_login.add(label_port);
		
		JButton button_connect = new JButton("Se connecter");
		button_connect.setFont(new Font("Garuda", Font.BOLD, 15));
		button_connect.setBounds(256, 295, 135, 41);
		panel_login.add(button_connect);
		
		// login
		button_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField_name.getText();
				int port = Integer.parseInt(textField_port.getText());

				System.out.println(name);
				System.out.println(port);
				jtfPort.setText(String.valueOf(port));
			
				chat = new Chat(name, port);
				System.out.println(name + " is running" + " listeningport " + port);
				chat.initrceivemessage();
				chat.multicast("newuser/" + name + "/" + Integer.toString(port));
				setTitle("User ["+name+"] ");

				// Gerer le pb de Le meme nom, et le port <1024
				//Change the display, entry to the second page
				cardLayout.show(contentPane, "chat");

			}
		});
		
		//No visibal the passeword
		textField_pass = new JPasswordField();
		textField_pass.setBounds(355, 154, 112, 34);
		textField_pass.setColumns(10);
		panel_login.add(textField_pass);
		
		
		textField_port = new JTextField();
		textField_port.setBounds(355, 209, 112, 34);
		textField_port.setColumns(10);
		//Input only number
		textField_port.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
				} else {
					e.consume(); 
				}
			}
		});
		panel_login.add(textField_port);
		
		// when the user close the window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				try {
					chat.broadcastmessage("quit*/" + chat.getUserName() + "/" + chat.getListeningport());
				} catch (NullPointerException e1) {
					frame.dispose();
				}
			}
		});
		panel_chat.setLayout(null);
		
		
		
		
		// 设置IPAddress标签
        JLabel jlIP = new JLabel("IPAddress:");
        jlIP.setBounds(10, 15, 70, 25);
        panel_chat.add(jlIP);
        
		// 设置IPAddress显示文本
		jtfIP = new JTextField();
		jtfIP.setText("127.0.0.1");
		jtfIP.setEditable(false);
		jtfIP.setBounds(90, 15, 185, 25);
		//jtfIP.setText(Common.IP);
		panel_chat.add(jtfIP);

		// 设置Port标签
		JLabel jlPort = new JLabel("Port:");
		jlPort.setBounds(10, 50, 70, 25);
		panel_chat.add(jlPort);
		// 设置Port显示文本
		jtfPort = new JTextField();
		//jtfPort.setText("10055");
		jtfPort.setEditable(false);
		jtfPort.setBounds(90, 50, 185, 25);
		//jtfPort.setText(Common.PORT + "");
		panel_chat.add(jtfPort);

        
        
        
        
        
		
		btnDmarrerChat = new JButton("Send Message");
		btnDmarrerChat.setBounds(163, 322, 112, 35);
		panel_chat.add(btnDmarrerChat);
		btnDmarrerChat.setVisible(true);
		
		
		
		
		
		
		//La list user enligne
		JLabel labellist = new JLabel("Users en ligne ");
		labellist.setBounds(25, 67, 160, 67);
		panel_chat.add(labellist);
		
		defaultListModel = new DefaultListModel<>();
		clientList = new JList<String>(defaultListModel);
		clientList.setBorder(BorderFactory.createLineBorder(Color.black));
		clientList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		clientList.setFocusable(false);

		// Scroll
		JScrollPane jpclientList = new JScrollPane(clientList);
		jpclientList.setBounds(25, 115, 255, 65);
		panel_chat.add(jpclientList);
	
		
		// 设置Send Message标签
        JLabel jlSendMessage = new JLabel("Send Message:");
        jlSendMessage.setBounds(25, 180, 100, 25);
        panel_chat.add(jlSendMessage);
		// 设置Send Message文本
        jtaSendMessage = new JTextArea();
        jtaSendMessage.setLineWrap(true);
        jtaSendMessage.setWrapStyleWord(true);

        jtaSendMessage.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane jpSendMessage = new JScrollPane(jtaSendMessage);
        jpSendMessage.setBounds(25, 210, 255, 100);
        panel_chat.add(jpSendMessage);
        
        // 设置Received Message标签
        JLabel labelReceivedMessage = new JLabel("Chat Messages:");
        labelReceivedMessage.setBounds(345, 12, 130, 25);
        panel_chat.add(labelReceivedMessage);
		// 设置Received Message文本
		jtaReceivedMessage = new JTextArea();
		jtaReceivedMessage.setLineWrap(true);
		jtaReceivedMessage.setWrapStyleWord(true);
		jtaReceivedMessage.setEditable(false);
		jtaReceivedMessage.setBorder(BorderFactory.createLineBorder(Color.black));
		JScrollPane jpReceivedMessage = new JScrollPane(jtaReceivedMessage);
		jpReceivedMessage.setBounds(340, 40, 250, 320);
		panel_chat.add(jpReceivedMessage);
		
		
		// commence un nouveau chat
		btnDmarrerChat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String name = defaultListModel.getElementAt(clientList.getSelectedIndex());
				System.out.println("/n Test1=================avec qui "+name);
				chat.chatWithOne(name);
				
				Application.User nu = null;
				for (Application.User ou : chat.getUsers().getUsers()) {
					if (ou.get_Name().equals(name)) {
						nu = ou;
					}
				}
				
				String typeString = dialogue.getType();
				System.out.println("/n Test2================= get the type"+typeString);
				
				
				String info = jtaSendMessage.getText();
				his = his + chat.getUserName() + " to " + nu.get_Name() + ":  "
						+ info + "\n";
				jtaReceivedMessage.setText(his);
				
				
				if (typeString.equals("Server")) {
					chat.getServer().sendMesFromServer(info);
					System.out.println("Test4   ***************************");
				} else if (typeString.equals("Client")) {
					chat.getClient().sendMessage(info);
				}
			}
		});
		
	
		
		//Global
		contentPane.add(panel_login, "login");
		contentPane.add(panel_chat, "chat");
	}
	
	
	
	public static void freshlist() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++");
		ArrayList<String> usernamelist = new ArrayList<String>();
		chat.getUsers().printUserConnecte();
		
		for (Application.User u : chat.getUsers().getUsers()) {
			usernamelist.add(u.get_Name());
		}
		defaultListModel.clear();
		for (String element : usernamelist) {
			defaultListModel.addElement(element);
		}

	}


	public JButton get_btnDmarrerChat() {
		return this.btnDmarrerChat;
	}
	
	
	public JTextArea get_jtaSendMessage() {
		return this.jtaSendMessage;
	}
	
	public JTextArea get_jtaReceivedMessage() {
		return this.jtaReceivedMessage;
	}
	
	
	public JPanel get_contentPane() {
		return this.contentPane;
	}
	
	
	
	
	
	
	
	
	
	

}
	
	


