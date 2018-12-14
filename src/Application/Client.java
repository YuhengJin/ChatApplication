package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket clientSocket;
	private BufferedReader input;
	
	
	public Client(int numPort) throws UnknownHostException, IOException {
		//ServerSocket class

		System.out.println(numPort);
		this.clientSocket = new Socket(InetAddress.getLocalHost(),1111);
	
	}	

//	public  void receive (Client client) throws IOException{
//		
//		//Client client = new Client(1234);	
//		this.input =  new BufferedReader(new InputStreamReader(client.clientSocket.getInputStream())); 
//		String answer = input.readLine() ;
//		System.out.println(answer);
//		
	//}
	
//	public static void main(String[] args) throws IOException {
//		Client client = new Client(1239);
//
//  		BufferedReader input =  new BufferedReader(new InputStreamReader(client.clientSocket.getInputStream())); 
//		String answer = input.readLine() ;
//		System.out.println(answer);
//	}
	
	public void closeAll() {
		try {
			this.clientSocket.close();
			this.input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
