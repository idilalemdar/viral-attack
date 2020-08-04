package group24.client;

import group24.client.GameApplication.StageReadyEvent;
import group24.client.controller.*;
import group24.client.models.BooleanValueUpdate;
import group24.client.models.LongValueUpdate;
import group24.client.models.StringValueUpdate;
import group24.client.models.entities.Player;
import group24.client.models.entities.aliens.Alien;
import group24.client.models.entities.missiles.DefenseMissile;
import group24.client.models.entities.missiles.EnemyMissile;
import group24.client.network.Receiver;
import group24.client.network.Sender;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

/**
 * StageInitializer class is responsible for
 * setting event handlers in each scene.
 */

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    @Value("${spring.application.apiAddress}") private String apiAddress;
    @Value("${spring.application.ui.windowWidth}") private int windowWidth;
    @Value("${spring.application.ui.windowHeight}") private int windowHeight;
    @Value("${spring.application.playerY}") private int playerY;
    @Value("${spring.application.cellWidth}") private int cellWidth;
    @Value("${spring.application.cellHeight}") private int cellHeight;
    @Value("${spring.application.maxPlayerX}") private int maxPlayerX;
    private String loggedInUser;
    private int flag, flag1;
    private final ApplicationContext applicationContext;
    private final String applicationTitle;
    private int currentLevel;
    private Sender sender;
    private Receiver receiver;
    private LongValueUpdate HPTakenByOpponent;
    private StringValueUpdate opponentName;
    private LongValueUpdate opponentScore;
    private boolean level5PLayerDead;
    private BooleanValueUpdate opponentDead;
    private boolean gameFinished;


    StageInitializer(ApplicationContext applicationContext,
                     @Value("${spring.application.ui.title}") String applicationTitle) {
        this.applicationContext = applicationContext;
        this.applicationTitle = applicationTitle;
    }

    private void renderAll(ArrayList<Alien> alienList,
                           ArrayList<DefenseMissile> defenseMissiles,
                           ArrayList<DefenseMissile> opponentMissiles,
                           ArrayList<EnemyMissile> enemyMissiles,
                           GraphicsContext gc) {
        gc.clearRect(0, 0, windowWidth,windowHeight);
        for (Alien alien : alienList)
            alien.render(gc);
        for (DefenseMissile dm: defenseMissiles)
            dm.render(gc);
        for (DefenseMissile odm: opponentMissiles)
            odm.render(gc);
        for (EnemyMissile em: enemyMissiles)
            em.render(gc);
    }

    /**
     * Called when setting a different scene on the stage
     * @param stage: main stage
     * @param scene: scene to be switched to
     */

    @FXML
    private void updateStage(Stage stage, Scene scene){
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Called when we need to set some image to the background
     * of the scene.
     * @param load: parent of the scene
     * @param image: image to be set on the background
     */

    @FXML
    private void configureParent(Parent load, String image) {
        load.setStyle(image);
    }

    /**
     * Called at switching between different controllers
     * @param controller: controller to be switched to
     * @param image: background image to be set on the scene
     */

    @FXML
    private void updateController(Controller controller, String image) {
        FXMLLoader fxmlLoader = controller.getFxmlLoader();
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        if (image != null) {
            configureParent(controller.getParent(), image);
        }
    }

    /**
     * Maps relative action, mouse or key event to their handlers
     * @param stageReadyEvent: provides main stage of the GUI
     */

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) throws NullPointerException{

        Stage stage = stageReadyEvent.getStage();
        stage.setTitle(applicationTitle);

        String image_main = "-fx-background-image: url('/static/virus.jpg');";

        RegisterController regController = new RegisterController(apiAddress);
        LoginController logController = new LoginController(apiAddress);
        MainPageController mainPageController = new MainPageController();
        HomePageController homePageController = new HomePageController();
        ScoreBoardController scoreBoardController = new ScoreBoardController(apiAddress);
        GameOverController gameOverController = new GameOverController();

        Scene scene_home = new Scene(homePageController.getParent(), windowWidth, windowHeight);
        Scene scene_log = new Scene(logController.getParent(), windowWidth, windowHeight);
        Scene scene_reg = new Scene(regController.getParent(), windowWidth, windowHeight);
        Scene scene_main_page = new Scene(mainPageController.getParent(), windowWidth, windowHeight);
        Scene scene_score = new Scene(scoreBoardController.getParent(), windowWidth, windowHeight);
        Scene scene_game_over = new Scene(gameOverController.getParent(), windowWidth, windowHeight);

        updateController(homePageController, image_main);
        updateStage(stage, scene_home);

        homePageController.getLoginButton().setOnAction(e -> {
            updateController(logController, image_main);
            updateStage(stage, scene_log);
            logController.getSigninButton().setOnAction(e12 -> {
                loggedInUser = logController.clickLoginButton();
                if (loggedInUser != null) {
                    updateController(mainPageController, image_main);
                    updateStage(stage, scene_main_page);
                }
            });

            logController.getBackButton().setOnAction(e22 -> {
                updateController(homePageController, image_main);
                updateStage(stage, scene_home);
            });
        });

        homePageController.getRegisterButton().setOnAction(e -> {
            updateController(regController, image_main);
            updateStage(stage, scene_reg);
            regController.getSubmitButton().setOnAction(e1 -> {
                if (regController.clickSubmitButton()) {
                    updateController(homePageController, image_main);
                    updateStage(stage, scene_home);
                }
            });

            regController.getBackButton().setOnAction(e2 -> {
                updateController(homePageController, image_main);
                updateStage(stage, scene_home);
            });
        });

        mainPageController.getButton_scoreboard().setOnAction(e -> {
            updateController(scoreBoardController, image_main);
            updateStage(stage, scene_score);
            scoreBoardController.getButton_week().setOnAction(e1 -> scoreBoardController.clickScoreBoard("7"));
            scoreBoardController.getButton_month().setOnAction(e3 -> scoreBoardController.clickScoreBoard("30"));
            scoreBoardController.getButton_back().setOnAction(e2 -> {
                scoreBoardController.clearTable();
                updateController(mainPageController, image_main);
                updateStage(stage, scene_main_page);
            });
        });

        mainPageController.getButton_start().setOnAction(e -> {
            currentLevel = 1;
            this.flag = 0;
            this.flag1 = 0;
            level5PLayerDead = false;
            gameFinished = false;
            opponentDead = new BooleanValueUpdate(false);
            HPTakenByOpponent = new LongValueUpdate(0);
            opponentName = new StringValueUpdate("");
            opponentScore = new LongValueUpdate(Constants.FourthLevelScore);// base 4th level score

            GameController gameController = new GameController(apiAddress);
            ArrayList<Alien> alienList = new ArrayList<>();
            ArrayList<DefenseMissile> defenseMissiles = new ArrayList<>();
            ArrayList<DefenseMissile> opponentMissiles = new ArrayList<>();
            ArrayList<EnemyMissile> enemyMissiles = new ArrayList<>();

            Group root = new Group();
            Scene scene_game = new Scene(root ,windowWidth, windowHeight);
            stage.setScene(scene_game);
            StackPane background = new StackPane();
            background.setId("background");
            Canvas canvas = new Canvas(windowWidth, windowHeight);
            background.getChildren().add(canvas);
            root.getChildren().add(background);
            background.setStyle("-fx-background-color: #FFFF66");

            Player player = new Player();
            player.setUser(loggedInUser);
            player.resetScore();

            player.setImage("static/Female Doctor-284473.png", cellWidth, cellHeight);
            ImageView doctor = player.getImage();
            doctor.setFitWidth(cellWidth);
            doctor.setFitHeight(cellHeight);
            doctor.setY(playerY);
            doctor.setId("doctor");

            Image img = new Image("static/opponent.png", cellWidth, cellHeight, true, false);
            ImageView opponent = new ImageView(img);
            opponent.setFitWidth(cellWidth);
            opponent.setFitHeight(cellHeight);
            opponent.setY(playerY);
            opponent.setId("opponent");

            Label level = new Label(String.valueOf(currentLevel));
            level.setFont(new Font(25));
            level.setTextFill(Paint.valueOf("#970288"));
            level.setContentDisplay(ContentDisplay.CENTER);
            level.setId("level");
            root.getChildren().add(level);

            scene_game.setCursor(Cursor.NONE);
            root.getChildren().add(doctor);
            scene_game.setOnMouseMoved(e7 -> {
                Thread thread = new Thread(() -> Platform.runLater(() -> {
                    double px = e7.getX();
                    if (px > maxPlayerX) {
                        px = maxPlayerX;
                    }
                    doctor.setX(px);
                }));
                thread.start();
            });

            GraphicsContext gc = canvas.getGraphicsContext2D();
            LongValueUpdate missileTimer = new LongValueUpdate(System.nanoTime());
            LongValueUpdate playerShootTimer = new LongValueUpdate(System.nanoTime());
            LongValueUpdate moveSARSTimer = new LongValueUpdate(System.nanoTime());
            LongValueUpdate moveMERSTimer = new LongValueUpdate(System.nanoTime());
            LongValueUpdate sarsshootTimer = new LongValueUpdate(System.nanoTime());
            LongValueUpdate mersshootTimer = new LongValueUpdate(System.nanoTime());
            LongValueUpdate covidshootTimer = new LongValueUpdate(System.nanoTime());

            AnimationTimer playerShoot = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    long elapsedTime = l - playerShootTimer.value;
                    if (elapsedTime >= Constants.PlayerShootFrequency) {
                        playerShootTimer.value = l;
                        DefenseMissile missile = player.shoot();
                        missile.setImage("static/mask.png", Constants.missileWidth, Constants.missileHeight);
                        defenseMissiles.add(missile);
                    }
                }
            };

            AnimationTimer moveSARS = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    long elapsedTime = l - moveSARSTimer.value;
                    if (elapsedTime >= Constants.MoveSarsFrequency) {
                        moveSARSTimer.value = l;
                        for (Alien alien: alienList) {
                            if (alien.getName().equalsIgnoreCase("SARS")) {
                                alien.move(windowWidth);
                            }
                        }
                    }
                }
            };

            AnimationTimer moveMERS = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    long elapsedTime = l - moveMERSTimer.value;
                    if (elapsedTime >= Constants.MoveMersFrequency) {
                        moveMERSTimer.value = l;
                        for (Alien alien: alienList) {
                            if (alien.getName().equalsIgnoreCase("MERS")) {
                                alien.move(windowWidth);
                            }
                        }
                    }
                }
            };

            AnimationTimer sarsShoot = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    long elapsedTime = l - sarsshootTimer.value;
                    if (elapsedTime >= Constants.SarsShootFrequency) {
                        sarsshootTimer.value = l;
                        for (Alien alien: alienList) {
                            if (alien.getName().equalsIgnoreCase("SARS")) {
                                EnemyMissile em = alien.shoot();
                                em.setImage("static/bomb.png", Constants.missileWidth, Constants.missileHeight);
                                enemyMissiles.add(em);
                            }
                        }
                    }
                }
            };

            AnimationTimer mersShoot = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    long elapsedTime = l - mersshootTimer.value;
                    if (elapsedTime >= Constants.MersShootFrequency) {
                        mersshootTimer.value = l;
                        for (Alien alien: alienList) {
                            if (alien.getName().equalsIgnoreCase("MERS")) {
                                EnemyMissile em = alien.shoot();
                                em.setImage("static/bomb.png", Constants.missileWidth, Constants.missileHeight);
                                enemyMissiles.add(em);
                            }
                        }
                    }
                }
            };

            AnimationTimer covidShoot = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    long elapsedTime = l - covidshootTimer.value;
                    if (elapsedTime >= Constants.CovidShootFrequency) {
                        covidshootTimer.value = l;
                        for (Alien alien: alienList) {
                            if (alien.getName().equalsIgnoreCase("COVID19")) {
                                EnemyMissile em = alien.shoot();
                                em.setImage("static/bomb.png", Constants.missileWidth, Constants.missileHeight);
                                enemyMissiles.add(em);
                            }
                        }
                    }
                }
            };

            AnimationTimer moveMissiles = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    long elapsedTime = l - missileTimer.value;
                    if (elapsedTime >= Constants.MoveMissileFrequency) {  // move missiles once in 0.15 seconds
                        missileTimer.value = l;
                        for (DefenseMissile dm : defenseMissiles) {
                            dm.move();
                        }
                        for (EnemyMissile em : enemyMissiles) {
                            em.move();
                        }

                        ArrayList<Alien> deadAliens = new ArrayList<>();
                        ArrayList<DefenseMissile> usedDM = new ArrayList<>();
                        ArrayList<EnemyMissile> usedEM = new ArrayList<>();

                        //hit check for aliens
                        for (DefenseMissile dm: defenseMissiles) {
                            for (Alien alien: alienList) {
                                if (alien.getHP() > 0 && alien.intersects(dm)) {
                                    alien.decreaseHP();
                                    usedDM.add(dm);
                                    if (alien.getName().equalsIgnoreCase("COVID19")) {
                                        player.increaseHPTaken();
                                    }
                                    if (alien.getHP() == 0) {
                                        deadAliens.add(alien);
                                    }
                                    break;
                                }
                            }
                        }
                        alienList.removeAll(deadAliens);
                        defenseMissiles.removeAll(usedDM);

                        // hit check for player
                        for (EnemyMissile em: enemyMissiles) {
                            if(player.intersects(em)){
                                player.decreaseHP();
                                usedEM.add(em);
                            }
                        }
                        enemyMissiles.removeAll(usedEM);
                    }
                }
            };

            AnimationTimer renderer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if (currentLevel == 5 && sender.getClient() != null) { // multiplayer level
                        try {
                            sender.sendMessage(doctor.getX(), loggedInUser, defenseMissiles, player.getHPTaken(), level5PLayerDead, gameFinished);
                        } catch (IOException | JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                    renderAll(alienList, defenseMissiles, opponentMissiles, enemyMissiles, gc);
                }
            };

            AnimationTimer hitChecker = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if (currentLevel == 5 && (HPTakenByOpponent.value + player.getHPTaken() >= Constants.CovidHP)){
                        alienList.clear();
                    }
                    if (alienList.isEmpty()) {
                        if (currentLevel <= 4) {
                            currentLevel++;
                            level.setText(String.valueOf(currentLevel));
                            defenseMissiles.clear();
                            enemyMissiles.clear();
                            if (currentLevel == 5) {
                                Label label = new Label("Waiting for a match...");
                                label.setId("waiting");
                                launchCommunication(opponent, label, root, gc);
                                Timeline wonder = getTimeline(
                                        root, background, label, opponent, doctor, playerShoot, this, gameController, alienList
                                );
                                wonder.setCycleCount(Timeline.INDEFINITE);
                                wonder.play();
                            } else {
                                gameController.initializeLvl(currentLevel, alienList, cellWidth, cellHeight);
                            }
                        } else { // game over
                            if (currentLevel == 5) {
                                gameFinished = true;
                            }
                            tearDownGame(currentLevel);
                            this.stop();
                        }
                    }
                    else if (player.getHP() == 0) { // player dead
                        if (currentLevel == 5) { // multiplayer level
                            level5PLayerDead = true;
                            try {
                                sender.sendMessage(doctor.getX(), loggedInUser, defenseMissiles, player.getHPTaken(), level5PLayerDead, gameFinished);
                            } catch (IOException | JSONException ex) {
                                ex.printStackTrace();
                            }
                            tearDownGame(currentLevel);
                        } else { // singleplayer levels
                            tearDownGame(currentLevel - 1); // last completed level
                        }
                        this.stop();
                    } else if (opponentDead.value) { // can only be true in multiplayer level
                        gameFinished = true;
                        tearDownGame(currentLevel);
                        this.stop();
                    }
                }

                private void tearDownGame(int level) {
                    moveMissiles.stop();
                    playerShoot.stop();
                    moveSARS.stop();
                    moveMERS.stop();
                    sarsShoot.stop();
                    mersShoot.stop();
                    covidShoot.stop();
                    renderer.stop();
                    player.calculateScore(level);
                    if (level == 5) {
                        // adding level 4 score of the opponent + 5th level score of the opponent
                        opponentScore.value += HPTakenByOpponent.value;
                    }
                    gameController.sendStats(currentLevel, player, opponentScore);
                    gameOverController.setPlayerInfo(currentLevel, player, opponentName.value, opponentScore, HPTakenByOpponent.value);
                    updateController(gameOverController, null);
                    updateStage(stage, scene_game_over);
                    this.stop();
                }
            };

            scene_game.setOnKeyPressed(e8 -> {
                if (e8.getCode() == KeyCode.DIGIT9 && e8.isControlDown() && e8.isShiftDown()) {
                    if (currentLevel <= 4) { // levelUp
                        currentLevel++;
                        level.setText(String.valueOf(currentLevel));
                        alienList.clear();
                        defenseMissiles.clear();
                        enemyMissiles.clear();
                        player.resetHP();
                        if (currentLevel == 5) {
                            Label label = new Label("Waiting for a match...");
                            label.setId("waiting");
                            launchCommunication(opponent, label, root, gc);
                            Timeline wonder = getTimeline(
                                    root, background, label, opponent, doctor, playerShoot, hitChecker, gameController, alienList
                            );
                            wonder.setCycleCount(Timeline.INDEFINITE);
                            wonder.play();
                        }
                        else {
                            gameController.initializeLvl(currentLevel, alienList, cellWidth, cellHeight);
                        }
                    }
                }
            });

            gameController.initializeLvl(currentLevel, alienList, cellWidth, cellHeight);

            renderer.start();
            hitChecker.start();
            playerShoot.start();
            moveSARS.start();
            moveMERS.start();
            sarsShoot.start();
            mersShoot.start();
            covidShoot.start();
            moveMissiles.start();

            stage.show();
        });

        gameOverController.getButton_back().setOnAction(e9 -> {
            try {
                sender.join();
                receiver.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (NullPointerException ignored) {

            }
            updateController(mainPageController, image_main);
            updateStage(stage, scene_main_page);
        });
    }

    private Timeline getTimeline(Group root, StackPane background, Label label, ImageView opponent, ImageView doctor, AnimationTimer playerShoot, AnimationTimer hitChecker, GameController gameController, ArrayList<Alien> alienList) {
        hitChecker.stop();
        return new Timeline(new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sender.getClient() == null && flag1 == 0) {
                    flag1 = 1;
                    playerShoot.stop();
                    resetView(root, background);
                    label.setFont(new Font(64));
                    label.setTextFill(Paint.valueOf("#970288"));
                    label.setContentDisplay(ContentDisplay.CENTER);
                    root.getChildren().add(label);
                }

                if (flag == 0 && sender.getClient() != null) {
                    flag = 1;
                    resetView(root, background);
                    root.getChildren().add(opponent);
                    root.getChildren().add(doctor);
                    playerShoot.start();
                    hitChecker.start();
                    gameController.initializeLvl(currentLevel, alienList, cellWidth, cellHeight);
                }
            }
        }));
    }

    private void launchCommunication(ImageView opponent, Label label, Group root, GraphicsContext gc) {
        try {
            sender = new Sender(Constants.senderDestinationHost, Constants.SenderPort);
            try {
                receiver = new Receiver(Constants.ReceiverPort, opponent, label, root, gc, HPTakenByOpponent, opponentName, opponentDead);
            } catch (IOException e14) {
                sender = new Sender(Constants.senderDestinationHost, Constants.ReceiverPort);
                try {
                    receiver = new Receiver(Constants.SenderPort, opponent, label, root, gc, HPTakenByOpponent, opponentName, opponentDead);
                } catch (IOException e15) {
                    e15.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sender.start();
        receiver.start();
    }

    public void resetView(Group root, StackPane background) {
        root.getChildren().clear();
        root.getChildren().add(background);
    }
}