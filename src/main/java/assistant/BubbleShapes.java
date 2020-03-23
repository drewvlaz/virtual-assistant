// This class controls the bubble created for messaging GUI

package assistant;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;

public class BubbleShapes extends HBox {
    // Instance variables
    private Text content;
    private Rectangle background = new Rectangle();
    private StackPane bubble = new StackPane();

    // Constructors
    public BubbleShapes() { super(); }
    public BubbleShapes(String content) {
        super();
        this.content = new Text(content);
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
        // Set bubble and text colors and text font
        content.getStyleClass().add("chat-bubble-text");
        content.getStyleClass().add(user ? "user" : "response");
        content.setFont(new Font(14));
        background.getStyleClass().add("chat-bubble-background");
        background.getStyleClass().add(user ? "user" : "response");

        // Wrap text and adjust length if too long
        // content.setMaxWidth(500 * 2 / 3.0);
        // content.setWrapText(true);
        // content.setWrappingWidth(500 * 2 / 3.0);

        System.out.println(content.getBoundsInLocal().getWidth());
        System.out.println(Math.min(content.getBoundsInLocal().getWidth(), 500 * 2 / 3.0));
        // content.setWrappingWidth(Math.min(content.getBoundsInLocal().getWidth(), (500 * 2 / 3.0)));

        // Set bubble background properties
        background.setWidth(content.getBoundsInLocal().getWidth() + 15);
        background.setHeight(content.getBoundsInLocal().getHeight() + 15);

        bubble.getChildren().addAll(background, content);

        // Add message content to HBox bubble and adjust position
        this.getChildren().add(bubble);
        this.setAlignment(user ? Pos.BASELINE_RIGHT : Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10, 10, 10, 10));;
    }
}