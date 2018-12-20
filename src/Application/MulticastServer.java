package Application;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastServer extends QuoteServerThread {
    public static void main(String[] args) throws IOException {
        new MulticastServerThread().start();
    }
    
    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];
                // don't wait for request...just send a quote

                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();
                buf = dString.getBytes();

                InetAddress group = InetAddress.getByName("203.0.113.0");
                DatagramPacket packet;
                packet = new DatagramPacket(buf, buf.length, group, 4446);
                socket.send(packet);

                try {
                    sleep((long)Math.random() * FIVE_SECONDS);
                } 
                catch (InterruptedException e) { }
            }
            catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }
}
