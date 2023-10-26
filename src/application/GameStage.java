package application;

import javafx.stage.Stage;

//GameStage is a subclass of Window
public class GameStage extends Window{
	private GameTimer gametimer;

	public GameStage() {
		super();
		
		gc.drawImage(MAP, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
     
		//instantiate an animation timer
		this.gametimer = new GameTimer(this.gc,this.scene, this);


		
	}

	//method to add the stage elements
	public void setStage(Stage stage) {
		this.stage = stage;
		//set stage elements here	 
		this.root.getChildren().add(canvas);
		this.stage.setTitle("MLBB: Unreleased");
		this.stage.setScene(this.scene);
		
		//invoke the start method of the animation timer
		this.gametimer.start();
		
		this.stage.show();
	}
	
	
	//getter
    public Stage getStage() {
        return stage;
    }
	
	
	
}

