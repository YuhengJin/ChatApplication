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

public class Client {
	
	private ListenServerMes lsmThread;
	private Socket socket;
	private int port;
	private PrintWriter out;
	private BufferedReader inputBuff;
	
	
	public Client(int numPort) {
			this.port = numPort;
			
	}
	
	public void startClient() {
		try {
			
			socket = new Socket("127.0.0.1",2021);
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
		this.out.println(mes);
	    this.out.flush();
	    
	    System.out.println("client send msg : " + mes);
	    if(mes.equals("ExpediteurFinishChat")) {
	    	closeAll();
	    }
	    	
	    
	    
	    
	}
	
	public int getPortClient() {
		return socket.getLocalPort();
	}
	
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
					if(result.equals("RecieveFinishChat")) {
						out.close();
						inputBuff.close();
						socket.close();
						break;
					}else {
						
						System.out.println("Server say :    " + result);
				
					}
					
	            
					
				
					//inputBuff.close();
					//out.close();
					
					//socket.close();
					
					
					
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	
		}
	}
	
	public static void main(String[] args) {
        /*try {
            Client c1 = new Client(1500);//启动客户端
            System.out.println("Client[port: 与服务端建立连接...");
            c1.startClient();
            c1.sendMessage ("Cient qui parle Cote Client ");
            
            
            Client c2 = new Client(1500);//启动客户端
            System.out.println("Client 2 est cree");
            c2.startClient();
            c2.sendMessage ("CLient 2 parle");
            
            
        }catch (Exception e) {
        }*/
		
		for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    	Client c1 = new Client(1502);//启动客户端
                        System.out.println("Client[port: 与服务端建立连接...");
                        c1.startClient();
                        c1.sendMessage ("Hello serve!"+ "qui parle Cote Client " +c1.getPortClient());
                    	
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

		
		
    }
	}
	
	
	
	
}
	







