package application;

import javafx.scene.image.Image;

public class Bullet extends Sprite {
	
	//FIXED ATTRIBUTES OF BULLETS
	private final int BULLET_SPEED = 20;
	public final static Image BULLET_IMAGE = new Image("bullet-1-removebg-preview.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);
	public final static int BULLET_WIDTH = 20;
	
	
	public Bullet(int x, int y){
		super(x-40,y-10);
		this.loadImage(Bullet.BULLET_IMAGE);
	}


	//method that will move/change the x position of the bullet 
	public void move(){
		/*
	     * Change the x position of the bullet depending on the bullet speed.
	     * If the x position has reached the right boundary of the screen,
	     * set the bullet's visibility to false.
	     */
	    this.x += BULLET_SPEED;
	   
	    // Check if the bullet has reached the right boundary of the screen
	    if (this.x >= Window.WINDOW_WIDTH) {
	        this.setVisible(false);
	    }
	}
}
