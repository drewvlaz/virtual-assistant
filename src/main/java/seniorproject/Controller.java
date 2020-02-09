package seniorproject;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.json.simple.parser.ParseException;

public class Controller {
	@FXML
	// The reference of inputText will be injected by the FXML loader
	private TextField inputText;
	
	// The reference of outputText will be injected by the FXML loader
	@FXML
	private TextArea outputText;
	
	// location and resources will be automatically injected by the FXML loader	
	@FXML
	private URL location;
	
	@FXML
	private ResourceBundle resources;
	
	// Add a public no-args constructor
	public Controller() {}
	
	@FXML
	private void initialize() {}
	
	@FXML
    private void printOutput() throws IOException, ParseException {
        MultinomialNB model = new MultinomialNB();
        String label;

        model.readTrainingData("./src/main/resources/data.json");
        model.prepareData();
        model.train();

        label = model.classify(inputText.getText());
        String probabilities = model.getFormattedProbabilities();
        System.out.println(probabilities);

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
            case "search":
                outputText.setText("What would you like me to look-up? ");
                break;
            default:
                outputText.setText("Hmm, I don't understand what you're asking");
                break;
        }
	}
}