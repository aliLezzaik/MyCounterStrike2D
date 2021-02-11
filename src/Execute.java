import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.awt.*;

public class Execute extends Application {
    private static Stage stage;
    protected static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    protected static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        primaryStage.setScene(new DustII().getScene());
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.setResizable(false);
        primaryStage.show();
    }
    public static void scale(Scene scene) {
        Scale scale = new Scale(screenWidth/1024,screenHeight/768);
        scale.setPivotX(0);
        scale.setPivotY(0);
        scene.getRoot().getTransforms().setAll(scale);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
