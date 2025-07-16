package com.example.game.Arcanoid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArcanoidGame extends Application {
    public         int count = 10;
    public boolean rightMove = false;
    public boolean leftMove = false;
    public double speedBallY = -2;
    public double speedBallX = -2;
    public   ImageView fon;
    public    ImageView platform;
    public ImageView ball;
    public int level = 1;
    public Image monster;
    public     int startY = 10;
    public    double startX = 10;
    @Override
    public void start(Stage stage) throws IOException {

        int nowLevel;

        List<ImageView> enemies = new ArrayList<>();
        int speed = 5;

        Pane root = new Pane();
        Scene scene = new Scene(root);
        root.setPrefSize(600, 500);
        fon = new ImageView(new Image("Fon.jpg"));
        platform = new ImageView(new Image("Platform.png"));
        ball = new ImageView(new Image("Ball.png"));
       if(level != 2) {
           monster = new Image("image.png");
           fon.setFitWidth(600);
           fon.setFitHeight(500);
       }
        root.getChildren().addAll(fon,platform, ball);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < count; i++) {
                ImageView enemy = new ImageView(monster);
                enemy.setFitWidth(50);
                enemy.setFitHeight(50);
                enemy.setLayoutX(startX);
                enemy.setLayoutY(startY);
                enemies.add(enemy);
                root.getChildren().add(enemy);
                startX += 55;
            }
            startY += 55;
            startX = (j + 1) * 27.5 + 10 ;
            count--;
        }
        ball.setLayoutX(300);
        ball.setLayoutY(200);
        platform.setLayoutX(250);
        platform.setLayoutY(370);

        stage.setScene(scene);
        stage.setTitle("Arkanoid");
        stage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                rightMove = true;
            } else if (event.getCode() == KeyCode.LEFT) {
                leftMove = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                rightMove = false;
            } else if (event.getCode() == KeyCode.LEFT) {
                leftMove = false;
            }
        });

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (enemies.isEmpty() && level == 2){

                }
                if(enemies.isEmpty() ){
                    level = 2;
                    System.out.println("Win!");
                }
                if(level == 2){
                    monster = new Image("L.png");
                    double x = 10;
                    double y = 10;
                    for (int j = 0; j < 3; j++) {

                        for (int i = 0; i < count; i++) {
                            ImageView enemy = new ImageView(monster);
                            enemy.setFitWidth(50);
                            enemy.setFitHeight(50);
                            enemy.setLayoutX(x);
                            enemy.setLayoutY(y);
                            enemies.add(enemy);
                            root.getChildren().add(enemy);
                            x += 55;
                        }
                        y += 55;
                        x = (j + 1) * 27.5 + 10 ;
                        count--;
                    }                }
                if(platform.getLayoutY() + 30 <= ball.getBoundsInParent().getCenterY()){
                    stop();
                    System.out.println("Game Over");
                }
                if (rightMove && platform.getLayoutX() <= root.getWidth() - platform.getBoundsInParent().getWidth()) {
                    platform.setLayoutX(platform.getLayoutX() + speed);
                }
                if (leftMove && platform.getLayoutX() >= 0) {
                    platform.setLayoutX(platform.getLayoutX() - speed);
                }

                ball.setLayoutX(ball.getLayoutX() + speedBallX);
                ball.setLayoutY(ball.getLayoutY() + speedBallY);

                if (ball.getLayoutX() <= 0 || ball.getLayoutX() >= root.getWidth()) {
                    speedBallX *= -1;
                }
                if (ball.getLayoutY() <= 0) {
                    speedBallY *= -1;
                }

                if (ball.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    speedBallY *= -1;
                }
                for (int i = 0; i < enemies.size(); i++) {
                    if(enemies.get(i).getBoundsInParent().intersects(ball.getBoundsInParent())){
                        speedBallY *= -1;
                        enemies.get(i).setVisible(false);
                        enemies.remove(i);
                    }
                }
            }

        };

            animationTimer.start();
    }
}
