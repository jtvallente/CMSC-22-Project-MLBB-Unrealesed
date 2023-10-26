package application;

import javafx.stage.Stage;

public class Instructions extends Window{
	
	//Text attributes for Instructions Page/Window
	private String instructionHeader = "Instructions and Mechanics";
	private String instructionParagraph = "- A game lasts 60 seconds. Clicking \"New Game\" starts the countdown.\r\n"
			+ "- Player Kimmy starts on the left and moves with arrow keys.\r\n"
			+ "- Shoot minions with SPACEBAR.\r\n"
			+ "- Each killed minion adds 1 point.\r\n"
			+ "- 7 minions spawn initially, 3 respawn every 3 seconds. Their speed increases every 15 seconds\nif not hit.\r\n"
			+ "- Minion hits reduce player's strength.\r\n"
			+ "- At 30 seconds, Elemental Lord respawns. Player's bullet reduces its life.\r\n"
			+ "- Power-up appears every 10 seconds to enhance Kimmy\n       1. Berserker's Fury: provides immortality to Kimmy for 5 sec\n       2. Blade of Heptaseas: : 				doubles the current strength of Kimmy\n       3. Bloodlust Axe: slows down all the minions' movement to the minimum speed for 5 secs\r\n\n"
			+ "- Game ends if time runs out, Elemental Lord's health reaches 0, or Kimmy's strength reaches 0.\r\n"
			+ "- Win by killing the Elemental Lord while Kimmy's strength is not zero.\n\n"
			+ "TIPS:\n"
			+ "    - Dodge minions.\r\n"
			+ "    - Kimmy dies if hit by the Elemental Lord.\r\n"
			+ "    - Long-press spacebar to shoot bullets consecutively.";
	
	public Instructions() {
		super();
		// Draw the background image
        gc.drawImage(BG_START, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
   
      //Display window properties; the setRectProperties() method sets the design and positioning of the text and the background shape
		setRectProperties(instructionHeader, instructionParagraph);
        setBackButton(330, 395);
        
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