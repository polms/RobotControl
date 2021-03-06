import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyBoardControler implements KeyListener {
	private static final int LEFT = KeyEvent.VK_LEFT;
	private static final int RIGHT = KeyEvent.VK_RIGHT;
	private static final int FORWARD = KeyEvent.VK_UP;
	private static final int BACKWARD = KeyEvent.VK_DOWN;
	private static final int TO_CLK = KeyEvent.VK_E;
	private static final int TO_CCLK = KeyEvent.VK_A;
	private static final int POUSSE_FERME = KeyEvent.VK_T;
	private static final int POUSSE_OUVRE = KeyEvent.VK_R;
	private static final int RECONNECT = KeyEvent.VK_P;
	private enum POUSSE {FERMER, POUSSE1, POUSSE2}
	private POUSSE etat_pousse;
	private Robot robot;
	
	public KeyBoardControler(Robot robot) {
		this.robot = robot;
		etat_pousse = POUSSE.FERMER;
	}
	
	@Override
	public void keyPressed(KeyEvent ev) {
		int keyCode = ev.getKeyCode();
		boolean slow = ev.isShiftDown();
		Robot.SPEED move_speedfw;
		Robot.SPEED move_speedbw;
		if (! ev.isShiftDown()) {
			move_speedfw = Robot.SPEED.REALY_SLOW;
			move_speedbw = Robot.SPEED.BACKWARD_REALY_SLOW;
		} else {
			if (ev.isControlDown()) {
				move_speedfw = Robot.SPEED.FAST;
				move_speedbw = Robot.SPEED.BACKWARD_FAST;
			} else {
				move_speedfw = Robot.SPEED.SLOW;
				move_speedbw = Robot.SPEED.BACKWARD_SLOW;
			}
		}
		switch (keyCode) {
		case FORWARD:
			robot.move(move_speedfw);
			break;
		case BACKWARD:
			robot.move(move_speedbw);
			break;
		case LEFT:
			robot.leftTurn(move_speedfw);
			break;
		case RIGHT:
			robot.rightTurn(move_speedfw);
			break;
		case TO_CLK:
			robot.tourne(move_speedfw);
			break;
		case TO_CCLK:
			robot.tourne(move_speedbw);
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
		case POUSSE_OUVRE:
			switch (etat_pousse) {
			case FERMER:
				robot.pousse2();
				etat_pousse = POUSSE.POUSSE1;
				break;
			case POUSSE1:
				robot.pousse();
				etat_pousse = POUSSE.POUSSE2;
				break;
			}
			break;
		case POUSSE_FERME:
			switch (etat_pousse) {
			case POUSSE1:
				robot.pousseStop();
				etat_pousse = POUSSE.FERMER;
				break;
			case POUSSE2:
				robot.pousse2();
				etat_pousse = POUSSE.POUSSE1;
				break;
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent ev) {

	}
	
	public static String keymap() {
		return "Avancer: "+KeyEvent.getKeyText(FORWARD)+
				"\nReculer: "+KeyEvent.getKeyText(BACKWARD)+
				"\nDroite: "+KeyEvent.getKeyText(RIGHT)+
				"\nGauche:"+KeyEvent.getKeyText(LEFT)+
				"\nPoussoir:"+KeyEvent.getKeyText(POUSSE_FERME)+"/"+KeyEvent.getKeyText(POUSSE_OUVRE)+
				"\nTourniquet: "+KeyEvent.getKeyText(TO_CLK)+"/"+KeyEvent.getKeyText(TO_CCLK)+
				"\nReconnect: "+KeyEvent.getKeyText(RECONNECT)+
				"\nVitesse reduite: shift (controle pour plus d'effet)";
	}
}
