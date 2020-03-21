// This class controls the bubble created for messaging GUI

package seniorproject;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Bubble extends HBox {
    // Instance variables
    private Label content;

    // Constructors
    public Bubble() { super(); }
    public Bubble(String content) {
        super();
        this.content = new Label(content);
    }

    // Accessor
    public void setContent(String content) {
        this.content = new Label(content);
    }

    // Mutator
    public String getContent() {
        return content.getText();
    }

    // Style the bubble appropriately
    public void configure(boolean user) {
        // Set bubble and text colors and text font
        content.getStyleClass().add(user ? "user-chat-bubble" : "response-chat-bubble");
        content.setFont(Font.font("HelveticaNeue", FontWeight.EXTRA_LIGHT, 14));

        // Wrap text and adjust length if too long
        content.setWrapText(true);
        content.setMaxWidth(500 * 2 / 3.0);

        // Add message content to HBox bubble and adjust position
        this.getChildren().add(content);
        this.setAlignment(user ? Pos.BASELINE_RIGHT : Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10, 10, 10, 10));;
    }
}