package Application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server_UDP {

	DatagramSocket socket;
	String pseudo ;
	int numPortBC ;
	int numPortMC ;
    MulticastSocket mcSocket = null;

	
	public Server_UDP(int numPort,String name) throws IOException {
		numPortBC=numPort+1000;
		numPortMC=numPort+7000;
	    pseudo=name ;
	    mcSocket = new MulticastSocket(numPortMC);
	}

	
	public void StartListenningBC()  {
		
		new Thread(new Runnable() {
		   public void run() {
		
		    try {	  
		    	
		    	// Discovery request command
		        byte[] buffer = "__DISCOVERY_REQUEST__".getBytes();
		        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		        getReceiveSocket().receive(packet);
		        System.out.println("Discovery package received! -> " + packet.getAddress() + ":" + packet.getPort());
		        // Get received data
		        String data = new String(packet.getData()).trim();
		        if (data.equals("__DISCOVERY_REQUEST__")) { // Check valid command
		            // Send response
		            byte[] response = new byte["__DISCOVERY_RESPONSE".length()];
		            DatagramPacket responsePacket = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());
		            getReceiveSocket().send(responsePacket);
		            System.out.println("Response sent to: " + packet.getAddress() + ":" + packet.getPort());
		        } else {
		            System.err.println("Error, not valid package!" + packet.getAddress() + ":" + packet.getPort());
		        }
		    	
		    	
		    	
		    	
		      /*socket = new DatagramSocket(numPortBC, InetAddress.getByName("0.0.0.0"));
			  socket.setBroadcast(true);
		    	
		      while (true) {
		
		        System.out.println("["+pseudo+": Pret à recevoir des messages en BroadCast ]");
		
		        //Receive a packet
		        byte[] recvBuf = new byte[15000];	//Buffer de reception 
		        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);	//Datagramm
		        System.out.println("test");
		        socket.receive(packet);		//Reception dans le socket
		        System.out.println("[" + pseudo+"Reception du paquet BroadCast: " + new String(packet.getData())+"]");
		
		        
		        //See if the packet holds the right command (message)
		        String message = new String(packet.getData()).trim();
		        if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
		          byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();
		
		          //Send a response
		          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
		          socket.send(sendPacket);
		          //System.out.println(getClass().getName() + ">>>Server: Sent answer packet to: " + sendPacket.getAddress().getHostAddress());
		        }*/
		
		      //}
		
		    } 	finally {}
		   }
   }).start() ;
  
}

	
	public void StartListenningMC() throws IOException  {
		
	 new Thread(new Runnable() {
	    public void run() {
	
		    try {	    
		
		      while (true) {
			    //System.out.println("["+pseudo+" abonné au groupe : ");
			    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
		
			    System.out.println("["+pseudo+": Waiting for a  multicast message...]");
			    mcSocket.receive(packet);
			    String msg = new String(packet.getData(), packet.getOffset(), 
			        packet.getLength());
			    System.out.println("["+pseudo+": Multicast Reception of:" + msg+"]");
		
			    //mcSocket.leaveGroup(mcIPAddress);
			    //mcSocket.close();
		      }
		    
		    } catch (Exception e) {} finally {}
	    }
	}).start() ;
	}
	   
	
	public MulticastSocket getMCSocket(){
		return this.mcSocket;
	}
}



