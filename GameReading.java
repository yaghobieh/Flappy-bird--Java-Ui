package flappyBird;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameReading extends JPanel
{
	//private static final long serialVersionUID = 1L; //Serial ID
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		FlappyBird.flappyBird.repaint(g);
	}
	
}