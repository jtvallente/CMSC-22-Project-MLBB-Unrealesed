package application;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class GameOver extends Window{
	
	//This constructor accepts types Kimmy, ElementalLord, and long since they will be changed/accessed inside the class
	public GameOver(Kimmy myKimmy, ElementalLord lord, long elapsedTime) {
		super();
		//display the back button on the window
        setBackButton(325, 380);
        
        //show conditions for victory or lose
        //call condition to check if player won or not
        checkStatus(myKimmy, lord, elapsedTime);
        
	}
	
	//This is just an additional feature; the final score of the player is calculated based on the player's remaining time, strength, score, and if the lord is killed. 
	public double calculateScore(Kimmy myKimmy, int lordKilled, long elapsedTime) {
		
		//Calculating the score
		double strengthLeft;
		if (myKimmy.getStrength()>myKimmy.getOrigStrength()) {
			strengthLeft = 5;
			
		} else {
			strengthLeft = (myKimmy.getStrength() * 5.0) / myKimmy.getOrigStrength();
		}
		
	    
	    double score = (myKimmy.getScore() * 5.0) / Minions.TOTAL_MINIONS;
	    double lord = lordKilled * 5.0;
		
		double finalScore = strengthLeft + score + lord;
		
		//since the result of the final score has many decimal, we will limit it to just 1. 
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		finalScore = Double.parseDouble(decimalFormat.format(finalScore));
		return finalScore;
		
	}
	
	//This method checks which Victory page or Defeat page will be displayed
	public void checkStatus(Kimmy myKimmy, ElementalLord lord, long elapsedTime) {
		
		//This will be true only if the lord died, player's strength is not equal to zero, and if the time is equal to zero
		if (lord.getHealth()<=0 && myKimmy.getStrength()>=1 && elapsedTime == 0) {
			//display background image
			gc.drawImage(VICTORY, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			
			//Displaying Game Stats and Scores
			gc.setFill(Color.BLACK);
			gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText("GAME STATS", WINDOW_WIDTH/2, 80);
			
		   	gc.setFill(Color.BLACK);
			gc.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
			gc.setTextAlign(TextAlignment.CENTER);
			
			//the parameter int lordKilled will be equal to 1 since the lord was killed
			gc.fillText("Minions killed: " + myKimmy.getScore() + "\nTime Left: " + elapsedTime + "\nStrength Left: " + myKimmy.getStrength() + "\nTotal Points: " + calculateScore(myKimmy, 1, elapsedTime), WINDOW_WIDTH/2, 110);
		
		//this will be true only if the lord did not appear yet. OR if Kimmy's strength is equal to zero while there is still time
		} else if (lord.isHasSpawned() == false || (myKimmy.getStrength()<=0 && elapsedTime>=0)) {
			//display background image
			gc.drawImage(DEFEAT, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		    
			//Displaying Game Stats and Scores
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText("GAME STATS", WINDOW_WIDTH/2, 80);
			
		   	gc.setFill(Color.WHITE);
			gc.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
			gc.setTextAlign(TextAlignment.CENTER);
			if (lord.getHealth()<=0) {
				gc.fillText("Minions killed: " + myKimmy.getScore() + "\nTime Left: " + elapsedTime + "\nStrength Left: " + myKimmy.getStrength() + "\nTotal Points: " + calculateScore(myKimmy, 1, elapsedTime), WINDOW_WIDTH/2, 110);
			} else {
				gc.fillText("Minions killed: " + myKimmy.getScore() + "\nTime Left: " + elapsedTime + "\nStrength Left: " + myKimmy.getStrength() + "\nTotal Points: " + calculateScore(myKimmy, 0, elapsedTime), WINDOW_WIDTH/2, 110);
			}
			
		}
	}
		
	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
    	this.root.getChildren().add(canvas);
    	this.root.getChildren().add(backButton);
		this.stage.setTitle("MLBB Unreleased");
		this.stage.setScene(this.scene);
        // Show the window
        this.stage.show();
		
	}
}