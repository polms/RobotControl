public class Principale {
	
	public static void main(String[] args) {
		System.out.println("Started robot controller");
		RobotConnector conn = new RobotConnector("192.168.42.1", 1234);
		Robot robot = new Robot("Roby", conn);
		new Window(robot);
	}
}
