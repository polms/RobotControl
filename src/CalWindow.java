import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalWindow extends JFrame {
    private static final long serialVersionUID = -8255319694373975038L;
    private Robot robot;
    private JSlider slider;
    private JComboBox<Object> motors;

    public CalWindow(Robot robot) {
        this.robot = robot;
        this.setTitle("Moteurs");
        this.setSize(976, 102);
        this.buildWindow();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void buildWindow() {
        JPanel pannel = new JPanel();
        JLabel help = new JLabel("<html>Cette fenêtre actionne les moteurs du robot</html>");
        this.motors = new JComboBox<>(robot.getMotors().keySet().toArray());
        this.slider = new JSlider(500, 2500);
        JLabel slider_v = new JLabel(""+this.slider.getValue());
        SpeedActionListener button_act = new SpeedActionListener();

        JPanel speed_pannel = new JPanel();
        Robot.SPEED[] arr = Robot.SPEED.values();
        for (Robot.SPEED anArr : arr) {
            JButton b = new JButton(anArr.name());
            b.addActionListener(button_act);
            speed_pannel.add(b);
        }
        this.motors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EnumMap<Robot.SPEED, Integer> motor = robot.getMotors().get(motors.getSelectedItem());
                button_act.setMotor(motor);
            }
        });
        this.motors.setSelectedIndex(0);

        this.slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                robot.rawMotorInput((Integer)motors.getSelectedItem(), slider.getValue());
                slider_v.setText(""+slider.getValue());
            }
        });

        pannel.add(help);
        pannel.add(this.motors);
        pannel.add(speed_pannel);
        pannel.add(this.slider);
        pannel.add(slider_v);
        this.add(pannel);
    }

    private class SpeedActionListener implements ActionListener {
        private EnumMap<Robot.SPEED,Integer> motor;

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (this.motor != null)
                slider.setValue(motor.get(Robot.SPEED.valueOf(((JButton)actionEvent.getSource()).getText())));
        }

        public void setMotor(EnumMap<Robot.SPEED,Integer> motor) {
            this.motor = motor;
        }
    }
}