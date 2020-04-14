// Controller for main GUI application

package assistant;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import com.jfoenix.controls.JFXButton;

import org.json.simple.parser.ParseException;

public class Controller {
    // Instance variables
    private MultinomialNB model;
    private String continuedConversationCategory;

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
        // TODO: AnimationTimer for displaying instantly?
        Bubble userInput = new Bubble(inputText.getText().trim());
        userInput.configure(true);
        chatBox.getChildren().add(userInput);

        // Get response from virtual assistant
        respond(model.classify(inputText.getText().trim()));
        System.out.println(model.getFormattedProbabilities());

        // Clear user input from field for next response
        inputText.clear();
    }

    // Classifies user input and responds accordingly
    @FXML
    private void respond(String category) throws IOException, ParseException {
        // Check if follow up needed
        if (continuedConversationCategory != null) {
            followUp();
            return;
        }

        // Initialize messaging bubbles
        ArrayList<Bubble> computerResponses = new ArrayList<Bubble>();

        // Execute user's request
        switch (category) {
            case "jokes":
                computerResponses.add(new Bubble(Actions.getJoke()));
                computerResponses.add(new Bubble("Would you like to hear another joke?"));
                continuedConversationCategory = category;
                break;
            case "grades":
                computerResponses.add(new Bubble(Actions.getGrades()));
                break;
            case "weather":
                computerResponses.add(new Bubble(Actions.getWeatherSummary()));
                break;
            case "music":
                computerResponses.add(new Bubble(Actions.playMusic()));
                break;
            case "greeting":
                computerResponses.add(new Bubble(Actions.getGreeting()));
                break;
            case "thanks":
                computerResponses.add(new Bubble(Actions.getPoliteResponse()));
                break;
            case "unknown":
                computerResponses.add(new Bubble("Hmm, I don't understand what you're asking"));
                computerResponses.add(new Bubble("Would you like me to look it up?"));
                continuedConversationCategory = category;
                break;
        }

        // Display response in messaging bubble
        for (int i = 0; i < computerResponses.size(); i++) {
            // Configure
            computerResponses.get(i).configure(false);
            if (computerResponses.size() > 1) {
                computerResponses.get(i).setAdjacent(i == 0, i == computerResponses.size() - 1);
            }
            // Add to display
            chatBox.getChildren().add(computerResponses.get(i));
        }
    }
    
    // Follows up and asks if user wants to continue
    private void followUp() throws IOException, ParseException {
        // Reset flag
        String category = continuedConversationCategory;
        continuedConversationCategory = null;

        // Create and train model
        MultinomialNB yesOrNo = new MultinomialNB("./src/main/resources/yes_or_no.json");
        yesOrNo.prepareData();
        yesOrNo.train();

        // Classify user input
        String affirmation = yesOrNo.classify(inputText.getText().trim());
        System.out.println(yesOrNo.getFormattedProbabilities());

        switch (affirmation) {
            case "yes":
                if (category == "unknown") {
                    // Confirm
                    Bubble computerResponse = new Bubble("Sure");
                    computerResponse.configure(false);
                    chatBox.getChildren().add(computerResponse);

                    // Get text that was not understood and look it up
                    String text = ((Bubble)chatBox.getChildren().get(chatBox.getChildren().size() - 5)).getContent();
                    Actions.lookUp(text);
                }
                else {
                    respond(category);
                }
                break;
            case "no":
                Bubble computerResponse = new Bubble("Okay");
                computerResponse.configure(false);
                chatBox.getChildren().add(computerResponse);
                break;
        }
    }
}