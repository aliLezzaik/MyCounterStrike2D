import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class DefaultDisplay extends Group {
    private final Scene scene = new Scene(this,1024,768);
    public DefaultDisplay() {
        scene.setFill(Color.BLACK);
    }
}
