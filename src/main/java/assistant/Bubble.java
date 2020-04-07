// This class controls the bubble created for messaging GUI

package assistant;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

import javax.swing.text.AttributeSet.FontAttribute;

import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Paint;

public class Bubble extends HBox {
    // Instance variables
    private Text content;
    private Rectangle background;
    private StackPane bubble;
    private final int FONT_SIZE = 14;

    // Constructors
    public Bubble() {
        super(); 
        this.background = new Rectangle();
        this.bubble = new StackPane();
    }
    public Bubble(String content) {
        super();
        this.content = new Text(content);
        this.background = new Rectangle();
        this.bubble = new StackPane();
    }

    // Accessor
    public void setContent(String content) {
        this.content = new Text(content);
    }

    // Mutator
    public String getContent() {
        return content.getText();
    }

    // Style the bubble appropriately
    public void configure(boolean user) {
        // Set colors and text font, css right work well for this
        content.setFont(new Font("Arial", FONT_SIZE));
        content.setFill(Paint.valueOf(user ? "#ffffff" : "#000000"));

        // Set bubble background shape and color
        background.getStyleClass().add("chat-bubble-background");
        background.getStyleClass().add(user ? "user" : "response");

        // Wrap text and adjust length if too long
        content.setWrappingWidth(Math.min(content.getBoundsInLocal().getWidth(), (500 * 2 / 3.0)));

        // Set bubble background width/height and add padding
        background.setWidth(content.getBoundsInLocal().getWidth() + FONT_SIZE);
        background.setHeight(content.getBoundsInLocal().getHeight() + FONT_SIZE);

        // Combine content and background into bubble
        bubble.getChildren().addAll(background, content);

        // Add message content to HBox bubble and adjust positioning
        this.getChildren().add(bubble);
        this.setAlignment(user ? Pos.BASELINE_RIGHT : Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10, 10, 10, 10));;
    }

    // Adjusts the padding so that the bubble is adjacent
    public void setAdjacent(boolean first, boolean last) {
        // Insets(top, right, bottom, left)
        this.setPadding(new Insets(first ? 10 : 0, 10, last ? 10 : 0, 10));;
    }
}