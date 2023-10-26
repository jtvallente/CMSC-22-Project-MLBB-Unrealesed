package application;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

import javafx.scene.image.Image;

//PowerUp is a subclass of Sprite
public class PowerUp extends Sprite{
	//private boolean availables
	private String item_name;
	private long spawnTime;
	private boolean isCollected;
	public final static int MAX_POWER_UPS = 3;
	public final static int DOUBLE_STRENGTH = 2;
	
	//These variables are used to keeping track of the count of the collected powerup
	private static int collectCountP1 = 0;
	private static int collectCountP2 = 0;
	private static int collectCountP3 = 0;
	
	public final static int ITEM_WIDTH = 50;
	
	//Powerup names
	public final static String POWER_UP_1 = "Berserker's Fury";
	public final static String POWER_UP_2 = "Immortality";
	public final static String POWER_UP_3 = "Ice Queen Wand";
	
	//ITEM IMAGES
	public final static Image ITEM_1 = new Image("build1-removebg-preview.png",ITEM_WIDTH ,ITEM_WIDTH,false,false);
	public final static Image ITEM_2 = new Image("immortality-png.png",ITEM_WIDTH,ITEM_WIDTH,false,false);
	public final static Image ITEM_3 = new Image("ice-queen-wand-png.png",ITEM_WIDTH,ITEM_WIDTH,false,false);
	
	
	public PowerUp(int xPos, int yPos, String item_name) {
        super(xPos, yPos);
        this.item_name = item_name;
        this.setVisible(false);										//each powerup is initially not visible
        this.spawnTime = System.currentTimeMillis();				//spawn time will be recorded the moment an instance of a powerup appears on screen. 
        
        // Load image based on item_name
        if (this.item_name.equalsIgnoreCase(POWER_UP_1)) {
            this.loadImage(ITEM_1);
        } else if (this.item_name.equalsIgnoreCase(POWER_UP_2)) {
            this.loadImage(ITEM_2);
        } else if (this.item_name.equalsIgnoreCase(POWER_UP_3)) {
            this.loadImage(ITEM_3);
        }
    }
	
	//methods for making the powerup appear on screen for 5 seconds; parameters are used because their values/attributes will be changed or accessed in this method
	public void appear(Kimmy myKimmy, PowerUp item, ArrayList<PowerUp> items, ArrayList<Minions> minion) {
	    long currentTime = System.currentTimeMillis();
	    if (currentTime - spawnTime > 5000) {						//if 5 seconds has passed
	        this.setVisible(false);
	    } else {
	        this.setVisible(true);
	        
	        //checking collision with the player
	        if (item.isVisible() && item.collidesWith(myKimmy)) {
	        	if (!item.isCollected()) {
	        		if (item.getItemName().equalsIgnoreCase(PowerUp.POWER_UP_1)) {
		                collectCountP1 = getCollectCountP1() + 1;
		                modifyItem(item, items);
		                myKimmy.setStrength(myKimmy.getStrength()*DOUBLE_STRENGTH);				//Double the current strength of kimmy
		                
		            } else if (item.getItemName().equalsIgnoreCase(PowerUp.POWER_UP_2)) {
		                collectCountP2 = getCollectCountP2() + 1;
		                modifyItem(item, items);
		                
		                //Setting immortality
		                myKimmy.setImmortal(true);
		                myKimmy.setImmortalityStartTime(currentTime); // Set the immortality start time
		                myKimmy.loadImage(Kimmy.KIMMY_IMO);
		                TimerTask task = new TimerTask() {
		                    public void run() {
		                        myKimmy.setImmortal(false); // Disable immortality after 5 seconds
		                        myKimmy.loadImage(Kimmy.KIMMY_IMAGE); // Set the normal image
		                    }
		                };
		                Timer timer = new Timer();
		                timer.schedule(task, 5000); // Schedule the task to run after 5 seconds
		            }
		            else if (item.getItemName().equalsIgnoreCase(PowerUp.POWER_UP_3)) {
		                collectCountP3 = getCollectCountP3() + 1;
		                modifyItem(item, items);
		                
		                // Store the initial speeds of the minions so that they will be accessed after setting their speed to minimum
		                List<Integer> initialSpeeds = new ArrayList<>();
		                for (Minions m : minion) {
		                    initialSpeeds.add(m.getSpeed());
		                    m.setSpeed(Minions.MIN_SPEED);				//set the speed of the minion to minimum
		                }

		                TimerTask task = new TimerTask() {
		                    public void run() {
		                        // Restore the initial speeds of the minions
		                        for (int i = 0; i < minion.size(); i++) {
		                            minion.get(i).setSpeed(initialSpeeds.get(i));
		                        }
		                    }
		                };

		                Timer timer = new Timer();
		                timer.schedule(task, 5000); // Schedule the task to run after 5 seconds
		               
		            }
	        	}
	            
	        } else {
	            item.setCollected(false);
	        }

	    }
	}
	
	//modify the powerup if collision with kimmy has occured
	private void modifyItem(PowerUp item, ArrayList<PowerUp> items) {
        item.setCollected(true);
        item.setVisible(false);
        items.remove(item);
	}

	//SETTERS AND GETTERS
	public String getItemName() {
		return this.item_name;
	}
	public void setCollected(boolean collected) {
	        this.isCollected = collected;
	    }

    public boolean isCollected() {
        return isCollected;
    }

	public static int getCollectCountP1() {
		return collectCountP1;
	}

	public static int getCollectCountP2() {
		return collectCountP2;
	}

	public static int getCollectCountP3() {
		return collectCountP3;
	}


    

	
	

}
