// Controller for main GUI application

package assistant;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import com.gargoylesoftware.htmlunit.javascript.host.fetch.Response;
import com.jfoenix.controls.JFXButton;

import org.json.simple.parser.ParseException;

public class Controller {
    // Instance variables
    private MultinomialNB model;
    private boolean continuedConversation;

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

    // Initialize is called after all FXML elements are loaded in
    @FXML
    private void initialize() {
        // Enable auto scroll down when user sends new input
        container.vvalueProperty().bind(chatBox.heightProperty());
    }
    
    // Sends user input to virtual assistant and displays it
    @FXML
    private void send() throws IOException, ParseException {
        // Ensure user input text
        if (inputText.getText().trim().equals("")) {
            inputText.clear();
            return;
        }

        // Display user input in messaging bubble
        Bubble userInput = new Bubble(inputText.getText().trim());
        userInput.configure(true);
        chatBox.getChildren().add(userInput);

        // Get response from irtual assistant
        respond();

        // Clear user input from field for next response
        inputText.clear();
    }

    // Classifies user input and responds accordingly
    @FXML
    private void respond() throws IOException, ParseException {
        if (continuedConversation) {
            followUp();
            continuedConversation = false;
            return;
        }
        // Initialize messaging bubble
        ArrayList<Bubble> computerResponses = new ArrayList<Bubble>();

        // Classify user input
        String label = model.classify(inputText.getText().trim());
        System.out.println(model.getFormattedProbabilities());

        // Execute user's request
        switch (label) {
            case "jokes":
                computerResponses.add(new Bubble(Actions.getJoke()));
                break;
            case "grades":
                computerResponses.add(new Bubble(Actions.getGrades()));
                break;
            case "weather":
                computerResponses.add(new Bubble(Actions.getWeatherSummary()));
                break;
            case "greeting":
                computerResponses.add(new Bubble(Actions.getGreeting()));
                break;
            case "unknown":
                computerResponses.add(new Bubble("Hmm, I don't understand what you're asking"));
                computerResponses.add(new Bubble("Would you like me to look it up?"));
                continuedConversation = true;
                break;
        }

        // Display response in messaging bubble
        for (int i = 0; i < computerResponses.size(); i++) {
            // Configure
            computerResponses.get(i).configure(false);
            if (continuedConversation) {
                computerResponses.get(i).setAdjacent(i == 0, i == computerResponses.size() - 1);
            }
            // Add to display
            chatBox.getChildren().add(computerResponses.get(i));
        }
    }
    
    public void followUp() {
        Scanner sc = new Scanner(inputText.getText());

        // Create and train model
        MultinomialNB yesOrNo = new MultinomialNB("./src/main/resources/yesno.json");
        yesOrNo.prepareData();
        yesOrNo.train();

        // Initialize messaging bubbles
        Bubble computerResponse = new Bubble();

        // Classify user input
        String label = yesOrNo.classify(inputText.getText().trim());
        System.out.println(model.getFormattedProbabilities());

        switch (label) {
            case "yes":
                computerResponse.setContent("Here you go:");
                break;
            case "no":
                computerResponse.setContent("Okay");
                break;
        }

        // Display response in messaging bubble
        computerResponse.configure(false);
        chatBox.getChildren().add(computerResponse);
    }
}