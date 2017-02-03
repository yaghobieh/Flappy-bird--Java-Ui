package flappyBird;

import java.awt.Rectangle;

public class Bird {
	public Rectangle bird; // Create the Bird
	public final int WIDTH = 700, HEIGHT = 700; // The size of Frame
	
	public Bird (){  //Creating the size of Bird
		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
	}
	
	/**Return the x, y, width & height Pointer**/
	public int birdX(){
		return this.bird.x;
	}
	public int birdY(){
		return this.bird.y;
	}
	public int birdWidth(){
		return this.bird.width;
	}
	public int birdHeight(){
		return this.bird.height;
	}
	/**Change Values of x, y, width & height**/
	public void setY(int x){
		this.bird.y = x;
	}
	public void setX(int x){
		this.bird.x = x;
	}
	/**Add to Values of x, y, width & height**/
	public void addToY(int x){
		this.bird.y += x;
	}
	public void addToX(int x){
		this.bird.x += x;
	}
}
