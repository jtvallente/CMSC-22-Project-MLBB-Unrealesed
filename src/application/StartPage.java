package application;

import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


//StartPage is a subclass of Window
public class StartPage extends Window {
	
	//button variables for new game, instruction, and about buttons
	private Button ngButton;
	private Button instructionButton;
	private Button aboutButton;
	
	public StartPage() {
		super();
		
		//playing the background music
		this.mediaPlayer.play();
		mediaPlayer.setVolume(0.5);  // Set the volume to 50%
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Loop the music indefinitely
		
        // Draw the background image and the logo
        gc.drawImage(StartPage.BG_START, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        gc.drawImage(LOGO, 190, 40, 400, 250);
        
        //calling the method to add the three buttons
        addButtons();

        
	}
	
	public void addButtons() {
		
		//NEW GAME BUTTON
		ngButton = new Button();
		ImageView newGame = new ImageView(NEWGAME_BTN);
		setButtonProperties(newGame, ngButton, 320, 300);
		ngButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	
		    	//print statement just to check if the button is clickable. 
		    	System.out.println("Button clicked!");
		    	
		        //Starts the game
		    	GameStage newGameStage = new GameStage();
				newGameStage.setStage(stage);

		    }
		});
	
		    
        //INSTRUCTIONS BUTTON
        instructionButton = new Button();
        ImageView instructionsBtn = new ImageView(INSTRUCTIONS_BTN);
        setButtonProperties(instructionsBtn, instructionButton, 320, 350);
        instructionButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	
		    	//print statement just to check if the button is clickable. 
		    	System.out.println("Button clicked!");
		    	
		    	//Will go to the instruction page/window
		    	Instructions instructionsWindow = new Instructions();
				instructionsWindow.setStage(stage);


		    }
		});
		
        //ABOUT BUTTON
        aboutButton = new Button();
        ImageView aboutBtn = new ImageView(ABOUT_BTN);
        setButtonProperties(aboutBtn, aboutButton, 320, 400);
    	aboutButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	
		    	//print statement just to check if the button is clickable. 
		    	System.out.println("Button clicked!");
		    	
		        //Will go the About page/window
		    	Window aboutWindow = new About();
				aboutWindow.setStage(stage);

		    }
		});
        
	}

	
    public void setStage(Stage stage) {
    	this.stage = stage;
    	this.root.getChildren().add(canvas);
    	this.root.getChildren().add(ngButton);
    	this.root.getChildren().add(instructionButton);
    	this.root.getChildren().add(aboutButton);
		this.stage.setTitle("MLBB Unreleased");
		this.stage.setScene(this.scene);
        // Show the window
        this.stage.show();
    }
}

