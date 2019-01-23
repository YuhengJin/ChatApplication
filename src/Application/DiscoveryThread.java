package Application;

public class DiscoveryThread implements Runnable {

  public void run() {

  }

  public static DiscoveryThread getInstance() {
    return DiscoveryThreadHolder.INSTANCE;
  }

  private static class DiscoveryThreadHolder {
	  private static final DiscoveryThread INSTANCE = new DiscoveryThread();
  } 

} 