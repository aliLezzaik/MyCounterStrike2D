import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MapMaker extends DefaultDisplay {
    private Tile[][] map;
    private Button save;
    private Tile[] options;
    private Tile current;
    private Button setMapSize;
    private TextField width, height;
    private GridPane mapContainer;
    private void setSetMapSize() {
        width = new TextField();
        width.setPromptText("Width:(10-40)");
        height = new TextField();
        height.setPromptText("Height:(10-40)");
        setMapSize = new Button("Set the Map!");
        setMapSize.relocate(800,100);
        width.relocate(800,20);
        height.relocate(800,65);
        setMapSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This input is invalid. Try again");
                try{
                    int w = Integer.parseInt(width.getText());
                    int h = Integer.parseInt(height.getText());
                    if (w<10||w>40||h<10||h>40) {
                        alert.showAndWait();
                        return;
                    }
                    for (int r=0;r<map.length;r++) {
                        for (int c =0;c<map[r].length;c++) {
                            getChildren().remove(map[r][c]);
                        }
                    }
                    setMap(h,w);
                }catch (NumberFormatException error) {
                    alert.showAndWait();
                }
            }
        });
        getChildren().addAll(width,height,setMapSize);
    }
    private void setOptions() {
        options = new Tile[Tile.TileType.values().length];
        for (int i =0;i<options.length;i++) {
            options[i] = new Tile(0,0,30,30, Tile.TileType.values()[i]);
            options[i].setFitWidth(30);
            options[i].setFitHeight(30);
            options[i].relocate(30*i,700);
            final int index = i;
            options[i].setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    current = options[index];
                }
            });
        }
        getChildren().addAll(options);
        current = options[0];
    }
    private void setMap(int row, int column) {
        if (mapContainer!= null) {
            getChildren().remove(mapContainer);
            mapContainer = null;
        }
        mapContainer = new GridPane();
        if (map!=null) {
            for (int r=0;r<map.length;r++) getChildren().removeAll(map[r]);
        }
        map = new Tile[row][column];
        for (int r =0;r<row;r++) {
            for (int c =0;c<column;c++) {
                map[r][c] = new Tile(0,0,15,15, Tile.TileType.SAND);
                final int frow = r;
                final int fcol = c;
                map[r][c].setOnMouseMoved(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (down) {
                            map[frow][fcol].setType(current.getType());
                            map[frow][fcol].setImage(current.getImage());
                        }

                    }
                });
                mapContainer.add(map[r][c],c,r);
            }
        }
        if (!getChildren().contains(mapContainer))
        getChildren().add(mapContainer);
        int objWidth = 15* column;
        int remainder = 800 - objWidth;
        mapContainer.relocate(remainder/2, 20);
    }

    private void saveMap() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV files(*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(Execute.getStage());
        if (file!=null) {
            try{
                FileWriter writer = new FileWriter(file);
                for (int r=0;r<20;r++) {
                    for (int c=0;c<20;c++) {
                        if (c!=19) {
                            writer.write(map[r][c].getCharRepresent()+", ");
                        } else writer.write(map[r][c].getCharRepresent()+"\n");
                    }
                }
                writer.close();
            }catch (IOException err) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error 404;");
                alert.setContentText("Error 404; file not found.");
            }
        }
    }
    private void setSave(double x, double y) {
        save = new Button("Save this map!");
        save.relocate(x,y);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveMap();
            }
        });
        getChildren().add(save);
    }
    private Tile copy() {
        return new Tile(0,0,15,15,current.getType());
    }

    private void setSceneMouse() {
        getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    down = true;
                }else down = false;
            }
        });
    }
    private boolean down;
    public MapMaker() {
        setOptions();
        setMap(40,40);
        setSave(500,700);
        down = false;
        setSceneMouse();
        Execute.scale(this.getScene());
        setSetMapSize();
    }

}
