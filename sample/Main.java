package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static int generation = 0;

    private static AnchorPane root;

    static public ArrayList<Food> foodArr = new ArrayList<>();
    static public ArrayList<Blob> blobs = new ArrayList<>();

    static public Label l1, l2, l3;

    public static ArrayList<Food> createFood(int amount){
        ArrayList<Food> f = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Food food = new Food(root);
            f.add(food);
        }
        return f;
    }

    public static ArrayList<Blob> createBlobs(int amount, int speed,
                                              int seekingRange, Color color,
                                              Type type){
        ArrayList<Blob> b = new ArrayList<>();
        for (int i = 0; i < amount; i++){
            Blob blob = new Blob(root, speed, seekingRange, color, type);
            b.add(blob);
        }
        return b;
    }


    public static void showEntities(){
        for (Blob blob: blobs
             ) {
            blob.show();
        }
        for (Food food: foodArr){
            food.show();
        }
    }

    public static void clearField(){
        for (Blob blob: blobs
             ) {
            blob.delete();
        }
        for (Food food: foodArr
            ){
            food.delete();
        }
    }

    public static void printInfo(){
        double sum_speed = 0;
        double sum_range = 0;

        double min_speed = Double.MAX_VALUE;
        double max_speed = -1;

        double min_range = Double.MAX_VALUE;
        double max_range = -1;

        for (Blob blob: blobs
             ) {
            sum_speed += blob.speed;
            sum_range += blob.seekingRange;

            if (blob.speed > max_speed) max_speed = blob.speed;
            if (blob.speed < min_speed) min_speed = blob.speed;
            if (blob.seekingRange > max_range) max_range = blob.seekingRange;
            if (blob.seekingRange < min_range) min_range = blob.seekingRange;
        }
        System.out.println("---------------\n" +
                            "Blobs max speed: " + max_speed + " Blobs max range: " + max_range +"\n" +
                            "Blobs min speed: " + min_speed + " Blobs min range: " + min_range +"\n" +
                            "Blobs average speed: " + sum_speed/blobs.size() + " Blobs average range: " + sum_range/blobs.size());
    }

    @Override
    public void start(Stage primaryStage){
        SimulationLoop loop = new SimulationLoop();

        Button run = new Button("Run");
        run.setOnAction(actionEvent -> loop.start());
        AnchorPane.setLeftAnchor(run, 30.0);
        Button pause = new Button("Pause");
        pause.setOnAction(actionEvent -> loop.stop());
        AnchorPane.setTopAnchor(pause, 30.0);
        AnchorPane.setLeftAnchor(pause, 30.0);
        Button repeat = new Button("Repeat");
        repeat.setOnAction(actionEvent -> {
            loop.stop();
            clearField();
            blobs = new ArrayList<>();
            blobs.addAll(createBlobs(Constants.SQUARE_BLOB_START_NUM
                    , Constants.SQUARE_BlOB_SPEED,
                    Constants.SQUARE_BLOB_SEEKING_RANGE,
                    Constants.START_COLOR, Type.Square));
            blobs.addAll(createBlobs(Constants.CIRCLE_BLOB_START_NUM
                    ,Constants.CIRCLE_BLOB_SPEED,
                    Constants.CIRCLE_BLOB_SEEKING_RANGE, Constants.START_COLOR,
                    Type.Circle));

            foodArr = createFood(Constants.FOOD_START_NUM);
            showEntities();
            generation = 0;
            l1.setText(Integer.toString(Constants.SQUARE_BLOB_START_NUM));
            l2.setText(Integer.toString(Constants.CIRCLE_BLOB_START_NUM));
            l3.setText(Integer.toString(generation));
        });
        AnchorPane.setTopAnchor(repeat, 60.0);
        AnchorPane.setLeftAnchor(repeat, 30.0);

        Rectangle r = new Rectangle();
        r.setX(105);
        r.setY(0);
        r.setWidth(1);
        r.setHeight(Constants.WINDOW_HEIGHT);

        l1 = new Label(Integer.toString(Constants.SQUARE_BLOB_START_NUM));
        l1.setFont(new Font(32));
        AnchorPane.setTopAnchor(l1, 110.0);
        AnchorPane.setLeftAnchor(l1, 65.0);

        Rectangle r1 = new Rectangle();
        r1.setX(16);
        r1.setY(123);
        r1.setHeight(25);
        r1.setWidth(25);
        r1.setFill(Color.BLACK);

        l2 = new Label(Integer.toString(Constants.CIRCLE_BLOB_START_NUM));
        l2.setFont(new Font(32));
        AnchorPane.setTopAnchor(l2, 155.0);
        AnchorPane.setLeftAnchor(l2, 65.0);

        Circle c1 = new Circle();
        c1.setCenterX(30);
        c1.setCenterY(180);
        c1.setRadius(15);
        c1.setFill(Color.BLACK);

        l3 = new Label(Integer.toString(generation));
        l3.setFont(new Font(25));
        AnchorPane.setBottomAnchor(l3, 45.0);
        AnchorPane.setLeftAnchor(l3, 15.0);

        root = new AnchorPane(run, pause, repeat, r, l1, l2, l3, r1, c1);

        blobs.addAll(createBlobs(Constants.SQUARE_BLOB_START_NUM
                , Constants.SQUARE_BlOB_SPEED,
                Constants.SQUARE_BLOB_SEEKING_RANGE,
                Constants.START_COLOR, Type.Square));
        blobs.addAll(createBlobs(Constants.CIRCLE_BLOB_START_NUM
                ,Constants.CIRCLE_BLOB_SPEED,
                Constants.CIRCLE_BLOB_SEEKING_RANGE, Constants.START_COLOR,
                Type.Circle));

        foodArr = createFood(Constants.FOOD_START_NUM);
        showEntities();

        Scene scene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        primaryStage.setTitle("Natural Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
