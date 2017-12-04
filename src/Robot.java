
public class Robot {
	public static final int S_LEFT = 1;
	public static final int S_RIGHT = 2;
	public static final int S_TOURNE = 3;
	public static final int FAST = 2500;
	public static final int SLOW = 1200;
	public static final int REALY_SLOW = 1000;
	public static final int BACKWARD = 550;
	public static final int STOPPED = 500;
	public static final int FAST_TURN = 2500;
	private static enum STATE {ROLLING, STOPPED}; 
	private String name;
	private RobotConnector conn;
	private STATE state;
	private int roling_speed;
	
	public Robot(String name, RobotConnector connector) {
		this.name = name;
		this.conn = connector;
		this.state = STATE.STOPPED;
		this.roling_speed = 0;
	}
	
	public boolean connect() {
		return conn.connect();
	}
	
	public boolean disconect() {
		return conn.disconnect();
	}
	
	public void forward(int speed) {
		this.conn.moveServo(S_LEFT, speed);
		this.conn.moveServo(S_RIGHT, speed);
		this.state = STATE.ROLLING;
		this.roling_speed = speed;
	}
	
	public void backward(int speed) {
		this.conn.moveServo(S_LEFT, speed);
		this.conn.moveServo(S_RIGHT, speed);
		this.state = STATE.ROLLING;
		this.roling_speed = speed;
	}
	
	public void stop() {
		this.conn.moveServo(S_LEFT, STOPPED);
		this.conn.moveServo(S_RIGHT, STOPPED);
		this.state = STATE.STOPPED;
		this.roling_speed = STOPPED;
	}
	
	public void leftTurn(int speed_diff) {
		switch (state) {
		case ROLLING:
			this.conn.moveServo(S_LEFT, this.roling_speed - speed_diff);
			this.conn.moveServo(S_RIGHT, this.roling_speed);
			break;
		case STOPPED:
			this.conn.moveServo(S_LEFT, REALY_SLOW);
			this.conn.moveServo(S_RIGHT, FAST);
			break;
		}
		this.state = STATE.ROLLING;
	}
	
	public void rightTurn(int speed_diff) {
		switch (this.state) {
		case ROLLING:
			this.conn.moveServo(S_RIGHT, this.roling_speed - speed_diff);
			this.conn.moveServo(S_LEFT, this.roling_speed);
			break;
		case STOPPED:
			this.conn.moveServo(S_RIGHT, REALY_SLOW);
			this.conn.moveServo(S_LEFT, FAST);
			break;
		}
		this.state = STATE.ROLLING;
	}
	
	public void stopTurn() {
		switch (this.state) {
		case ROLLING:
			this.conn.moveServo(S_RIGHT, this.roling_speed);
			this.conn.moveServo(S_LEFT, this.roling_speed);
			break;
		case STOPPED:
			this.stop();
			break;
		}		
	}
	
	public void tourneClk(int speed) {
		this.conn.moveServo(S_TOURNE, speed);
	}
	
	public void tourneCclk(int speed) {
		this.conn.moveServo(S_TOURNE, speed);
	}
	
	public void tourneStop() {
		this.conn.moveServo(S_TOURNE, STOPPED);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
