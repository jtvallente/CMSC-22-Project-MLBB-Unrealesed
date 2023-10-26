package application;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

//Kimmy is a subclass of s
public class Kimmy extends Sprite{
	
	private String name;
	private int strength;
	private boolean alive;
	private int score;
	private int origStrength;					//will be used to calculate the final score of the player when the game is over
	private boolean immortal;
	private ArrayList<Bullet> bullets;
	private long immortalityStartTime;
	
	//Kimmy final attributes
	private final static int KIMMY_WIDTH = 80;
	public final static Image KIMMY_IMAGE = new Image("hero-1-removebg-preview.png",Kimmy.KIMMY_WIDTH,Kimmy.KIMMY_WIDTH,false,false);
	public final static Image KIMMY_IMO = new Image("kimmy-immortal.png",Kimmy.KIMMY_WIDTH,Kimmy.KIMMY_WIDTH,false,false);
	
	
	//constructor for Kimmy
	public Kimmy(String name, int x, int y){
		super(x,y);
		this.name = name;
		this.setImmortal(false);				//Kimmy is initially not immortal
		
		//randomize the initial strength of kimmy from 100-150
		Random r = new Random();
		this.strength = r.nextInt(51)+100;
		
		//original strength must not be changed
		this.origStrength = strength;
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.loadImage(Kimmy.KIMMY_IMAGE);
		
	}
	
	//SETTERS & GETTERS
	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	} 
	public String getName(){
		return this.name;
	}

	public void die(){
    	this.alive = false;
    }
	
	public void setStrength(int dStrength) {
		this.strength = dStrength;
	}
	public int getStrength() {
        return strength;
    }
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score+=score;
	}
	public int getOrigStrength() {
		return origStrength;
	}

	public boolean isImmortal() {
		return immortal;
	}

	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
	}
	public void setImmortalityStartTime(long startTime) {
        immortalityStartTime = startTime;
    }

    public long getImmortalityStartTime() {
        return immortalityStartTime;
    }
	
	//reduce/update kimmy's strength after collision with minion or lord
	public void reduceStrength(int damage) {
        this.strength = this.strength - damage;
        if (this.strength < 0) {
            this.strength = 0;
        }
    }
	
	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}
	
	//method called if spacebar is pressed
	public void shoot(){
		  // compute for the x and y initial position of the bullet
	    int x = (int) (this.x + this.width + 20);
	    int y = (int) (this.y + this.height / 2);

	    /*
	     *Instantiate a new bullet and add it to the bullets ArrayList of kimmy
	     */
	    Bullet bullet = new Bullet(x, y);
	    bullets.add(bullet);
    }
	
	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		  /*
	     * Only change the x and y position of Kimmy if the current x,y position
	     * is within the Window width and height so that Kimmy won't exit the screen
	     */
	    int newPosX = this.x + this.dx;
	    int newPosY = this.y + this.dy;

	    // Check if the new position is within the game stage width and height
	    if (newPosX >= 0 && newPosX <= Window.WINDOW_WIDTH-80 && newPosY >= 20 && newPosY <= Window.WINDOW_HEIGHT-80) {
	        this.x = newPosX;
	        this.y = newPosY;
	    }
	}



}
