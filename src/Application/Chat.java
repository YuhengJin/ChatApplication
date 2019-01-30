package Application;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import Application.Server.CommunicateThread;
import Interface.Graphique;
import Session.Session;

public class Chat {
	//private User user;
	private String username;
	private String usermdp;
	private List_users users;
	private BDD_users bdd_users;
	private int listeningport;
	DatagramSocket ds;
	MulticastSocket sock;
	Server server;
	Client client;
	private static Thread mutiThread;
	private static boolean running = true;
	private static Thread inimsaageThread;
	private int samename=0 ;
	private int sameport=0 ;
	private int wrongmdp=0 ;
	private int wrongpseudo=0 ;
	private String type ;
	private int ds_created=1 ;
	private int ok=1 ;
	private int notmodif=0 ;
	private int onlyme=0 ; 
	
	
	public Chat(String  username, int port, String mdp, String type2) throws Exception {
		//Context = this;
		running=true;
		this.username = username;
		this.listeningport = port;
		this.usermdp = mdp ;
		this.type=type2 ;
		users = new List_users();
		bdd_users = new BDD_users();
		User me = new User(username, InetAddress.getByName("localhost"), port) ;
		me.set_Mdp(usermdp);
		bdd_users.add_User(me);
		//bdd_users.add_User(new User("Test2", InetAddress.getByName("localhost"), 1999));
		for (int i=0;i<5000;i++) {}

		System.out.println("constructeur de chat, BDD de "+username+": ");
		for (User u : bdd_users.getUsers()) {
			System.out.println(u.get_Name());
		}
		System.out.println("Fin BDD");

		try {
			ds = new DatagramSocket(listeningport);
			
		} catch (BindException bb) {
			System.out.println("Error listening port dans bindexception");
			ok=0 ;
			sameport=1 ;
			ds_created=0 ;
			closechat() ;

		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("Error listening port dans socketexcpetion");

		}

	}
	
	
	public void multicast(final String msg) {
		try {
			sock = new MulticastSocket(2222);
			InetAddress bcAddr = InetAddress.getByName("230.0.0.1");

			sock.joinGroup(bcAddr);
			sock.setLoopbackMode(false); 
			mutiThread = new Thread(new Runnable() {

				@Override
				public void run() {
					byte[] buffer = new byte[65507];
					DatagramPacket inpack = new DatagramPacket(buffer,
							buffer.length);

					while (running) {
						try {
						
							sock.receive(inpack);
							String info = new String(inpack.getData(), 0,inpack.getLength());
							System.out.println(username+"端  Print message recu： "+info);
							
							//user changed his username
							if (info.startsWith("modif")) {
								String old_pseudo = info.split("/")[1] ;
								String new_pseudo = info.split("/")[2] ;
								int port2 = Integer.parseInt(info.split("/")[3]) ;
								System.out.println("Reception modifpseudo sur "+username+" oldpseudo: "+old_pseudo+" new_pseudo: "+new_pseudo);

								if (!old_pseudo.equals(username)) {
									
									if (bdd_users.containsPseudo(new_pseudo)) {
										User newuser = new User(old_pseudo, inpack.getAddress(),port2);
										Session session = new Session(newuser.get_Address(),newuser.get_Port());
										session.sendmesssage("notmodif");
									}
									else {
										users.maj_user(old_pseudo, new_pseudo);
										bdd_users.maj_user(old_pseudo, new_pseudo);
										Graphique.freshlist();
									}
								}
								
							}
							
							if (info.startsWith("inscription")) {
								// Means the user is on line
								String name = info.split("/")[1];
								int port = Integer.parseInt(info.split("/")[2]);
								String mdp = info.split("/")[3];

								User newuser = new User(name, inpack.getAddress(), port);
								newuser.set_Mdp(mdp);
								Session session = new Session(newuser.get_Address(),newuser.get_Port());
								
								System.out.println("Reception dans multicast Inscription, BDD de "+username+": ");
								for (User u : bdd_users.users) {
									System.out.println(u.get_Name()+u.get_Port());
								}
								System.out.println("Fin BDD");
								
								//There is  a new user
								if (!bdd_users.containsPseudo(name) && !bdd_users.containsPort(port)){
									System.out.println(username+" recoit: New user "+ new String(inpack.getData(), 0,inpack.getLength()));
									users.getUsers().add(newuser);
									bdd_users.getUsers().add(newuser);
									Graphique.freshlist();
									session.sendmesssage("newuser/" + username+ "/"+ Integer.toString(listeningport)+"/"+usermdp);

							      } else if (bdd_users.containsPseudo(name) && !bdd_users.containsPort(port)){
									session.sendmesssage("samename/");
									System.out.println("["+username+" Same name]");
									
								  } else if (!bdd_users.containsPseudo(name) && bdd_users.containsPort(port)){
									session.sendmesssage("sameport/");
								}
							}
							
							//Connexion
							if(info.startsWith("connexion")) {
								
								// Means the user is on line
								String name = info.split("/")[1];
								int port = Integer.parseInt(info.split("/")[2]);
								String mdp2 = info.split("/")[3];
								System.out.println("[Tentative de connexion de "+name+mdp2+"]");
								System.out.println("Dans connexion, BDD de "+username+": ");
								for (User j : bdd_users.getUsers()) {
									System.out.println(j.get_Name()+j.get_Mdp());
								}
								System.out.println("Fin BDD");
								//System.out.println("[Comparaison entre "+mdp2+" et "+bdd_users.getUserFromPseudo(name).get_Mdp()+"]");

								User newuser = new User(name, inpack.getAddress(), port);
								newuser.set_Mdp(mdp2);
								
								Session session = new Session(newuser.get_Address(),newuser.get_Port());
								
								if (!bdd_users.containsPseudo(name)) {
									session.sendmesssage("wrongpseudo/");
									System.out.println("[Envoi de "+username+" wrongpseudo]");
								}
								else if (bdd_users.containsPseudo(name) && !bdd_users.getUserFromPseudo(name).get_Mdp().equals(mdp2)) {
									session.sendmesssage("wrongmdp/");
									System.out.println("[Envoi de "+username+" wrongmdp]");
								}
								else if (bdd_users.containsPseudo(name) && bdd_users.getUserFromPseudo(name).get_Mdp().equals(mdp2)) {
									System.out.println("[Connexion "+username+" recoit: "+ new String(inpack.getData(), 0,inpack.getLength()));
									if (!name.equals(username)) {
										users.getUsers().add(newuser);
										System.out.println("[Connexion, ListUsers "+username+" ajoute: "+name+"]") ;
									}
									Graphique.freshlist();
									session.sendmesssage("newuser/" + username+ "/"+ Integer.toString(listeningport)+"/"+usermdp);
								}
							}
							
							// user quit offline
							if (info.startsWith("quit*")){
							//else if (info.split("/")[1].equals(username)) {
								// respresent the user is off line
								String offlineusername = info.split("/")[1];
								String offlineport = info.split("/")[2];
								
								System.out.println("users co de "+username+" suppression de "+offlineusername);
								for (User u : users.getUsers()) {
									if (u.get_Name().equals(offlineusername) && !offlineusername.equals(username)) {
										System.out.println(u.get_Name());
									}
								}
								System.out.println("fin users co de "+username);

								
								
								if (!offlineusername.equals(username)) {
									for (User u : users.getUsers()) {
										if (u.get_Name().equals(offlineusername) && !offlineusername.equals(username)) {
											users.getUsers().remove(u) ;
											System.out.println("SUPPRESSION DE "+u.get_Name());
											Graphique.freshlist();
											if (users.getUsers().isEmpty()) {
												break ;
											}
											
										}Graphique.freshlist();
									}
								}
								else if (offlineusername.equals(username)) {
									closechat() ;
								}
								
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});
			mutiThread.start();
			// broadcast i'm on line
			if (type.equals("inscription")) {
				broadcastmessage("inscription/"+username + "/" + listeningport+"/"+usermdp);
			}
			else if (type.equals("connexion")) {
				broadcastmessage("connexion/"+username + "/" + listeningport+"/"+usermdp);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void broadcastmessage(String msg) {
		try {
			InetAddress bcAddr = InetAddress.getByName("230.0.0.1");
			DatagramPacket outpack = new DatagramPacket(msg.getBytes(),msg.getBytes().length, bcAddr, 2222); 

			sock.send(outpack);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	public void initrceivemessage() {

		if (ok==1) {
		final Chat me = this;

		inimsaageThread = new Thread(new Runnable() {

			public void run() {
				System.out.println("[Initialisation de initreceive de "+username+", running= "+running+"]");

				try {
					while (running) {
						for(int c=0; c<5000;c++) {}

						DatagramPacket dp = new DatagramPacket(new byte[256],
								256);
						ds.receive(dp);
						byte[] data = dp.getData();
						String info = new String(data).trim();
						
						System.out.println(username+" Message recu :       "+info);
						// if request for chat, on lui envoi une confirmation de notre cote et lance le server
						if (info.split("/")[0].equals("*chat")) {

							String name = info.split("/")[1];
							String port = info.split("/")[2];
							// distribuer a new port for chatting
							int chatport = listeningport + 1000;
							// commencer listening this port

							User u = new User(name,InetAddress.getByName("localhost"),Integer.parseInt(port));
							//bdd_users.add_User(u);
							
							System.out.println("Dans initreceive, BDD de "+username+": ");
							for (User j : bdd_users.getUsers()) {
								System.out.println(j.get_Name());
							}
							System.out.println("Fin BDD");

							server = new Server(chatport, u, me);
							server.Startlistenning();

							try {

								Session s = new Session(InetAddress.getByName("localhost"),Integer.parseInt(port));
								s.sendmesssage("*re/" + username + "/"+ chatport);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						// receive a request for groupechat
						if (info.split("/")[0].equals("*groupechat")) {
							String name = info.split("/")[1];
							String msg = info.split("/")[2];

							// distribuer a new port for chatting
							//Display.showmessage(name + "send you a message:\n"
							//		+ msg);
							// commencer listening this port

						}

						// receive response for chat
						if (info.startsWith("*re")) {
							System.out.println("\n"+"收到聊天请求     准备启动  client");
							String username = info.split("/")[1];
							String chatport = info.split("/")[2];
							// distribuer a new port for chatting
							User u = new User(username,InetAddress.getByName("localhost"),Integer.parseInt(chatport));
							
							
							//u.connecter(Integer.parseInt(chatport));
							
							client = new Client(Integer.parseInt(chatport), u, me,InetAddress.getByName("localhost"));
							client.startClient();

						}
						// if there is new user (Reponse de multicast pr savoir qui est connecté)
						if (info.split("/")[0].equals("newuser")) {
							String name = info.split("/")[1];
							int port = Integer.parseInt(info.split("/")[2]);
							String mdp = info.split("/")[3];

							System.out.println("List_user de "+username+" avant le newuser : ");
							for (User j : users.getUsers()) {
								System.out.println(j.get_Name());
							}
							System.out.println("Fin List");
							
							User nu = new User(name, dp.getAddress(), port) ;
							nu.set_Mdp(mdp);
							if (!username.equals(name)) {
								System.out.println("[Initreceive, ListUsers "+username+" ajoute: "+name+"]") ;
								users.add_User(nu);
								bdd_users.add_User(nu);	
							}

							Graphique.freshlist();
							//System.out.println("For  "+username+"端 the user en ligne");
							//users.printUserConnecte();
							
							System.out.println("List_user de "+username+" après le newuser : ");
							for (User j : users.getUsers()) {
								System.out.println(j.get_Name());
							}
							System.out.println("Fin List");
						}

						if (info.split("/")[0].equals("samename")) {
							// represent this name is already used
							//Graphique.showmessage("user alreay exsits!");
							System.out.println("[Reception samename "+username+"]");
							samename=1 ;
							closechat() ;
						}

						if (info.split("/")[0].equals("sameport")) {
							System.out.println("[Reception sameport "+username+"]");
							sameport=1 ;		
							closechat() ;

						}

						if (info.split("/")[0].equals("wrongpseudo")) {
							System.out.println("[Reception wrongpseudo "+username+"]");
							wrongpseudo=1 ;
							closechat() ;

						}

						if (info.split("/")[0].equals("wrongmdp")) {
							wrongmdp=1 ;
							System.out.println("[Reception wrongmdp "+username+"]");
							closechat() ;

						}
						if (info.startsWith("notmodif")) {
							notmodif=1 ;
							//for (int i=0;i<10000000;i++) {}
						}

					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		inimsaageThread.start();

	}
	}
	


	// Groupechat message
	public void groupechat(String name, String msg) {
		int destinationport = 0;
		for (User user : users.getUsers()) {
			if (user.get_Name().equals(name)) {
				destinationport = user.get_Port();
			}
		}

		if (destinationport != 0) {
			try {
				Session s = new Session(InetAddress.getByName("localhost"), destinationport);
				s.sendmesssage("*groupechat/" + username + "/" + msg);

			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	
	// Envoyer au destinataire,on va char avec lui  (*chat+author+son port....)
	public void chatWithOne(String nom) {

		int destinationport = 0;
		InetAddress clientAdress = null;
		for (User user : users.getUsers()) {
			if (user.get_Name().equals(nom)) {
				destinationport = user.get_Port();
				clientAdress = user.get_Address();
			}
		}
		
		System.out.println("Test3+++++++++++++++  destination port"+destinationport);
		
		String usernameString = getUserName();
		if (destinationport != 0) {
			Session s;
			//s = new Session(InetAddress.getByName("localhost"), destinationport);
			s = new Session(clientAdress, destinationport);
			s.sendmesssage("*chat/" + usernameString + "/" + listeningport);

		}

	}
	
	
	public static void closeuser() {
		System.out.println("closed");
		running = false;
		try {
			mutiThread.stop();
			inimsaageThread.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String getUserName() {
		return username;
	}

	public int getListeningport() {
		return listeningport;
	}

	public List_users getUsers() {
		return users;
	}
	
	public Client getClient() {
		System.out.println("Test11--------------------是否真的  get client");
		while(client==null) {
			
		}
		
		System.out.println("Test10--------------------是否真的  get client"+client.getPort());
		return client;
	}

	public Server getServer() {
		return server;
	}
	
	public int get_samename() {
		return samename ;
	}
	public int get_sameport() {
		return sameport ;
	}

	public void maj_chat (String  username2, int port2) throws SocketException {
		listeningport=port2 ;
		username=username2;
		ds = new DatagramSocket(listeningport);
		samename=0 ;
		sameport=0 ;
	}
	
	public void closechat() throws SocketException {
		running=false ;
		this.users.clear_users();
		this.bdd_users.clear_users();
		if (ds_created==1) {
			this.ds.disconnect();
			this.ds.close();
		}
		System.out.println("[Closechat de "+username+"]");
		//ds=null; 
		//ds = new DatagramSocket (99999) ;
		for(int c=0; c<100000000;c++) {}
	}


	public int get_wrongmdp() {
		return wrongmdp;
	}

	public int get_wrongpseudo() {
		return wrongpseudo;
	}
	public void rst_wrongmdp() {
		wrongmdp=0;
	}
	public int get_notmodif() {
		return notmodif;
	}
	public void rst_notmodif() {
		notmodif=0;
	}
	
	

}
