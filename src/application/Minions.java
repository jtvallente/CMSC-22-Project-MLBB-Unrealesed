package application;


import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

//Minions is a subclass of Enemy.
public class Minions extends Enemy {
	
	//MINION ATTRIBUTES
	public static final int TOTAL_MINIONS = 40;			//calculated/expected number of minions to be spawned within 60 seconds
	public final static Image MINION_IMAGE = new Image("minion-1-removebg-preview.png",Minions.MINION_WIDTH, Minions.MINION_WIDTH,false,false);
	public final static int MINION_WIDTH=80;
	public static final int MIN_DAMAGE = 30;
    public static final int MAX_DAMAGE = 40;
    
	
	
	Minions(int x, int y){
		super(x,y);
		this.loadImage(Minions.MINION_IMAGE);
		//initializing the damage of minions (each minion has a random damage ranging 30-40)
		this.damage = generateDamage();
		
	}
	
	
	// Check for collision with Kimmy (the minion will disappear if collision with kimmy occurs; Kimmy's strength is reduced by minions' damage
	public void move(Kimmy myKimmy, ArrayList<Bullet> bulletList){

		//method that changes the x position of the minion
        super.move(myKimmy, bulletList);
        
        //checking the collision of Kimmy and minion; Kimmy's strength will only be reduced if she is not in the immortal state. 
        
    	if (this.collidesWith(myKimmy)) {
    		if (myKimmy.isImmortal() == false) {
	            // Reduce Kimmy's strength
	            myKimmy.reduceStrength(damage);
	            
	            // Mark the minion as not alive
				this.alive = false;
	      
	            //just to check if kimmy's strength is reduced after collision
	            System.out.println(myKimmy.getStrength());
    		} else {
    			this.alive = false;
    		}
      }
			
        
        // Check for collision with bullet
        for(int i = 0; i < bulletList.size(); i++){
			Bullet b = bulletList.get(i);
			if (this.collidesWith(b)) {
            
				// Mark the minion as not alive
				this.alive = false;
				
				//increment the score of Kimmy; the parameter 1 here will be added to the initial value of her score. 
				myKimmy.setScore(1);
				b.setVisible(false);
			}
           
        }
	}
	
	
	//getter
	
	
	public int getDamage() {
		return this.damage;
	}
	public int getSpeed() {
		return this.speed;
	}
	//this setter will only be called for setting the minimum speed of the minions once a powerup for this is collected
	public void setSpeed(int min_speed) {
		this.speed = min_speed;
	}
	
	//method for generating minion random damage
	@Override
	public int generateDamage() {
		Random random = new Random();
        return random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1) + MIN_DAMAGE; //ranges from 30 to 40, inclusive
		
	}
	
}

