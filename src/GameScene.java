import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public abstract class GameScene extends DefaultDisplay {
    private BombSight bombSightA, bombSightB;
    private Spawn counterTerrorists, terrorists;
    private final int tileSize = 20;
    private final int tileQuantity = 1000;
    private final double worldWidth = 10000, worldHeight = 10000;
    private final Random random = new Random();
    private Tile[][] map;
    private Label bulletCount;

    private void setBulletCount() {
        bulletCount = new Label("0|0");
        getChildren().add(bulletCount);
    }

    public void relocateBulletCount(double x, double y) {
        bulletCount.setTranslateX(x);
        bulletCount.setTranslateY(y);
    }

    public void updateBulletCount(int mag, int currentBullets) {
        bulletCount.setText(currentBullets + "|" + mag);
    }

    private void loadMap(File csvFile) {
        try {
            ArrayList<String[]> contents = new ArrayList<>();
            Scanner reader = new Scanner(csvFile);
            while (reader.hasNextLine()) {
                contents.add(reader.nextLine().split(", "));
            }
            map = new Tile[contents.size()][contents.get(0).length];
            for (int r = 0; r < map.length; r++) {
                for (int c = 0; c < map[r].length; c++) {
                    double x = c * (worldWidth / map[r].length);
                    double y = r * (worldHeight / map.length);
                    double width = worldWidth / map[r].length;
                    double height = worldHeight / map.length;
                    map[r][c] = new Tile(x, y, width, height, Tile.getTileType(contents.get(r)[c]));
                    getChildren().add(map[r][c]);
                }
            }
        } catch (IOException error) {
            System.out.println("Error 404");
        }
        setBulletCount();
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getTileQuantity() {
        return tileQuantity;
    }

    public BombSight getBombSightA() {
        return bombSightA;
    }

    public void setBombSightA(BombSight bombSightA) {
        this.bombSightA = bombSightA;
    }

    public BombSight getBombSightB() {
        return bombSightB;
    }

    public void setBombSightB(BombSight bombSightB) {
        this.bombSightB = bombSightB;
    }

    public Spawn getCounterTerrorists() {
        return counterTerrorists;
    }

    public void setCounterTerrorists(Spawn counterTerrorists) {
        this.counterTerrorists = counterTerrorists;
    }

    public Spawn getTerrorists() {
        return terrorists;
    }

    public void setTerrorists(Spawn terrorists) {
        this.terrorists = terrorists;
    }

    public Tile[][] getMap() {
        return map;
    }


    public GameScene(String fileDirectory) {
        loadMap(new File(fileDirectory));
    }

    public Tile randomWalkableTile() {
        int r = 0, c = 0;
        while (map[r][c].getType() != Tile.TileType.SAND) {
            r = random.nextInt(map.length);
            c = random.nextInt(map[r].length);
        }
        return map[r][c];
    }
    public Player[] getMobs() {
        return null;
    }
}
