package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Application.Server;
import Application.Client.ListenServerMes;

public class Server {
	
	private User user;
	private Chat chat;
	private ServerSocket socket;
	private int port;
	private Socket link;
	private Socket socket2;
	private PrintWriter out;
	private BufferedReader inputBuff;
	private CommunicateThread cThread;
	//private ArrayList<User> listUserConnecte = new ArrayList<User>();
	private String pseudo;
	
	public Server(int numPort,String name) {
		this.port = numPort;
		this.pseudo=name ;
	}
	
	public Server(int numPort, User u, Chat c) {
		this.port = numPort;
		this.user = u;
		this.chat = c;
	}
	
	
	public void Startlistenning() {
		new Thread(new Runnable() {
			public void run() {
				try {
					socket = new ServerSocket(port);
				    
					while (true) {
						link= socket.accept();
					    System.out.println("["+pseudo+": Waiting for someone to connect ...]");
						cThread = new CommunicateThread(link);
						cThread.start();
		            }

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void sendMesFromServer(String message) {
		this.out.println(message);
	    this.out.flush();
	    if(message.equals("ServerFinishChat")) {
	    	closeAll();
	    }	    	  
	}

	public void closeAll() {
		try {
			this.socket.close();
			this.link.close();
			this.out.close();
			this.inputBuff.close();
			cThread.stop();
			System.out.println("Reussir fermer server");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public class CommunicateThread extends Thread {
		public CommunicateThread(Socket s) {
			socket2 = s;
			try {
				out = new PrintWriter(socket2.getOutputStream(),true);
				inputBuff =new BufferedReader(new InputStreamReader(socket2.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			int flag = 0;
			super.run();
			String result = null;
			try {
				while ((result = inputBuff.readLine()) != null) {
					if(result.equals("ExpediteurFinishChat")) {
						System.out.println("Capter le     ExpediteurFinishChat");
						chat.getUsers().remove(user);
						inputBuff.close();
						out.close();
						link.close();
						break;
					}
					
					if(result.equals("showuser")) {
						chat.printUserConnecte();
					}
					
					/*if(flag++ ==0) {  //La 1er fois on connecte
						
						//System.out.println("La premiere fois"+user.get_Name()+"on connecte");
						//chat.getUsers().add(user);
						
					}else {*/
						//System.out.println( "------------------"+socket2.getPort());
						//System.out.println("Form Client[port:" + socket2.getPort()
						//+ "] Content is : " + result);
						//System.out.println( "-----"+socket2.getPort());
						sendMesFromServer(pseudo+": Message well received from "+socket2.getPort()); //A changer par le pseudo au lieu de num de Client


					//}
					//inputBuff.close();
					//out.close();
					//socket2.close();
	
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)throws IOException {

	 }
	
	
	public void BroadC() {
		
	}
	
}
