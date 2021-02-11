import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Pistol extends Weapon {
    public Pistol(Player player, Tile[][] map, Player[] allPlayers, ObservableList<Node> children) {
        super(player, map, allPlayers, children, 5, 10,12,90,12,0);
    }

    public void mousePressed() {
        shoot();
    }
}
