package seniorproject;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

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
    private Pane chatField;
    
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
        Bubble userRequest = new Bubble(inputText.getText());
        Bubble response = new Bubble();

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
                response.setContent(Actions.getJoke());
                break;
            case "grades":
                response.setContent(Actions.getGrades());
                break;
            case "weather":
                response.setContent(Actions.getWeatherSummary());
                break;
            case "greeting":
                response.setContent(Actions.getGreeting());
                break;
            default:
                response.setContent("Hmm, I don't understand what you're asking");
                break;
        }

        // Format response
        userRequest.style(true);
        response.style(false);

        // Display in chatbox
        container.setContent(chatBox);
        chatBox.setSpacing(10);
        chatBox.getChildren().add(userRequest);
        chatBox.getChildren().add(response);
	}
}