import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardControler implements KeyListener {
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	public static final int FORWARD = KeyEvent.VK_UP;
	public static final int BACKWARD = KeyEvent.VK_DOWN;
	public static final int TO_CLK = KeyEvent.VK_E;
	public static final int TO_CCLK = KeyEvent.VK_A;
	public static final int RECONNECT = KeyEvent.VK_P;
	private Robot robot;
	
	public KeyBoardControler(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void keyPressed(KeyEvent ev) {
		int keyCode = ev.getKeyCode();
		boolean slow = ev.isShiftDown();
		
		switch (keyCode) {
		case FORWARD:
			robot.forward(slow ? Robot.SLOW : Robot.FAST);
			break;
		case BACKWARD:
			robot.forward(slow ? Robot.SLOW : Robot.FAST);
			break;
		case LEFT:
			robot.leftTurn(Robot.FAST_TURN);
			break;
		case RIGHT:
			robot.rightTurn(Robot.FAST_TURN);
			break;
		case TO_CLK:
			robot.tourneClk(Robot.SLOW);
			break;
		case TO_CCLK:
			robot.tourneCclk(Robot.SLOW);
			break;
		case RECONNECT:
			System.out.println("Trying to reconnect");
			robot.disconect();
			robot.connect();
			break;
		}
			
	}

	@Override
	public void keyReleased(KeyEvent ev) {
		int keyCode = ev.getKeyCode();
		//boolean slow = ev.isShiftDown();
		
		switch (keyCode) {
		case FORWARD:
		case BACKWARD:
			robot.stop();
			break;
		case LEFT:
		case RIGHT:
			robot.stopTurn();
			break;
		case TO_CLK:
		case TO_CCLK:
			robot.tourneStop();
			break;
			
		}
	}

	@Override
	public void keyTyped(KeyEvent ev) {
		
	}
	
	public static final String keymap() {
		return "Avancer: "+KeyEvent.getKeyText(FORWARD)+
				"\nReculer: "+KeyEvent.getKeyText(BACKWARD)+
				"\nDroite: "+KeyEvent.getKeyText(RIGHT)+
				"\nGauche"+KeyEvent.getKeyText(LEFT)+
				"\nTourniquet: "+KeyEvent.getKeyText(TO_CLK)+"/"+KeyEvent.getKeyText(TO_CCLK)+
				"\nReconnect: "+KeyEvent.getKeyText(RECONNECT)+
				"\nSpeed: shift";
	}
}
