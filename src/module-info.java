module vallente_aguirre_miniproject {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.media;

	
	opens main to javafx.graphics, javafx.fxml;
}
