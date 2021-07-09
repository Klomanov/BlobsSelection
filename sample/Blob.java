package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

enum Type{
    Square,
    Circle
}

public class Blob extends Entity{
    double speed;
    int collectedFood = 0;
    double seekingRange;
    Color color;
    Rectangle r;
    Circle c;
    Type type;

    public Blob(AnchorPane root, int x, int y, double speed, double seekingRange,
                Color color, Type type){
        super(root, x, y, Constants.BLOB_SIZE);
        this.speed = speed;
        this.seekingRange = seekingRange;
        this.type = type;
        this.color = color;
    }

    public Blob(AnchorPane root, double speed, double seekingRange, Color color,
                Type type){
        super(root, Constants.BLOB_SIZE);
        this.speed = speed;
        this.seekingRange = seekingRange;
        this.type = type;
        this.color = color;
    }

    @Override
    void show(){
        if (type == Type.Square) {
            r = new Rectangle();
            r.setX(x);
            r.setY(y);
            r.setWidth(Constants.BLOB_SIZE);
            r.setHeight(Constants.BLOB_SIZE);
            r.setFill(color);
            root.getChildren().add(r);
        }
        else{
            c = new Circle();
            c.setCenterX(x);
            c.setCenterY(y);
            c.setRadius(size*0.64);
            c.setFill(color);
            root.getChildren().add(c);
        }
    }

    @Override
    void delete() {
        if (type == Type.Square) {
            root.getChildren().remove(r);
        }
        else{
            root.getChildren().remove(c);
        }
    }

    public void makeStepToFood(){
        Food nearestFood = findNearestFood();
        if (nearestFood != null) {
            delete();
            double k = (double) (y - nearestFood.y) / (x - nearestFood.x);
            double angle = Math.abs(Math.atan(k));

            x = x + (int) (speed * Math.cos(angle)) * Integer.compare(nearestFood.x, x);
            y = y + (int) (speed * Math.sin(angle)) * Integer.compare(nearestFood.y, y);
            show();
            if (type == Type.Square && r.intersects(nearestFood.r.getBoundsInLocal())) {
                eatFood(nearestFood);
            }
            if (type == Type.Circle && c.intersects(nearestFood.r.getBoundsInLocal())){
                eatFood(nearestFood);
            }
        }

    }

    public void eatFood(Food food){
        collectedFood++;
        Main.foodArr.remove(food);
        food.delete();
    }

    public Food findNearestFood(){
        Food nearestFood = null;
        double minDist = Constants.FIELD_SIZE * Constants.FIELD_SIZE;
        for (Food food: Main.foodArr
             ) {
            double dist = countDistToFood(food);
            if (dist < minDist && dist <= seekingRange){
                minDist = dist;
                nearestFood = food;
            }
        }
        return nearestFood;
    }

    public double countDistToFood(Food food){
        return Math.sqrt(Math.pow(x - food.x, 2) + Math.pow(y - food.y, 2));
    }

    public void mutate(double mutProb, double speedChangeLimit,
    double rangeChangeLimit){
        Random r = new Random();
        double blue = color.getBlue();
        double red = color.getRed();
        double green = color.getGreen();
        double s = color.getSaturation();
        if (r.nextDouble() <= mutProb){
            double delta = speedChangeLimit*(r.nextDouble()*2 - 1);
            speed += delta;
            if (delta >= 0){
                blue -= delta/4;
                red += delta/4;
            }
            else{
                blue += delta/4;
                red -= delta/4;
            }
            green -= delta/4;
        }
        if (r.nextDouble() <= mutProb){
            double delta = rangeChangeLimit*(r.nextDouble()*2 - 1);
            seekingRange += delta;
            if (delta >= 0){
                blue -= delta/100;
                green += delta/100;
            }
            else{
                blue += delta/100;
                green -= delta/100;
            }
             red -= delta/100;
        }
        red = Math.min(Math.max(red, 0), 1);
        green = Math.min(Math.max(green, 0), 1);
        blue = Math.min(Math.max(blue, 0), 1);
        color = new Color(red, green, blue, s);
    }


    public ArrayList<Blob> mitosis(double mutProb, double speedChangeLim, double
                                   rangeChangeLim){
        Blob child1 = new Blob(root, speed, seekingRange, color, type);
        Blob child2 = new Blob(root, speed, seekingRange, color, type);
        child1.mutate(mutProb, speedChangeLim, rangeChangeLim);
        child2.mutate(mutProb, speedChangeLim, rangeChangeLim);
        ArrayList<Blob> arr = new ArrayList<>();
        arr.add(child1);
        arr.add(child2);
        return arr;
    }

}
