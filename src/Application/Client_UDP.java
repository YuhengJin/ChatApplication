package Application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client_UDP {

	String pseudo ;

	public Client_UDP() {
		
	}
	public void runBC(String name, String msg) throws SocketException {
		pseudo=name ;
		// Find the server using UDP broadcast
		try {
		  //Open a random port to send the package
		  DatagramSocket c = new DatagramSocket();
		  c.setBroadcast(true);	  
		  byte[] sendData = msg.getBytes();	
		  
		  //Try the 255.255.255.255 first
		  try {
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
		    c.send(sendPacket);
		    System.out.println("[BroadCast depuis "+pseudo+": "+new String (sendPacket.getData())+"]");
			c.close();

		  } catch (Exception e) {
		  } 
		  }finally {}
			  
	
	}	
		
	public void runMC(String mes, InetAddress mcIPAddress) throws IOException {

		
		int mcPort = 12345;
	    DatagramSocket udpSocket = new DatagramSocket();

	    byte[] msg = mes.getBytes();
	    DatagramPacket packet = new DatagramPacket(msg, msg.length);
	    packet.setAddress(mcIPAddress);
	    packet.setPort(mcPort);
	    udpSocket.send(packet);

	    System.out.println("["+pseudo+": Envoi d'un paquet Multicast sur la session"+mcIPAddress.toString());
	    //System.out.println("Exiting application");
	    udpSocket.close();
		 
		  //Wait for a response
		  /*byte[] recvBuf = new byte[15000];
		  DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
		  c.receive(receivePacket);
	
		  //We have a response
		  System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
	
		  //Check if the message is correct
		  String message = new String(receivePacket.getData()).trim();
		  if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
		    //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
		   // Controller_Base.setServerIp(receivePacket.getAddress());
		  }
	
		  //Close the port!
		  c.close();
		 } catch (IOException ex) {
		  //Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
		}  */
	


		
}
}


