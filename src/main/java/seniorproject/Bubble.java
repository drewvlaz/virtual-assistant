// This class controls the bubble created for messaging GUI

package seniorproject;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

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
    public void style(boolean user) {
        content.getStyleClass().add(user ? "user-chat-bubble" : "response-chat-bubble");
        content.setTextFill(Paint.valueOf(user ? "#ffffff" : "#000000"));
        content.setWrapText(true);
        this.getChildren().add(content);
        this.setAlignment(user ? Pos.BASELINE_RIGHT : Pos.BASELINE_LEFT);
    }
}