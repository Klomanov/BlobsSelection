package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SimulationLoop extends AnimationTimer {

    long lastL = 0;

    public boolean blobsCanSeekFood(){
        for (Blob blob: Main.blobs
             ) {
            if (blob.findNearestFood() != null){
                return true;
            }
        }
        return false;
    }

    @Override
    public void handle(long l) {
        if (Main.foodArr.size() != 0 && blobsCanSeekFood()) {
            for (Blob blob : Main.blobs
            ) {
                if (Main.foodArr.size() != 0) {
                    blob.makeStepToFood();
                }
            }
        } else {
            if (lastL == 0){lastL = l;}
            if (l - lastL > 0.6 * Math.pow(10, 9)) {
                lastL = 0;
                int square = 0;
                int circle = 0;
                Main.clearField();
                ArrayList<Blob> childBlobs = new ArrayList<>();
                for (Blob blob : Main.blobs
                ) {
                    if (blob.collectedFood != 0) {
                        if (blob.type == Type.Square) {
                            childBlobs.addAll(blob.mitosis(Constants.SQUARE_MUTATION_PROBABILITY,
                                    Constants.SQUARE_SPEED_CHANGE_LIMIT,
                                    Constants.SQUARE_SEEKING_RANGE_CHANGE_LIMIT));
                            square += 2;
                        }
                        if (blob.type == Type.Circle) {
                            childBlobs.addAll(blob.mitosis(Constants.CIRCLE_MUTATION_PROBABILITY,
                                    Constants.CIRCLE_SPEED_CHANGE_LIMIT,
                                    Constants.CIRCLE_SEEKING_RANGE_CHANGE_LIMIT));
                            circle += 2;
                        }
                    }
                }
                Main.blobs = childBlobs;
                Main.foodArr = Main.createFood(Constants.FOOD_START_NUM);
                Main.showEntities();
                Main.l1.setText(Integer.toString(square));
                Main.l2.setText(Integer.toString(circle));
                Main.generation++;
                Main.l3.setText(Integer.toString(Main.generation));
                Main.printInfo();
            }
        }
    }
}
