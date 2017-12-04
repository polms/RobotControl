
public class Principale {
	
	public static void main(String[] args) {
		System.out.println("Started robot controller");
		RobotConnector conn = new RobotConnector("localhost", 1234);
		Robot robot = new Robot("Roby", conn);
		robot.connect();
		new Window(robot);
	}

}
