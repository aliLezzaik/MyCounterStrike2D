import javafx.collections.ObservableList;
import javafx.scene.Node;

public class ShotGun extends Weapon{
    public ShotGun(Player player, Tile[][] map, Player[] allPlayers, ObservableList<Node> children) {
        super(player, map, allPlayers, children, 15, 200,8,32,8,32);
    }

    public void mousePressed() {
        for (int i =0;i<3;i++) {
            shoot(getPlayer().getRotate()-(10)*i);
            shoot(getPlayer().getRotate()+(10)*i);
        }
    }
}
