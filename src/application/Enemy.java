package application;

import java.util.ArrayList;
import java.util.Random;

//SUPERCLASS OF MINIONS AND ELEMENTAL LORD
public abstract class Enemy extends Sprite{
	
	//COMMON ATTRIBUTES OF MINIONS AND ELEMENTAL LORD
	public static final int MAX_ENEMY_SPEED = 5;
	public static final int MIN_SPEED = 1;
	protected boolean moveRight;						//attribute that will determine if a minion will initially move to the right
	protected boolean alive;
	protected int speed;
	protected int damage;
	protected int initialYPos;
	protected int initialXPos;
	
	//SUPER CONSTRUCTOR
	public Enemy(int xPos, int yPos) {
		super(xPos, yPos);
		this.alive = true;
		
		//this will randomize the speed of the minion from 1 to 5
		Random random = new Random();
		this.speed = random.nextInt(MAX_ENEMY_SPEED) + 1;
		
		//true or false based on generated boolean value
		this.moveRight = random.nextBoolean();
		
	}
	
	
	public void move(Kimmy myKimmy, ArrayList<Bullet> bulletList) {
		/**
		 * If moveRight is true and if the minion hasn't reached the right boundary yet,
		 * move the minion to the right by changing the x position of the minion depending on its speed
		 * else if it has reached the boundary, change the moveRight value / move to the left 
		 * Else, if moveRight is false and if the minion hasn't reached the left boundary yet,
		 * move the minion to the left by changing the x position of the minion depending on its speed.
		 * else if it has reached the boundary, change the moveRight value / move to the right
		 */
		if (moveRight) {
			if (x + width + speed <= Window.WINDOW_WIDTH) {			//width variable here is the width of the sprite from Sprite.java class
				x += speed;
			} else {
				moveRight = false;
			}
		} else {
			if (x - speed >= 0) {
				x -= speed;
			} else {
				moveRight = true;
			}
		}
	}
	
	//implemented by the subclasses
	public abstract int generateDamage();
	
	//getter
	public boolean isAlive() {
		return this.alive;
	}

}
