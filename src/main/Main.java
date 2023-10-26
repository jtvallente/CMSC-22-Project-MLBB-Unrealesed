/**
 * CMSC 22 MINI PROJECT: "MLBB Unreleased" (This game is inspired by Mobile Legends: Bang Bang, developed by Moonton.)
 * @author: James Lourence T. Vallente - 202167857
 * @author: John Benedict Aguirre
 * 
 * Instructors: Johaira Ariola and Mylah Rystie Anacleto
 * BSCS, Institute of Computer Science, UPLB
 * 
 * @date Project Duration: May 11, 2023 --> June 14, 2023
 * 
 * About the game: see About section of the game
 * References: see documentation below
 * 
 */

package main;

import javafx.application.Application;
import application.*;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	//This will go to the first window (StartPage.java)
	public void start(Stage stage){
		StartPage newGame = new StartPage();
		newGame.setStage(stage);
	}

}



/**
 * DISCLAIMER: Disclaimer: This game is an independent project developed by James and John for educational purposes as part of CMSC 22's requirement. It is not intended for commercial use or distribution. This game is inspired by Mobile Legends: Bang Bang. However, it is an independent creation and is not affiliated with or endorsed by the creators of MLBB. The sprites, images, and other assets used in this game are either original creations or have been sourced from various publicly available platforms. All third-party assets used in this game are the property of their respective creators and are used under the appropriate licenses or permissions. If you believe that any content used in this game infringes upon your rights, please contact us immediately so that we can take appropriate action.
 * 
 * REFERENCES (Images/Codes used)
 * Sprites
 * 	-Kimmy: pinimg.com (https://i.pinimg.com/originals/15/e6/83/15e683a0cf6d60abf260a2f264c89974.png): 
 * 	-Minions: Mobile Legends: Bang Bang Wiki 
 * 					(https://static.wikia.nocookie.net/mobile-legends/images/7/77/Siege_Minion_with_Extra_Gold.jpg/revision/latest?cb=20200828100602)
 * 	-Elemental Lord: Mobile Legends PH/twitter.com (https://twitter.com/MobileLegendsP3/status/1154502584767377408/photo/1)
 * 	-Berserker's Fury (Power up): Novazenn/Berserker's Fury (https://www.novazenn.com/2023/01/item-mobile-legend-png.html)
 * 	-Immortality (Power up): Novazenn/Immortality (https://www.novazenn.com/2023/01/item-mobile-legend-png.html)
 * 	-Ice Queen Wand (Power up): Novazen/Ice Queen Wand (https://www.novazenn.com/2023/01/item-mobile-legend-png.html)
 * 	-Bullet: pngtree.com (https://png.pngtree.com/png-clipart/20230102/original/pngtree-realistic-muzzle-flash-gaming-gun-fire-transparent-image-clipart-png-image_8855459.png)
 * 	
 * Background Images:
 * 	-New Game Page Background Image: pxfuel wallpaper (https://e0.pxfuel.com/wallpapers/591/690/desktop-wallpaper-draven-league-of-legends-and-mobile-background.jpg)
 * 	-Start Page Background Image: jms20000/wallpaper.com (https://wallpapers.com/images/high/miya-and-other-mobile-legend-heroes-fec3p6ccgadqmtxk.webp)
 *  -Victory Page Background Image: Zembii/Wallpaper Abyss (https://images7.alphacoders.com/954/954347.jpg)
 *  -Defeat Page Background Image wallpaper safari (https://mcdn.wallpapersafari.com/medium/58/9/F0aghR.png)
 *  
 * 		
 * Buttons: ImageFu.com (https://reviewgrower.com/button-and-badge-generator/)
 * Background Music: "Mobile legend Background Music Theme song (season 1 - Season 20)" Youtube/Sinargus (https://www.youtube.com/watch?v=wMtmVwnu29M)
 * MLBB Logo: vai42rus/wallpaper.com (https://wallpapers.com/wallpapers/original-mobile-legends-bang-bang-logo-25b8jjlmo78wxkix.html)
 * 
 * Codes used:
 * 	-Project Template: CMSC 22 09_LAB_MINI_PROJECT, ICS, CAS, UPLB
 *  - Project Template: CMSCC 22 09_LAB_MINI_PROJECT, ICS, CAS, UPLB
    - JavaFX Button create image-based buttons (http://www.java2s.com/ref/java/javafx-button-create-imagebased-buttons.html)
    - Getting a mp3 file to play using javafx (https://stackoverflow.com/questions/24347658/getting-a-mp3-file-to-play-using-javafx)
    - JavaFX Button (https://jenkov.com/tutorials/javafx/button.html)
    - Java Code Examples for javafx.scene.canvas.GraphicsContext#setGlobalAlpha() (https://www.programcreek.com/java-api-examples/?class=javafx.scene.canvas.GraphicsContext&method=setGlobalAlpha)
    - How to use fillText method in javafx.scene.canvas.GraphicsContext (https://www.tabnine.com/code/java/methods/javafx.scene.canvas.GraphicsContext/fillText)
    - Java TimerTask (https://www.youtube.com/watch?v=QEF62Fm81h4)
    - 14.13 How to use CurrentTimeMillis of System Class to calculate execution time in Java Tutorial (https://www.youtube.com/watch?v=7KThZb9G7II)
    - JavaFX Event Handling (https://www.tutorialspoint.com/javafx/javafx_event_handling)

 */
