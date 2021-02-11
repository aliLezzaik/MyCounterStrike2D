import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Random;

public class DustII extends GameScene{
    private Player[] mobs = new Player[3];
    public DustII() {

        super("maps/DeDust.csv");
        final Random random = new Random();
        for (int i =0;i<3;i++) {
            Tile pos = randomWalkableTile();
            mobs[i] = new Player(this,false, pos.getTranslateX(), pos.getTranslateY());
        }
        Player p = new Player(this, 400, 400);

        getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                p.setKeyPressed(event);
            }
        });
        getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                p.setKeyReleased(event);
            }
        });
        getScene().setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                p.setMouseMoved(event);
            }
        });
        getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                p.setMousePressed(event);
            }
        });
        getScene().setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                p.setMouseReleased(event);
            }
        });
    }

    public Player[] getMobs() {
        return mobs;
    }
}
