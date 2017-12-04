import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Window extends JFrame {
	private static final long serialVersionUID = -8255319694373975038L;
	private Robot robot;
	
	public Window(Robot robot) {
		this.robot = robot;
		this.setTitle("Controleur de robot");
		this.setSize(400, 400);
		this.addKeyListener(new KeyBoardControler(this.robot));
		this.buildWindow();
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter()
						        {
						            @Override
						            public void windowClosing(WindowEvent e)
						            {
						                robot.disconect();
						                super.windowClosing(e);
						            }
						        });
	}
	
	public void buildWindow() {
		JPanel pannel = new JPanel();
		JLabel help = new JLabel("<html>Cette fenêtre permet de contrôler le robot<br/>"
								+KeyBoardControler.keymap().replaceAll("\n", "<br/>")+"</html>");
		pannel.add(help);
		this.add(pannel);
	}
}
