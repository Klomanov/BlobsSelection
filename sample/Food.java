package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Entity{
    Rectangle r;

    public Food(AnchorPane root, int x, int y){
        super(root, x, y, Constants.FOOD_SIZE);
    }

    public Food(AnchorPane root){
        super(root, Constants.FOOD_SIZE);
    }

    public void show(){
        r = new Rectangle();
        r.setX(x);
        r.setY(y);
        r.setWidth(Constants.FOOD_SIZE);
        r.setHeight(Constants.FOOD_SIZE);
        r.setFill(Color.DARKORANGE);
        root.getChildren().add(r);
    }

    @Override
    void delete() {
        root.getChildren().remove(r);
    }
}
