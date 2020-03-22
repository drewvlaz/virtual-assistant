package assistant;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
public class GUI extends Application
{
    public static void main(String[] args) 
    {
        // Launch GUI
        Application.launch(args);
    }
     
    @Override
    public void start(Stage stage) throws IOException 
    {
        // Create the FXMLLoader 
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new Controller());

        // Path to the FXML File
        String fxmlDocPath = "./src/main/resources/AssistantMessenger.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
         
        // Create the Pane and all Details
        AnchorPane root = (AnchorPane)loader.load(fxmlStream);
         
        // Create the Scene
        Scene scene = new Scene(root);

        // Add css elements
        File css = new File("./src/main/resources/style.css");
        scene.getStylesheets().add("file:///" + css.getAbsolutePath().replace("\\", "/"));

        // Set scene and title
        stage.setScene(scene);
        stage.setTitle("Virtual Assistant");

        // Display the Stage
        stage.show();
    }
}