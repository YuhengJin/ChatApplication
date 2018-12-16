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
				    socket = new ServerSocket(2020);
				    
				    link= socket.accept();
				    System.out.println("-----Capter la connection!!!----");
					cThread = new CommunicateThread(link);
					cThread.start();
					
					
					sendMesFromServer("Server qui parle Cote Server");
					sendMesFromServer("ServerFinishChat");

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
						out.close();
						inputBuff.close();
						link.close();
						break;
					}else {
						System.out.println("Client say :    " + result);
					}
					
					
					
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 
	
	 public static void main(String[] args)throws IOException {
	        Server s = new Server(1500);//启动服务端
	        s.Startlistenning();
	        
	 }
	
	

}
