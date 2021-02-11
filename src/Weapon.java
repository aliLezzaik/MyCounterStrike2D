import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.LinkedList;


public abstract class Weapon {
    private int damage;
    private int price;
    private final LinkedList<Bullet> bullets = new LinkedList<>();
    private Player player;
    private Tile[][] map;
    private Player[] allPlayers;
    private ObservableList<Node> children;
    private int maxLoad;
    private int maxBullets;
    private int currBullets;
    private int curLoad;
    private Timeline reloader;

    public int getMaxLoad() {
        return maxLoad;
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    public int getCurrBullets() {
        return currBullets;
    }

    public int getCurLoad() {
        return curLoad;
    }

    public Weapon(Player player, Tile[][] map, Player[] allPlayers, ObservableList<Node> children, int damage, int price, int maxLoad, int maxBullets, int currBullets, int curLoad) {
        this.player = player;
        this.map = map;
        this.allPlayers = allPlayers;
        this.children = children;
        this.price = price;
        this.damage = damage;
        this.maxLoad = maxLoad;
        this.maxBullets = maxBullets;
        this.currBullets = currBullets;
        this.curLoad = curLoad;
        canShoot = true;
        reloader = new Timeline();
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    public void mousePressed() {

    }

    public int getDamage() {
        return damage;
    }

    public int getPrice() {
        return price;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile[][] getMap() {
        return map;
    }

    public Player[] getAllPlayers() {
        return allPlayers;
    }

    public ObservableList<Node> getChildren() {
        return children;
    }

    public void mouseReleased() {

    }

    private void reload() {
        currBullets = maxBullets;
        curLoad -= currBullets;
        if (curLoad < 0) {
            currBullets += curLoad;
            curLoad = 0;
        }
        if (currBullets < 0) {
            currBullets = 0;
        }
    }

    private boolean canShoot;

    private void manageBullets() {
        currBullets--;
        if (currBullets <= 0) {
            currBullets = 0;
            canShoot = false;
            reloader.setCycleCount(1);
            reloader.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    canShoot = true;
                    if (curLoad == 0) canShoot = false;
                    reload();

                }
            }));
            reloader.play();
        }
    }

    public void shoot() {
        shoot(getPlayer().getRotate());

    }

    public void shoot(double angle) {

        if (canShoot) {
            manageBullets();
            getBullets().add(new Bullet(angle, getMap(), getAllPlayers(), getChildren(), this, getPlayer().getTranslateX() + getPlayer().getFitWidth() / 2, getPlayer().getTranslateY() + getPlayer().getFitHeight() / 2, player));
        }

    }
}
