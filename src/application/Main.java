package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private static Stage primaryStage; // **Declare static Stage**

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }
	@Override
	public void start(Stage primaryStage) {
		try { 
			
			setPrimaryStage(primaryStage);
			BorderPane root=(BorderPane) FXMLLoader.load(getClass().getResource("/application/mainPage.fxml"));;
			Scene scene = new Scene(root,600,600);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pdf Extractor");
			primaryStage.getIcons().add(new Image(getClass().getResource("/resources/images/extractor.png").toExternalForm()));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
