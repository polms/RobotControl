import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

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
		JButton call_butt = new JButton("cal");
		call_butt.setFocusable(false);
		call_butt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				new CalWindow(robot);
			}
		});
		pannel.add(help);
		pannel.add(call_butt);
		this.add(pannel);
	}
}
