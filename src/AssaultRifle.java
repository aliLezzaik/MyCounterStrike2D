import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class AssaultRifle extends Weapon{
    private boolean shooting;
    private AnimationTimer updater;
    public AssaultRifle(Player player, Tile[][] map, Player[] allPlayers, ObservableList<Node> children) {
        super(player, map, allPlayers, children, 20, 100,30,90,30,90);
        updater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (shooting) shoot();
            }
        };
        updater.start();
    }

    @Override
    public void mousePressed() {
        shooting = true;
    }

    public void mouseReleased() {
        shooting = false;
    }

    public void StopAssaultRifle() {
        updater.stop();
    }
}
