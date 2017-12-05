import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RobotConnector {
	private Socket mSocket;
	private DataOutputStream mDos;
	private final String hostname;
	private final int port;
	
	public RobotConnector(String hostname, int port)
	{
		this.hostname = hostname;
		this.port = port;
	}
	
	/**
	 * Connection au serveur dans un robot
	 * @return True si la connextion est établie
	 */
	public boolean connect() {
		
		try {
			System.out.println("Trying to connect to "+this.hostname+":"+this.port);
			mSocket = new Socket(this.hostname, this.port);
			mDos = new DataOutputStream(mSocket.getOutputStream());
			System.out.println("Connected");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean disconnect() {
		try {
			if (mDos != null) {
			    mDos.close();
			    return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void sendCommand(String command)
	{
		if (this.mSocket.isConnected()) {
			try {
				mDos.writeBytes(command);
				mDos.flush();
			} catch (IOException e) {
				System.out.println("disconnected "+e.getLocalizedMessage());
				this.disconnect();
			}
		} else {
			System.out.print("Couldn't send command, socket closed. ("+command+")");
		}
	}
	
	/**
	 * Envoie une pwm à un moteur
	 * @param id id du moteur entre 0 et 31
	 * @param value Valeur de la PWM entre 500 et 2500
	 */
	public void moveServo(int id, int value)
	{
		assert id >= 0 && id <= 31;
		assert value >= 500 && value <= 2500;
		sendCommand("#"+id+"P"+value+"\r");
	}
	
}
