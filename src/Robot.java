import java.util.EnumMap;
import java.util.HashMap;

public class Robot {
	public enum SPEED {FAST, SLOW, REALY_SLOW, STOPPED, BACKWARD_REALY_SLOW, BACKWARD_SLOW, BACKWARD_FAST}
	private enum STATE {ROLLING, STOPPED}
	private String name;
	private HashMap<Integer, EnumMap<SPEED, Integer>> motors;
	private RobotConnector conn;
	private STATE state;
	private SPEED roling_speed;
	private static final int S_LEFT = 1;
	private static final int S_RIGHT = 2;
	private static final int S_TOURNE = 3;

	public Robot(String name, RobotConnector connector) {
		this.name = name;
		this.conn = connector;
		this.motors = new HashMap<>();
		this.motors.put(S_LEFT, motorsInitCal(S_LEFT));
		this.motors.put(S_RIGHT, motorsInitCal(S_RIGHT));
		this.motors.put(S_TOURNE, motorsInitCal(S_TOURNE));
		this.state = STATE.STOPPED;
		this.roling_speed = SPEED.STOPPED;
	}

	public boolean connect() {
		return conn.connect();
	}
	
	public boolean disconect() {
		return conn.disconnect();
	}
	
	public void move(SPEED speed) {
		if (speed == SPEED.STOPPED) this.stop();
		this.moveMotor(S_LEFT, speed);
		this.moveMotor(S_RIGHT, speed);
		this.state = STATE.ROLLING;
		this.roling_speed = speed;
	}
	
	public void stop() {
		this.moveMotor(S_LEFT, SPEED.STOPPED);
		this.moveMotor(S_RIGHT, SPEED.STOPPED);
		this.state = STATE.STOPPED;
		this.roling_speed = SPEED.STOPPED;
	}
	
	public void leftTurn(SPEED speed) {
		switch (state) {
		case ROLLING:
			this.moveMotor(S_LEFT, SPEED.STOPPED);
			this.moveMotor(S_RIGHT, this.roling_speed);
			break;
		case STOPPED:
			this.moveMotor(S_LEFT, reverseSpeed(speed));
			this.moveMotor(S_RIGHT, speed);
			break;
		}
	}

	public void rightTurn(SPEED speed) {
		switch (this.state) {
		case ROLLING:
			this.moveMotor(S_LEFT, this.roling_speed);
			this.moveMotor(S_RIGHT, SPEED.STOPPED);
			break;
		case STOPPED:
			this.moveMotor(S_LEFT, speed);
			this.moveMotor(S_RIGHT, reverseSpeed(speed));
			break;
		}
	}

	public void stopTurn() {
		switch (this.state) {
		case ROLLING:
			this.moveMotor(S_RIGHT, this.roling_speed);
			this.moveMotor(S_LEFT, this.roling_speed);
			break;
		case STOPPED:
			this.stop();
			break;
		}		
	}
	
	public void tourne(SPEED speed) {
		this.moveMotor(S_TOURNE, speed);
	}

	
	public void tourneStop() {
		this.moveMotor(S_TOURNE, SPEED.STOPPED);
	}

	@Override
	public String toString() {
		return name;
	}

	public void rawMotorInput(int motor, int speed) {
		this.moveMotor(motor, speed);
	}

	public HashMap<Integer, EnumMap<SPEED, Integer>> getMotors() {
		return this.motors;
	}

	private EnumMap<SPEED, Integer> motorsInitCal(int motor) {
		EnumMap temp;
		switch(motor) {
			case S_LEFT:
				temp = new EnumMap<SPEED, Integer>(SPEED.class);
				temp.put(SPEED.FAST, 2500);
				temp.put(SPEED.SLOW, 1544);
				temp.put(SPEED.REALY_SLOW, 1522);
				temp.put(SPEED.STOPPED, 1500);
				temp.put(SPEED.BACKWARD_REALY_SLOW, 1478);
				temp.put(SPEED.BACKWARD_SLOW, 1456);
				temp.put(SPEED.BACKWARD_FAST, 500);
				break;
			case S_RIGHT:
				temp = new EnumMap<SPEED, Integer>(SPEED.class);
				temp.put(SPEED.FAST, 500);
				temp.put(SPEED.SLOW, 1456);
				temp.put(SPEED.REALY_SLOW, 1478);
				temp.put(SPEED.STOPPED, 1500);
				temp.put(SPEED.BACKWARD_REALY_SLOW, 1522);
				temp.put(SPEED.BACKWARD_SLOW, 1544);
				temp.put(SPEED.BACKWARD_FAST, 2500);
				break;
			case S_TOURNE:
				temp = new EnumMap<SPEED, Integer>(SPEED.class);
				temp.put(SPEED.FAST, 1554);
				temp.put(SPEED.SLOW, 1530);
				temp.put(SPEED.REALY_SLOW, 1511);
				temp.put(SPEED.STOPPED, 1500);
				temp.put(SPEED.BACKWARD_REALY_SLOW, 1479);
				temp.put(SPEED.BACKWARD_SLOW, 1445);
				temp.put(SPEED.BACKWARD_FAST, 1438);
				break;
			default:
				throw new IllegalArgumentException("Motor number unknown");
		}
		return temp;
	}

	private int getMotorSpeedValue(int motor, SPEED speed) {
		EnumMap<SPEED, Integer> motor_speeds = this.motors.get(motor);
		return motor_speeds.get(speed);
	}

	private SPEED reverseSpeed(SPEED speed) {
		SPEED ret = null;
		switch (speed) {
			case STOPPED:
				ret = SPEED.STOPPED;
				break;
			case FAST:
				ret = SPEED.BACKWARD_FAST;
				break;
			case SLOW:
				ret = SPEED.BACKWARD_SLOW;
				break;
			case REALY_SLOW:
				ret = SPEED.BACKWARD_REALY_SLOW;
				break;
			case BACKWARD_FAST:
				ret = SPEED.FAST;
				break;
			case BACKWARD_SLOW:
				ret = SPEED.SLOW;
				break;
			case BACKWARD_REALY_SLOW:
				ret = SPEED.REALY_SLOW;
				break;
			default:
				throw new IllegalArgumentException("Unknown speed");
		}
		return ret;
	}

	private void moveMotor(int motor, int pos) {
		this.conn.moveServo(motor, pos);
	}

	private void moveMotor(int motor, SPEED speed) {
		int speed_value = getMotorSpeedValue(motor, speed);
		this.conn.moveServo(motor, speed_value);
	}
}
