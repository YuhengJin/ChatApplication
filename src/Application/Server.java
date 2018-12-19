package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Application.Server;
import Application.Client.ListenServerMes;

public class Server {
	
	private User user;
	private ServerSocket socket;
	private int port;
	private Socket link;
	private Socket socket2;
	private PrintWriter out;
	private BufferedReader inputBuff;
	private CommunicateThread cThread;
	
	public Server(int numPort) {
		this.port = numPort;
	}
	
	
	
	public void Startlistenning() {
		new Thread(new Runnable() {
			public void run() {
				try {
					//socket = new ServerSocket(port);
				    socket = new ServerSocket(2021);
				    
					while (true) {
						link= socket.accept();
					    System.out.println("-----Capter la connection!!!----");
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
		//out.print("hello Client, I am Server!");
		this.out.println(message);
	    this.out.flush();
	    //this.out.close();
	    System.out.println("server send msg : " + message);
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
			super.run();
			String result = null;
			try {
				//System.out.println("tt"+ inputBuff.readLine());
				while ((result = inputBuff.readLine()) != null) {
					if(result.equals("ExpediteurFinishChat")) {
						System.out.println("Capter le     ExpediteurFinishChat");
						inputBuff.close();
						out.close();
						link.close();
						break;
					}else {
						//System.out.println( "------------------"+socket2.getPort());
						System.out.println("Form Client[port:" + socket2.getPort()
						+ "] Content is : " + result);
						//System.out.println( "-----"+socket2.getPort());
						sendMesFromServer("Hi, bro  how are you！"+socket2.getPort());
						//sendMesFromServer("ServerFinishChat");
						
					
						
						System.out.println(
								"To Client[port:" + socket2.getPort() + "] The response to client succes");
						
						
					}
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
	        Server s = new Server(1502);//Create the server 
	        s.Startlistenning(); //Launch the thread
	        
	 }
	
	

}
