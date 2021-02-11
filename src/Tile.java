import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Tile extends ImageView {
    private boolean walkable;
    protected enum TileType{
        SAND, BOMBSIGHT, BORDER, OBJECT, WALL, SPAWN
    }
    private TileType type;

    public boolean isWalkable() {
        return walkable;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    private void setImg() {
        setImage(new Image("img/"+type.toString().toLowerCase()+".jpg"));
    }
    public Tile(double x, double y, double width, double height, TileType type) {
        this.type = type;
        setImg();
        setTranslateX(x);
        setTranslateY(y);
        setFitWidth(width);
        setFitHeight(height);
        walkable = true;
        if (type == TileType.BORDER|| type == TileType.WALL||type == TileType.OBJECT) walkable = false;
    }

    public static TileType getTileType(String wrd) {
        if (wrd.equals("b")) {
            return TileType.BORDER;
        } else if (wrd.equals("g")) {
            return TileType.SAND;
        } else if (wrd.equals("w")) {
            return TileType.WALL;
        } else if (wrd.equals("o")) {
            return TileType.OBJECT;
        } else if (wrd.equals("p")) {
            return TileType.SPAWN;
        }return TileType.BOMBSIGHT;
    }
    public String getCharRepresent() {
        switch (type) {
            case BORDER:
                return "b";
            case SAND:
                return "g";
            case WALL:
                return "w";
            case OBJECT:
                return "o";
            case SPAWN:
                return "p";
            default:
                return "s";
        }
    }
}
