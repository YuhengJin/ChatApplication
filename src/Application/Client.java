package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	
	public  Socket clientSocket;
	
	
	public Client(int numPort) {
		//ServerSocket class
		try {
				clientSocket = new Socket("127.0.0.1",numPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public static void main(String[] args) throws IOException {
		Client client = new Client(1234);

		
		BufferedReader input =  new BufferedReader(new InputStreamReader(client.clientSocket.getInputStream())); 
		String answer = input.readLine() ;
		System.out.println(answer);
		
		client.clientSocket.close();

	}

}
