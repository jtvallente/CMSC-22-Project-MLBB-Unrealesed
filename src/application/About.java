package application;



import javafx.stage.Stage;

public class About extends Window{
	
	//Text attributes for About Page/Window
	private String aboutHeader = "About the Game";
	private String aboutText = "Hi!\n\nFirst off, thank you so much for playing the game.\n\nInspired by the popular mobile game, Mobile Legends: Bang Bang, this game, \"MLBB Unreleased\" was \ndeveloped in accordance with the required specifications. This game is a mini-project for CMSC 22\nObject-Oriented Programming course (AY 2022-2023; Semester 2). Modifications have also been\nmade to the given project template to make the game unique. It is a single-player shooting game\nwhere the player takes on the role of Kimmy, a hero from MLBB, and shoots enemies such as minions\nand the elemental lord, also from the original game. Game power-ups are inspired by MLBB's items or\nbuilds. Should you have any questions or suggestions, do not hesitate to get in touch with us:\njtvallente@up.edu.ph and jlaguirre@up.edu.ph\n\nSincerely,\nThe Developers\n\nJames Lourence Vallente\nJohn Benedict Aguirre\n\nVersion: v1.0.1\nLast Update: June 14, 2022\nReferences: https://tinyurl.com/cmsc22projectreferences\n\nInstructors: Johaira Arriola, Mylah Rystie Anacleto";
	
	public About() {
		super();
		// Draw the background image and the logo
        gc.drawImage(BG_START, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        gc.drawImage(LOGO, 190, 40, 400, 250);
       
        //Display window properties; the setRectProperties() method sets the design and positioning of the text and the background shape
		setBackButton(330, 395);
		setRectProperties(aboutHeader, aboutText);	
        
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
