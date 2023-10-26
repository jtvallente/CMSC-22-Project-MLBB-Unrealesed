package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method. 
 */
 
public class GameTimer extends AnimationTimer{
	
	private GraphicsContext gc;
	private GameStage gameStage;
	private Scene theScene;
	
	//Sprites
	private Kimmy myKimmy;
	private ElementalLord l = new ElementalLord(0,0);			//instantiated an Elemental Lord so that the game over method will not cause an error when Kimmy's strength turns to zero while lord hasn't spawned yet
	private Bullet bullet;
	private Minions m;
	
	//Arrays for minions, bullets, and powerups
	private ArrayList<Minions> minion;
	private ArrayList<Bullet> bulletList;
	private ArrayList<PowerUp> items;
	private ArrayList<ElementalLord> lord;
	
	//minion count
	public static final int MAX_NUM_MINIONS = 7;
	public static final int ADDITIONAL_MINIONS = 3;
	
	//Respawn attributes for minions
	private long respawnInterval = 5000000000L;
	private long speedUpInterval = 15000000000L;
	private long lastSpeedUpTime;
	private long lastSpawnTime;
	private int spawnedMinions;
    
	//variables for keeping track of the start time, elapsed time, and current time
	private long startTime;
	private long elapsedTime;
	private long currentTime;
     
	private long lastPowerUpSpawnTime;
	
	//counts the spawned lord; this variable is just used to limit the number of lord to be spawned to just 1. 
	private int lord_count;
	
	//INSTATIATING BACKGROUND MUSIC
	public String musicFile = "src/music.mp3";  // relative path to the music file
	public File file = new File(musicFile);
	public Media sound = new Media(file.toURI().toString());
	public MediaPlayer mediaPlayer = new MediaPlayer(sound);
	
	
	GameTimer(GraphicsContext gc, Scene theScene, GameStage gameStage){
		
		this.gameStage = gameStage;
		this.gc = gc;
		this.theScene = theScene;
		
		//Setting background music for the game
		this.mediaPlayer.play();
		mediaPlayer.setVolume(0.5);  // Set the volume to 50%
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Loop the music indefinitely
		
		//Kimmy instance
		this.setMyKimmy(new Kimmy("MLBB Kimmy",100,100));
		
		//instantiate the ArrayList of Minions, Bullets, and PowerUp
		this.minion = new ArrayList<Minions>();
		this.bulletList = new ArrayList<Bullet>();
		this.items = new ArrayList<PowerUp>();
		this.lord = new ArrayList<ElementalLord>();
		
		
		this.spawnedMinions = 0;			//initially, there's no minions yet
		this.lastSpawnTime = 0;				//last spawn time for the minion
		this.lastSpeedUpTime = 0;			//last speed up time for the minions; speeds up every 15 secs for 3 secs
		this.lastPowerUpSpawnTime = 0;		//last spawn time for the powerup
		this.lord_count = 0;				//initially, lord count is zero
		
		//call the spawnFishes method
		this.spawnMinions(MAX_NUM_MINIONS-3);		//there's minus 3 because the next 3 minions will also spawn together with initial 7 minions
		
		//keeping track of the start time
		this.startTime = System.currentTimeMillis();	
		
		//call method to handle mouse click event
		this.handleKeyPressEvent();
	
		
	}

	@Override
	public void handle(long currentNanoTime) {
		
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		
		//Add background image
		gc.drawImage(GameStage.MAP, 0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
        
        //movement mechanics of sprites
		this.getMyKimmy().move();
        this.moveBullets();
        this.moveMinions();
        this.moveLord();

        // render kimmy, minions, and bullets, powerups
        this.getMyKimmy().render(this.gc);
        //this.lord.render(this.gc);
        this.renderBullets();
        this.renderMinions();
        this.renderLord();
        
        
        // Add the rectangle as a placeholder for timer, strength and score
	    gc.setFill(Color.BLACK);
	    gc.setGlobalAlpha(0.6); // Set opacity to 50%
	    gc.fillRect(0, 0, GameStage.WINDOW_WIDTH, 50);

	    // Reset the opacity to 1.0 so that the text inside the rectangle will be opaque
	    gc.setGlobalAlpha(1.0);
	    
	    //display the timer, score and kimmy's strength on screen
        renderTimer();
        renderScore();
        renderStrength();
        renderpowerUpStatus();
        
        //check strength of Kimmy
        if (myKimmy.getStrength() <= 0) {
            gameOver();
        }
        
        //if there is no time left
        if(elapsedTime == 0) {
        	System.out.println(elapsedTime);
        	gameOver();
        }
                
        //this is to check if a powerup will be spawned; powerup spawns every 10 seconds
        long elapsedTimeSincePowerUpSpawn = currentNanoTime - lastPowerUpSpawnTime;
        if (elapsedTimeSincePowerUpSpawn >= 10000000000L && !(elapsedTime>=51)) {
            spawnPowerUp();
            lastPowerUpSpawnTime = currentNanoTime;
        }
 
        renderPowerUps();
        
        
      //move the lord if elapsed time <= 30
        if (this.elapsedTime <= ElementalLord.APPEAR_TIME && this.lord_count == 0) { 
        	//spawn the elemental lord
        	this.lord_count+=1;
    		this.spawnLord();
			moveLord();
        }
        
        //this is to check if it is time for minions to speed up to max speed; speeds up every 15 seconds for 3 seconds
        long elapsedTimeSinceSpeedUp = currentNanoTime - lastSpeedUpTime;
        if (elapsedTimeSinceSpeedUp >= speedUpInterval && !(elapsedTime>=46)) {
        	speedUpMinions();
        	lastSpeedUpTime = currentNanoTime;
        }
        
        //checking the time for spawning another minions
        elapsedTime = currentNanoTime - lastSpawnTime;
        if (elapsedTime >= respawnInterval) {
            spawnMinions(ADDITIONAL_MINIONS);
            lastSpawnTime = currentNanoTime;
        }
        
       
	}   
	
	//method for display the status of collected powerups
	private void renderpowerUpStatus() {
		//POWER UP 1
		gc.drawImage(PowerUp.ITEM_1,10, 10, PowerUp.ITEM_WIDTH-20, PowerUp.ITEM_WIDTH-20);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        gc.fillText(String.valueOf(PowerUp.getCollectCountP1()), 45, 33);
        
        //POWER UP 2
		gc.drawImage(PowerUp.ITEM_2,70, 10, PowerUp.ITEM_WIDTH-20, PowerUp.ITEM_WIDTH-20);
		gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        gc.fillText(String.valueOf(PowerUp.getCollectCountP2()), 103, 33);
            	
        //POWER UP 3
		gc.drawImage(PowerUp.ITEM_3,130, 10, PowerUp.ITEM_WIDTH-20, PowerUp.ITEM_WIDTH-20);
		gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        gc.fillText(String.valueOf(PowerUp.getCollectCountP3()), 165, 33);
      
 
	}
	//render Timer
	 private void renderTimer() {
		  	currentTime = System.currentTimeMillis();
	        this.elapsedTime = (currentTime-startTime) / 1000; // Convert to seconds for easy manipulation and display
	        this.elapsedTime = 60-elapsedTime;
	        String timerText = "TIMER: " + elapsedTime + "s";
	        gc.setFill(Color.WHITE);
	        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	        gc.fillText(timerText, 300, 30);
	        
	    }
	
	 //render the score
	 private void renderScore() {
		String scoreText = "SCORE: " + getMyKimmy().getScore();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        gc.fillText(scoreText, 450, 30);
	 }
	 
	 //render kimmy's strength
	 private void renderStrength() {
			String strengthText = "STRENGTH: " + getMyKimmy().getStrength();
	        gc.setFill(Color.WHITE);
	        gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	        gc.fillText(strengthText, 590, 30);
		 }
	 
	 
	//method that will render/draw the minions to the canvas
	private void renderMinions() {
		for (Minions f : this.minion){
			f.render(this.gc);
		}
	}
	
	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		/*
		 *Loop through the bullets arraylist of myKimmy 
		 *and render each bullet to the canvas
		 */
		 bulletList = this.getMyKimmy().getBullets();
	        for (Bullet bullet : bulletList) {
	            bullet.render(this.gc);
	        }
	}
		    
	//method that will render/draw the lord to the canvas
		private void renderLord() {
			for (ElementalLord l : this.lord){
				l.render(this.gc);
			}
		}
		
	//method that will spawn lord at a random y location
	private void spawnLord(){
		Random r = new Random();
		for (int i = 0; i < ElementalLord.MAX_NUM_LORD;) {
			//Lord instance that will start from the rightmost part of the screen and with random y position
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - ElementalLord.LORD_WIDTH);
            l = new ElementalLord(GameStage.WINDOW_WIDTH, y);
            lord.add(l);
            l.setHasSpawned(true);
            break;
            
        }
		
	}
	
	//method that will spawn/instantiate 7 minions at a random x,y location
	private void spawnMinions(int numMinions){
		Random r = new Random();
		for (int i = 0; i < numMinions; i++) {
			int x = GameStage.WINDOW_WIDTH;
			int y = r.nextInt(GameStage.WINDOW_HEIGHT - Minions.MINION_WIDTH-80) + 50;
            m = new Minions(x, y);
            minion.add(m);
            this.spawnedMinions+=1;
        }
		
	}
	
	//method for speeding up minions every after 15 seconds; this will override the effect caused by PowerUp 2 (Slowing minions) when they occur simultaneously. 
	private void speedUpMinions() {
		
		//store the original speeds of minions and make their speed at max for 3 seconds
		List<Integer> initialSpeeds = new ArrayList<>();
        for (Minions m : minion) {
            initialSpeeds.add(m.getSpeed());
            m.setSpeed(Minions.MAX_ENEMY_SPEED);
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
        timer.schedule(task, 3000); // Schedule the task to run after 5 seconds
	}
	
	//Get random powerup to be spawned
	private String getRandomPowerUp() {
	    String[] powerUps = {PowerUp.POWER_UP_1, PowerUp.POWER_UP_2, PowerUp.POWER_UP_3};  
	    Random r = new Random();
	    int randomIndex = r.nextInt(powerUps.length);
	    return powerUps[randomIndex];
	}
	
	//method that will spawn powerup at random x and y location
	private void spawnPowerUp() {
        Random r = new Random();
        int x = r.nextInt(GameStage.WINDOW_WIDTH / 2 - PowerUp.ITEM_WIDTH);  // Random x position on the left half of the screen
        int y = r.nextInt(GameStage.WINDOW_HEIGHT - 50 -PowerUp.ITEM_WIDTH) + 50;
        String item_name = getRandomPowerUp();  // Implement a method to get a random power-up name
        PowerUp powerUp = new PowerUp(x, y, item_name);
        items.add(powerUp);
    }
	
	//method that will render/draw the lord to the canvas
    private void renderPowerUps() {
        for (PowerUp powerUp : items) {
            powerUp.appear(this.myKimmy, powerUp, items, minion);
            if (powerUp.isVisible()) {
                powerUp.render(gc);
            }
        }
    }
    
	//method that will move the bullets shot by a ship
	private void moveBullets(){
		//create a local arraylist of Bullets for the bullets 'shot' by kimmy
		bulletList = this.getMyKimmy().getBullets();
		
		//Loop through the bullet list and check whether a bullet is still visible.
		for(int i = 0; i < bulletList.size(); i++){
			bullet = bulletList.get(i);
			
			 //If a bullet is visible, move the bullet, else, remove the bullet from the bullet array list. 
			 
			if (bullet.isVisible()) {
	            bullet.move();
	          
	        } else {
	            bulletList.remove(i);
	            i--; // Decrement the index since the list size has changed
	        }
		}
	}
	
	//method that will move the minions
	private void moveMinions(){
		//Loop through the minions arraylist
		
		for(int i = 0; i < this.minion.size(); i++){
			Minions m = this.minion.get(i);
			/*
			 * If a minion is alive, move the minion. Else, remove the minion from the minions arraylist. 
			 */
			m.move(getMyKimmy(), bulletList);
			if (!m.isAlive()) {
				this.minion.remove(m);
				i--;
			}
	
		}
	
	}
	
	//method that will move the lord
		private void moveLord(){
			//Loop through the lord arraylist
			
			for(int i = 0; i < this.lord.size(); i++){
				ElementalLord eL = this.lord.get(i);
				/*
				 * If lord is alive, move the lord. Else, remove the lord from the lord arraylist. 
				 */
				eL.move(getMyKimmy(), bulletList);
				if (!l.isAlive()) {
					this.lord.remove(eL);
					i--;
				}
		
			}
		
		}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyKimmy(code);
			}
		});
		
		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyKimmy(code);
		            }
		        });
    }
	
	//method that will move the ship depending on the key pressed
	private void moveMyKimmy(KeyCode ke) {
		if(ke==KeyCode.UP) this.getMyKimmy().setDY(-3);                 

		if(ke==KeyCode.LEFT) this.getMyKimmy().setDX(-3);

		if(ke==KeyCode.DOWN) this.getMyKimmy().setDY(3);
		
		if(ke==KeyCode.RIGHT) this.getMyKimmy().setDX(3);
		
		if(ke==KeyCode.SPACE) this.getMyKimmy().shoot();			
		
		System.out.println(ke+" key pressed.");
   	}
	
	//method that will stop Kimmy's movement; set Kimmy's DX and DY to 0
	private void stopMyKimmy(KeyCode ke){
		this.getMyKimmy().setDX(0);
		this.getMyKimmy().setDY(0);
	}
	
	public long getElapsedTime() {
		return this.elapsedTime;
	}

	public Kimmy getMyKimmy() {
		return myKimmy;
	}

	public void setMyKimmy(Kimmy myKimmy) {
		this.myKimmy = myKimmy;
	}
	
	//this method will be called once the condition (kimmy's strength = 0 or lord is not alive) in handle method is satisfied
    private void gameOver() {
        stop(); // Stop the animation timer
        
        // Show the game over window to show score
		GameOver over = new GameOver(myKimmy, l, elapsedTime);
        over.setStage(gameStage.getStage());
		
        
        
    }
	
	
}
