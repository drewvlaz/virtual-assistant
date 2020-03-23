// This class controls the bubble created for messaging GUI

package assistant;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class Bubble extends HBox {
    // Instance variables
    private Label content;
    private StackPane bubble = new StackPane();

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
        content.getStyleClass().add("chat-bubble");
        content.getStyleClass().add(user ? "user" : "response");

        // Wrap text and adjust length if too long
        // content.setMaxWidth(500 * 2 / 3.0);
        // content.setWrapText(true);

        bubble.getChildren().addAll(content);

        // Add message content to HBox bubble and adjust position
        this.getChildren().add(bubble);
        this.setAlignment(user ? Pos.BASELINE_RIGHT : Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10, 10, 10, 10));;
    }
}