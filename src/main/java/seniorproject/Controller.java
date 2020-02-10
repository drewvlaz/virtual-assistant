package seniorproject;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import org.json.simple.parser.ParseException;

public class Controller {
    private MultinomialNB model;

    // FXML elements automatically loaded
	@FXML
    private TextField inputText;

	@FXML
    private TextArea outputText;

	@FXML
	private Button sendBtn;
	
	// Constructor
	public Controller() {}
	
	// @FXML
    // private void initialize() {}
    
	@FXML
    private void respond() throws IOException, ParseException {
        // Reset text field from previous request
        outputText.clear();

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
                outputText.setText(Actions.getJoke());
                break;
            case "grades":
                outputText.setText(Actions.getGrades());
                break;
            case "weather":
                outputText.setText(Actions.getWeatherSummary());
                break;
            case "greeting":
                outputText.setText(Actions.getGreeting());
                break;
            default:
                outputText.setText("Hmm, I don't understand what you're asking");
                break;
        }
	}
}