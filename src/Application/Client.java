package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Interface.Dialogue;
import Interface.Graphique;

public class Client {
	
	private ListenServerMes lsmThread;
	private Socket socket;
	private int port;
	private PrintWriter out;
	private BufferedReader inputBuff;
	private InetAddress address;
	private User user;

	private Chat chat;
	Dialogue d;
	

	public Client(int numPort) {

			this.port = numPort;
			
	}
	
	public Client(InetAddress address,int numPort) {
		this.address = address;
		this.port = numPort;
	}
	
	
	public Client(int numPort,User u,Chat c) {
		this.port = numPort;
		this.user = u;
		this.chat =c;
		
		
	}
	
	public void startClient() {
		try {
			
		
			d = new Dialogue(chat, user, "Client");
			
			socket = new Socket(this.address,this.port);
			//System.out.println("Port CLient est "+this.port);
			//socket = new Socket("127.0.0.1",this.port);
			// 60s超时
            //socket.setSoTimeout(60000);
			
			/*System.out.println("==============");
			System.out.println("====="+socket.getInetAddress().toString());
			System.out.println(socket.getLocalAddress().toString());*/
			
			
			this.out =new PrintWriter(socket.getOutputStream(),true);
			this.inputBuff =new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			this.lsmThread= new  ListenServerMes();
			this.lsmThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		
	}
	
	/*
	 * Send the message that client prepared to transfer 
	 */
	public  void sendMessage (String mes){
		
		/*System.out.println("++++++++");
		System.out.println("++++"+socket.getInetAddress().toString());
		System.out.println(socket.getLocalAddress().toString());*/
		
		
		this.out.println(mes);
	    this.out.flush();
	    if(mes.equals("ExpediteurFinishChat")) {
	    	closeAll();
	    }

	}
	
	public int getPortClient() {
		return socket.getLocalPort();
	}
	
	/*public String getname() {
		return this.pseudo ;
	}*/
	
	public void closeAll() {
		try {
			this.socket.close();
			this.out.close();
			this.inputBuff.close();
			this.lsmThread.stop();
			System.out.println("Reussir a kill the client");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/*
	 * Get the information from the server
	 */
	public class ListenServerMes extends Thread {
		@Override
		public void run() {
			super.run();
			String result = null;
			try {
				while ((result = inputBuff.readLine()) != null) {
					if(result.equals("bye client")) {
						inputBuff.close();
						out.close();
						socket.close();
						//d.showmessageialog("the chat is finished");
						break;
					}else {
						

						d.showmessagerecu(user.get_Name() + " to "
								+ chat.getUserName()+ " : " + result);
						System.out.println("Recu[CLient] Server say :    " + result);

				
					} 

				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	
		}
	}
	
	/*public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Client c1 = new Client(1502);// 启动客户端
						System.out.println("Client[port: Connection with the server...");
						c1.startClient();
						
						c1.sendMessage("Hello server!" + "parle Cote Client " + c1.getPortClient());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

		}
	}*/
	
	

	
	
}

