import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    protected static final String TITLE = "SDD2-Project-22/23";
    protected static BackEnd BACKEND;

    public static void main(String[] args) {

//      ==FRONT-END==
        launch(args);
//      =============
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//      ==FRONT-END==

        Stage stage = FXMLLoader.load(getClass().getResource("Page_Launcher.fxml"));
        stage.setTitle(TITLE);
        stage.show();
//      =============


    }
}
