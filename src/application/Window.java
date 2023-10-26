package application;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public abstract class Window extends Stage {
	
	//WINDOW AND BUTTON PROPERTIES
	public static final int WINDOW_HEIGHT = 500;
	public static final int WINDOW_WIDTH = 800;
	public static final int BUTTON_WIDTH = 130;
	public static final int BUTTON_HEIGHT = 40;
	
	public static final double RECT_MARGIN = 0.1;					//the margin of the rectangle/background used in texts for instructions and about (can be padding from the edge of the window)
	
	//Protected attributes/accessed only by the subclasses
	protected double rectX, rectY, rectWidth, rectHeight;
	protected Button backButton;
	protected Scene scene;
	protected Stage stage;
	protected Group root;
	protected Canvas canvas;
	protected GraphicsContext gc;
	
	//IMAGES USED IN A WINDOW
	public final static Image MAP = new Image("mapbg-2.jpg",WINDOW_WIDTH,WINDOW_WIDTH,false,false);
	public final static Image BG_START = new Image("start-bg-2.jpg",WINDOW_WIDTH,WINDOW_WIDTH,false,false);
	public final static Image LOGO = new Image("logo-removebg-preview.png",WINDOW_WIDTH,WINDOW_WIDTH,false,false);
	public final static Image VICTORY = new Image("game-over-bg2.jpg",WINDOW_WIDTH,WINDOW_WIDTH,false,false);
	public final static Image DEFEAT = new Image("game-over-bg3.png",WINDOW_WIDTH,WINDOW_WIDTH,false,false);
	public final static Image NEWGAME_BTN = new Image("newgame-button.png");
	public final static Image INSTRUCTIONS_BTN = new Image("instructions-button.png");
	public final static Image ABOUT_BTN = new Image("about-button.png");
	public final static Image EXIT_BTN = new Image("exit-btn.png");
	public final static Image BACK_BTN = new Image("back-btn.png");
	
	//INSTATIATING BACKGROUND MUSIC
	public String musicFile = "src/music.mp3";  // relative path to the music file
	public File file = new File(musicFile);
	public Media sound = new Media(file.toURI().toString());
	public MediaPlayer mediaPlayer = new MediaPlayer(sound);
	
	public Window() {
		this.root = new Group();
		this.scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);	
		this.canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);	
		this.gc = canvas.getGraphicsContext2D();
		
		//play the background music; this will still play in Window's subclasses
		this.mediaPlayer.play();
		mediaPlayer.setVolume(0.5);  // Set the volume to 50%
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Loop the music indefinitely
		
		
	}
	
	//Setting general button properties; xIncrement and Yincrement here are the value of their positions relative to positions of the prior button. 
	public void setButtonProperties(ImageView buttons, Button btntype, int xIncrement, int yIncrement) {
		buttons.setFitWidth(BUTTON_WIDTH);
        buttons.setFitHeight(BUTTON_HEIGHT);
        btntype.setStyle("-fx-background-color: transparent;");			//remove the border or box
        btntype.setGraphic(buttons);
        btntype.setLayoutX(xIncrement);
        btntype.setLayoutY(yIncrement);
	}
	
	//should be implemented by the subclasses
	public abstract void setStage(Stage stage);
	
	//text placeholder for instructions and about texts; accepts the string for the title and the paragraph
	public void setRectProperties(String textHeader, String textParagraph) {
		
		//properties and position of the rectangle; must have a 10% margin
	    double rectX = WINDOW_WIDTH * RECT_MARGIN;
	    double rectY = WINDOW_HEIGHT * RECT_MARGIN;
	    double rectWidth = WINDOW_WIDTH * (1 - 2 * RECT_MARGIN); //800 * (1 - (2*0.1)) == 800 * 0.8 == 640
	    double rectHeight = WINDOW_HEIGHT * (1 - 2 * RECT_MARGIN);

	    // Add the rectangle
	    gc.setFill(Color.BLACK);
	    gc.setGlobalAlpha(0.6); // Set opacity to 50%
	    gc.fillRect(rectX, rectY, rectWidth, rectHeight);

	    // Reset the opacity to 1.0 so that the text inside the rectangle will be opaque
	    gc.setGlobalAlpha(1.0);

	    // Add the header text
	    gc.setFill(Color.WHITE);
	    gc.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
	    gc.setTextAlign(TextAlignment.CENTER);
	    gc.fillText(textHeader, rectX + rectWidth / 2, rectY + 30);

	    // Add the paragraph text
	    gc.setFill(Color.WHITE);
	    gc.setFont(Font.font("Verdana", 12));
	    gc.setTextAlign(TextAlignment.LEFT);
	    //initial position of the first line of the text; paragraphY should increment after the first line
	    double paragraphX = rectX + 20;
	    double paragraphY = rectY + 60;
	    double paragraphWidth = rectWidth - 40;

	    String paraText = textParagraph;
	    
	    // Split the paraText into separate lines using the delimiter "\n" and store each line to the array of strings
	    String[] lines = paraText.split("\n");
	    for (String line : lines) {
	        gc.fillText(line, paragraphX, paragraphY, paragraphWidth);
	        paragraphY += 12; // Adjust line spacing as needed; the next line will be added 12 units after the initial line
	    }
	}
	
	//this method is called/used by Game Over, Instruction, and About Page
	public void setBackButton(int xPos, int yPos) {
		//BACK GAME BUTTON - GOES TO THE MAIN PAGE
		backButton = new Button();
		ImageView newGame = new ImageView(BACK_BTN);
		
		//call this method to set up the size and position of the button
		setButtonProperties(newGame, backButton, xPos, yPos);
		
		//the button will only be triggered if there is a mouseclick
		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	
		    	//print statement just to check if the button is clickable. 
		    	System.out.println("Button clicked!");
		    	
		        // Create a new stage for the main game window (StartPage.java)
		    	StartPage back = new StartPage();
				back.setStage(stage);

		    }
		});
	}
	
}
