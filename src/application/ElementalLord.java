package application;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

//Elemental Lord is a subclass of Enemy.
public class ElementalLord extends Enemy{
	
	private int health;
	private long lastCollisionTime = 0;  // variable to track the time of the last collision
	private boolean hasSpawned;

	//properties of the Elemental Lord
	public final static int LORD_WIDTH=200;
	public final static int APPEAR_TIME = 30;			//The lord instance will appear after 30 seconds
	public final static Image LORD_IMAGE = new Image("lord-1-removebg-preview.png",LORD_WIDTH, LORD_WIDTH,false,false);
	public final static int LORD_DAMAGE = 50;
	public final static int MAX_NUM_LORD = 50;
	public final static int LORD_INITIAL_HEALTH = 3000;
	
	
	public ElementalLord(int xPos, int yPos) {
		super(xPos, yPos);
		this.alive = true;
		this.hasSpawned = false;
		this.loadImage(ElementalLord.LORD_IMAGE);
		this.damage = LORD_DAMAGE;			//this will reduce player's strength when the lord hits the player. 
		this.setHealth(LORD_INITIAL_HEALTH );				//set initial health to 3000
		
	}
	
	
	public void move(Kimmy myKimmy, ArrayList<Bullet> bulletList){
		
		//method that changes the x position of the Elemental Lord
		super.move(myKimmy, bulletList);
		
		//checking the collision of Kimmy and lord; Kimmy's strength will only be reduced if she is not in the immortal state. 
		if (myKimmy.isImmortal() == false) {
			if (this.collidesWith(myKimmy)) {
			    long currentTime = System.currentTimeMillis();
			    if (currentTime - lastCollisionTime >= 2000) {  // check if 2 seconds have passed since the last collision
			        lastCollisionTime = currentTime;  // update the last collision time

			        // Reduce Kimmy's strength
			        myKimmy.reduceStrength(LORD_DAMAGE);

			        //just to check if Kimmy's strength is reduced after collision
			        System.out.println(myKimmy.getStrength());
			    }
			}
	        
		}
	
        // Check for collision with bullet
        for(int i = 0; i < bulletList.size(); i++){
			Bullet b = bulletList.get(i);
			if (this.collidesWith(b)) {
            
				//reduce the health of elemental lord once it is hit by a bullet
				this.health = this.health - myKimmy.getStrength();
				System.out.println(this.health);
				
				//remove the bullet in the array
				bulletList.remove(b);
				
				//checking if Lord's health is equal to zero
				if (this.health <= 0) {
					this.alive = false;
				
				}
				// checking if the Elemental Lord is alive
			    if (!this.isAlive()) {
			        this.setVisible(false);  // Hide the Elemental Lord's image
			        return;  // Exit the method to stop further processing
			    }
			}
           
        }
	}
	
	@Override
	public int generateDamage() {
		return LORD_DAMAGE;
	}
	
	//getter
	public int getHealth() {
		return health;
	}
	
	//setter
	public void setHealth(int health) {
		this.health = health;
	}
	
	//random Y position of the elemental lord during spawn 
	public int enemyYPos() {
		Random r = new Random();
		return r.nextInt(GameStage.WINDOW_HEIGHT - ElementalLord.LORD_WIDTH);
	}


	public boolean isHasSpawned() {
		return hasSpawned;
	}


	public void setHasSpawned(boolean hasSpawned) {
		this.hasSpawned = hasSpawned;
	}
	


}
