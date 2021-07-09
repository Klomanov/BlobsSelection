package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public abstract class Entity {
    int x, y, size;
    AnchorPane root;

    public Entity(AnchorPane root, int x, int y, int size){
        this.root = root;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public Entity(AnchorPane root, int size){
        Random r = new Random();
        y = r.nextInt(Constants.FIELD_SIZE);
        x = 10 + Constants.WINDOW_WIDTH - Constants.FIELD_SIZE +
                r.nextInt(Constants.FIELD_SIZE - 10);
        this.root = root;
        this.size = size;
    }

    abstract void show();

    abstract void delete();

}
