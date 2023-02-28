import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    private static final String TITLE = "SDD2-Project-22/23";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = new AnchorPane(); //TODO change this to fxml

        Scene scene = new Scene(root,WIDTH,HEIGHT);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
