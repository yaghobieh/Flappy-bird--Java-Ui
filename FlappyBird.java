package flappyBird;

import javax.swing.Timer;
import javax.tools.ToolProvider;
import javax.xml.namespace.QName;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
/**Import Java**/
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;

public class FlappyBird implements ActionListener, MouseListener, KeyListener, ControlAble {
	public static FlappyBird flappyBird; // Create static Item of Flappy
	public final int WIDTH = 700, HEIGHT = 700; // The size of Frame
	public GameReading gameReading;
	public Bird bird; // Create the Bird
	public ArrayList<Rectangle> columns; // List of columns
	public Random random;
	public int ticksm, yMotion, score = 0; // for The Action/ Move of the bird and score
	public boolean gameOver, started; //Start and GameOver Boolean 
	public ScoreArchive S = ScoreArchive.getReference(); //Get the Reference of Singleton ScoreArchive
	private int scoreAc = 0;
	
	public FlappyBird() { // Create the Visibility
		beginStation();
	}
	
	
	public void beginStation(){
		JFrame gameFrame = new JFrame();
		Timer timer = new Timer(20, this);
		gameReading = new GameReading();
		random = new Random();

		gameFrame = new JFrame();
		gameFrame.add(gameReading); // Add the game Calling
		gameFrame.setResizable(false); // Can't change the size
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(WIDTH, HEIGHT);
		gameFrame.addMouseListener(this); //Mouse Listener
		gameFrame.addKeyListener(this); //Key Listener 
		gameFrame.setTitle("Flappy Bird");
		gameFrame.setVisible(true);

		// The size of red Bird Runner
		bird = new Bird();
		// The List array of columns size
		columns = new ArrayList<Rectangle>();

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
	}
	public void addColumn(boolean start) {
		int lineSpace = 270; // Space
		int tempWidth = 100; // Width of
		int tempHeight = 60 + random.nextInt(300);
		if (start) {
			// !!Remember everything in Java go to Left
			columns.add(new Rectangle(WIDTH + tempWidth + columns.size() * 300, HEIGHT - tempHeight - 120, tempWidth,
					tempHeight));
			columns.add(new Rectangle(WIDTH + tempWidth + (columns.size() - 1) * 300, 0, tempWidth,
					HEIGHT - tempHeight - lineSpace));
		} else { // One column inside the List/ size get = start + first because
					// of that
					// Make -1
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - tempHeight - 120, tempWidth,
					tempHeight));
			columns.add(
					new Rectangle(columns.get(columns.size() - 1).x, 0, tempWidth, HEIGHT - tempHeight - lineSpace));
		}
	}

	public void paintColumn(Graphics g, Rectangle c) {
		g.setColor(Color.green.darker()); // Dark Green Column
		//g.fillRect(c.x, c.y, c.width, c.height);
		g.fill3DRect(c.x, c.y, c.width, c.height, true);
	}
	
	public void jumpping(){
		if (gameOver){
			// The size of red Bird Runner
			bird = new Bird();
			// The List array of columns size
			columns.clear();
			yMotion = 0;
			score = 0; 
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false;
		}
		if (!started){
			started = true;
		}
		else if (!gameOver) {
			if ( yMotion > 0)
				yMotion = 0;
			yMotion -=10;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		int speed = 8;
		ticksm++;
		if (started) {
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}
			if (ticksm % 2 == 0 && yMotion < 15) {
				yMotion += 2; // Add 2 to Motion of
			}
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);

				if (column.x + column.width < 0) {
					columns.remove(column);

					if (column.y == 0) {
						addColumn(false);
					}
				}
			}
			bird.addToY(yMotion); 

			for (Rectangle C : columns) {
				if (C.y == 0 && bird.birdX() + bird.birdWidth() / 2 > C.x + C.width/2 - 10 && bird.birdX() + 
						bird.birdWidth() /2 < C.x + C.width /2 + 10){
					score++;
				}
				if (C.intersects(bird.birdX(), bird.birdY(), bird.birdWidth(), bird.birdHeight())) {
					gameOver = true;
					
					if ( bird.birdX() <= C.x)
						bird.setX(C.x - bird.birdWidth());//Stop the bird when Game-Over
					else{
						if ( C.y != 0)
							bird.setY(C.y - bird.birdHeight());
						else if (bird.birdY() < C.height)
							bird.setY(C.height);
					}
				}
			}
			if (bird.birdY() > HEIGHT - 120 || bird.birdY() < 0) {
				gameOver = true;
			}
			if ( bird.birdY() + yMotion >= HEIGHT -120 ){
				bird.setY(HEIGHT- 120 - bird.birdHeight()); //Making the effect of falling,
			    // don't fall at all
			}
		}
		gameReading.repaint();
	}
	/** @Print the Option into the Frame **/
	public void repaint(Graphics g) {
		g.setColor(Color.cyan); // BackGround of the Frame
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.orange); // Button of the Frame Color and Model
		g.fillRect(0, HEIGHT - 120, WIDTH, 150);

		g.setColor(Color.green); // Line of Green
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);

		g.setColor(Color.red); // The flappyBird Red and size of bird();
		g.fillRect(bird.birdX(), bird.birdY(), bird.birdWidth(), bird.birdHeight());

		for (Rectangle C : columns) {
			paintColumn(g, C);
		}
		//Making the font of game over
		g.setColor(Color.white);
		g.setFont(new Font("David", 1, 25));
		
		if ( !started ){ //If not game- over
			g.drawString("Click 'Space' to Start", 75, HEIGHT/ 2 - 50);
			g.drawString("Click 'ESC' for exit" , 75, HEIGHT/ 2 - 100);
		}
		if ( gameOver ){ //If game over
			g.drawString("Game Over!", 75, HEIGHT/ 3 - 25);
			g.setColor(Color.red);
			g.setFont(new Font("David", 1, 25));
			g.drawString("Click 'Space' to Start", 75, HEIGHT/ 2 - 50);
			g.drawString("Click 'Esc' for exit" , 75, HEIGHT/ 2 - 100);
			
			g.setColor(Color.white);
			g.setFont(new Font("David", 1, 25));
			g.drawString("Score: " +String.valueOf(scoreAc)+ " | The High score was:"
					+S.getReference().getMax(), WIDTH /12 -25, 650);
			scoreAc = score; //Adding the last score to ScoreArchive
		}
		
		if (!gameOver && started){
			g.setColor(Color.white);
			g.setFont(new Font("David", 1, 25));
			g.drawString("Score: " +String.valueOf(score), WIDTH /12 -25, 650);
		}
		S.addScore(scoreAc);
	}

	public static void main(String args[]) {
		flappyBird = new FlappyBird(); // Create the Static Flappy
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		jumpping(); //For moving 
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if ( e.getKeyCode() == KeyEvent.VK_SPACE)
			jumpping();
		if ( e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}
	/**Not in use**/
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}