package seniorproject;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;

import org.json.simple.parser.ParseException;

public class Controller {
    private MultinomialNB model;

    // FXML elements automatically loaded
	@FXML
    private TextField inputText;

	// @FXML
    // private TextArea outputText;

	@FXML
    private JFXButton sendBtn;

	@FXML
    private ScrollPane container;

	@FXML
    private VBox chatBox;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
	
	// Constructor
	public Controller() {}
	
	@FXML
    private void initialize() {}
    
	@FXML
    private void respond() throws IOException, ParseException {
        Label userRequest = new Label(inputText.getText());
        Label response = new Label();

        // Adjust allignment for bubble
        userRequest.setPrefWidth(chatBox.getPrefWidth());
        userRequest.setAlignment(Pos.CENTER_RIGHT);

        // Create and train model
        MultinomialNB model = new MultinomialNB();
        model.readTrainingData("./src/main/resources/data.json");
        model.prepareData();
        model.train();

        // Classify text
        String label = model.classify(inputText.getText());
        System.out.println(model.getFormattedProbabilities());

        // Execute user's request
        switch (label) {
            case "jokes":
                response.setText(Actions.getJoke());
                break;
            case "grades":
                response.setText(Actions.getGrades());
                break;
            case "weather":
                response.setText(Actions.getWeatherSummary());
                break;
            case "greeting":
                response.setText(Actions.getGreeting());
                break;
            default:
                response.setText("Hmm, I don't understand what you're asking");
                break;
        }

        // Display in chatbox
        container.setContent(chatBox);
        response.setAlignment(Pos.CENTER_LEFT);
        chatBox.getChildren().add(userRequest);
        chatBox.getChildren().add(response);
	}
}