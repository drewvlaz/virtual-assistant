// Controller for main GUI application

package assistant;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import com.jfoenix.controls.JFXButton;

import org.json.simple.parser.ParseException;

public class Controller {
    // Instance variables
    private MultinomialNB model;

    // FXML elements automatically loaded
    @FXML private TextField inputText;
    @FXML private JFXButton sendBtn;
    @FXML private ScrollPane container;
    @FXML private VBox chatBox;
	
    // Constructor
    public Controller() {
        // Create and train model
        model = new MultinomialNB("./src/main/resources/data.json");
        model.prepareData();
        model.train();
    }

    @FXML
    private void initialize() {}
    
    // Responds to user input and updates GUI accordingly
    @FXML
    private void respond() throws IOException, ParseException {
        // Ensure user input text
        if (inputText.getText().equals("")) {
            return;
        }

        // Initialize messaging bubbles
        Bubble userInput = new Bubble(inputText.getText());
        Bubble computerResponse = new Bubble();

        // Classify user input
        String label = model.classify(inputText.getText());
        System.out.println(model.getFormattedProbabilities());

        // Execute user's request
        switch (label) {
            case "jokes":
                computerResponse.setContent(Actions.getJoke());
                break;
            case "grades":
                computerResponse.setContent(Actions.getGrades());
                break;
            case "weather":
                computerResponse.setContent(Actions.getWeatherSummary());
                break;
            case "greeting":
                computerResponse.setContent(Actions.getGreeting());
                break;
            default:
                computerResponse.setContent("Hmm, I don't understand what you're asking");
                break;
        }

        // Format bubbles
        userInput.configure(true);
        computerResponse.configure(false);

        // Display user input and computer response
        chatBox.getChildren().add(userInput);
        chatBox.getChildren().add(computerResponse);

        // Auto scroll down 
        container.vvalueProperty().bind(chatBox.heightProperty());

        // Clear user input from field for next response
        inputText.clear();
	}
}