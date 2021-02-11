import javafx.animation.AnimationTimer;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.Random;


public class Player extends ImageView {
    private double hp;
    private double money;
    private boolean isMain;

    private final Weapon[] weapons = new Weapon[4];
    private Weapon selected;
    private Player[] players;

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    private PerspectiveCamera camera;
    private AnimationTimer updater;
    private double xAcc, yAcc;
    private double speed = 10;
    private final double width = 50;
    private final double height = 50;
    private GameScene scene;

    private void setCamera(GameScene scene) {
        camera = new PerspectiveCamera();
        while (!allowedToWalk(scene)) {
            setTranslateX(new Random().nextInt(5000));
            setTranslateY(new Random().nextInt(5000));
        }
        scene.getScene().setCamera(camera);

    }

    private void setPlayer(GameScene scene, double x, double y) {
        this.hp = 100;
        setTranslateX(x);
        setTranslateY(y);
        setImage(new ImageView("img/t.jpg").getImage());
        setFitWidth(width);
        setFitHeight(height);
        scene.getChildren().add(this);

        weapons[0] = new AssaultRifle(this, scene.getMap(), players, scene.getChildren());

        selected = weapons[0];
        weapons[1] = new Pistol(this, scene.getMap(), players, scene.getChildren());
        weapons[2] = new ShotGun(this, scene.getMap(), players, scene.getChildren());
    }

    public void setMousePressed(MouseEvent event) {
        selected.mousePressed();
    }

    public void setMouseReleased(MouseEvent event) {
        selected.mouseReleased();
    }


    private double angleBetween(double x, double y) {
        return Math.toDegrees(Math.atan2(y - getTranslateY(), x - getTranslateX()));
    }

    public void setMouseMoved(MouseEvent event) {
        setRotate(angleBetween(event.getX(), event.getY()) + 90);
    }

    public void setKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                yAcc = -speed;
                break;
            case S:
                yAcc = speed;
                break;
            case A:
                xAcc = -speed;

                break;
            case D:
                xAcc = speed;
                break;
        }
    }

    public void setKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                yAcc = 0;
                break;
            case S:
                yAcc = 0;
                break;
            case A:
                xAcc = 0;
                break;
            case D:
                xAcc = 0;
                break;
            case DIGIT1:
                selected = weapons[0];
                break;
            case DIGIT2:
                selected = weapons[1];
                break;
            case DIGIT3:
                selected = weapons[2];
                break;
        }
    }

    private boolean allowedToWalk(GameScene scene) {
        Rectangle playerOnNext = new Rectangle(getTranslateX() + xAcc, getTranslateY() + yAcc, getFitWidth(), getFitHeight());
        for (Tile[] tiles : scene.getMap()) {
            for (Tile tile : tiles) {
                if (!tile.isWalkable() && playerOnNext.getBoundsInParent().intersects(tile.getBoundsInParent())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updatePosition() {
        if (isMain) {
            setTranslateX(getTranslateX() + xAcc);
            setTranslateY(getTranslateY() + yAcc);
            if (isMain) {
                camera.setTranslateX(getTranslateX() - (Execute.screenWidth / 2) + (getFitWidth()) / 2);
                camera.setTranslateY(getTranslateY() - (Execute.screenHeight / 2) + (getFitHeight()) / 2);
            }
        }else {
            //Todo
        }
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    private void update(GameScene scene) {
        if (isMain) {
            updater = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    scene.relocateBulletCount(camera.getTranslateX() + Execute.screenWidth - 50, camera.getTranslateY() + Execute.screenHeight - 50);
                    scene.updateBulletCount(selected.getCurLoad(), selected.getCurrBullets());
                    destroyPlayer();
                    if (allowedToWalk(scene))
                        updatePosition();
                }
            };
        }else {
            updater = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (allowedToWalk(scene)) {
                        updatePosition();
                    }
                    destroyPlayer();
                }
            };
        }
        updater.start();
    }
    private void destroyPlayer() {
        if (hp<=0) {
            if (isMain) {
                System.exit(0);
            }else {
                updater.stop();
                scene.getChildren().remove(this);
            }
        }
    }

    public Player(GameScene scene, double x, double y) {
        setCamera(scene);
        this.players = scene.getMobs();
        setPlayer(scene, x, y);
        update(scene);
        isMain = true;
        this.scene = scene;
        System.out.println(players.length);
    }
    public Player(GameScene scene, boolean isMain, double x, double y) {
        this.scene = scene;
        this.players = scene.getMobs();
        if (!isMain) {
            isMain = false;
        } else {
            setCamera(scene);
            isMain = true;
        }
        setPlayer(scene, x, y);
        update(scene);
    }
}
