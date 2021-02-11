import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {
    private final static double speed = 30;
    private Tile[][] map;
    private Player[] players;
    private double angle;
    private AnimationTimer updater;
    private Weapon owner;
    private double damage;
    private Player bulletOwner;

    private ObservableList<Node> children;

    public Bullet(double angle, Tile[][] map, Player[] players, ObservableList<Node> children, Weapon owner, double x, double y, Player bulletOwner) {
        setWidth(10);
        setHeight(3);
        this.map = map;
        this.players = players;
        this.angle = angle;
        this.children = children;
        this.owner = owner;
        setTranslateX(x);
        setTranslateY(y);
        setDamageByWeapon();
        children.add(this);
        setRotate(angle+90);
        this.bulletOwner = bulletOwner;
        updater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        updater.start();
    }

    private void setDamageByWeapon() {
        damage = 10;
    }

    private void update() {
        setTranslateX(getTranslateX() - Math.cos(Math.toRadians(angle+90))*speed);
        setTranslateY(getTranslateY() - Math.sin(Math.toRadians(angle+90))*speed);
        if (shouldBeDestroyedByTiles()) destroyBullet(false, null);
        if (hitPlayers()) destroyBullet(true, hitPlayer());
    }
    private boolean hitPlayers() {
        for (Player player:players) {
            if (bulletOwner!=null&&player == bulletOwner) {
                continue;
            }else {
                if (getBoundsInParent().intersects(player.getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }
    private Player hitPlayer() {
        for (Player player:players) {
            if (player == bulletOwner) {
                continue;
            }else {
                if (getBoundsInParent().intersects(player.getBoundsInParent())) {
                    return player;
                }
            }
        }
        return null;
    }
    private boolean shouldBeDestroyedByTiles() {
        for (Tile[] tiles:map) {
            for (Tile tile:tiles) {
                if (getBoundsInParent().intersects(tile.getBoundsInParent())) {
                    if (tile.getType() == Tile.TileType.BORDER|| tile.getType() == Tile.TileType.OBJECT|| tile.getType() == Tile.TileType.WALL) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void destroyBullet(boolean hitPlayer, Player player) {
        if (hitPlayer) {
            player.setHp(player.getHp() - damage);
        }

        owner.getBullets().remove(this);
        children.remove(this);
        updater.stop();
    }
}
