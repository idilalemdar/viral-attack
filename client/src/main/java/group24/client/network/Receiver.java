package group24.client.network;

import group24.client.Constants;
import group24.client.models.BooleanValueUpdate;
import group24.client.models.LongValueUpdate;
import group24.client.models.StringValueUpdate;
import group24.client.models.entities.missiles.DefenseMissile;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Communication element that receives from the opponent.
 * Only for level 5.
 */
public class Receiver extends Thread {

    private final GraphicsContext gc;
    private final ServerSocket receiverSocket;
    private final LongValueUpdate hpTaken;
    private final BooleanValueUpdate opponentDead;
    private double posX;
    private Label label;
    private final ImageView opponent;
    private final Group root;
    private final ArrayList<DefenseMissile> opponentMissiles;
    private final ReentrantLock lock;
    private final StringValueUpdate opponentName;
    private boolean connectionEnded;

    public Receiver(int port,
                    ImageView opponent,
                    Label label,
                    Group root,
                    GraphicsContext gc,
                    LongValueUpdate hpTakenbyOpponent,
                    StringValueUpdate opponentName,
                    BooleanValueUpdate opponentDead) throws IOException {
        receiverSocket = new ServerSocket();
        receiverSocket.bind(new InetSocketAddress(Constants.ReceiverHost, port));
        receiverSocket.setSoTimeout(1000000);
        this.opponent = opponent;
        this.label = label;
        this.root = root;
        opponentMissiles = new ArrayList<>();
        this.gc = gc;
        this.lock = new ReentrantLock();
        this.hpTaken = hpTakenbyOpponent;
        this.opponentName = opponentName;
        this.opponentDead = opponentDead;
        connectionEnded = false;
    }

    @Override
    public void run() {
        Socket receiver;
        try {
            DataInputStream in;
            while ((receiver = receiverSocket.accept()) != null) {
                while (true) {
                    InputStream inputStream = receiver.getInputStream();
                    in = new DataInputStream(inputStream);
                    while (!connectionEnded) {
                        if(in.available() > 0) {
                            String received = in.readUTF();
                            JSONObject json = new JSONObject(received);
                            opponentName.value = json.getString("username");
                            posX = Double.parseDouble(json.getString("X"));
                            hpTaken.value = json.getLong("hpTaken");
                            opponentDead.value = json.getBoolean("opponentDead");
                            connectionEnded = json.getBoolean("gameFinished");
                            JSONArray coordinates = json.getJSONArray("missiles");
                            lock.lock();
                            try {
                                opponentMissiles.clear();
                                for (int i = 0; i < coordinates.length(); ++i) {
                                    JSONObject coors = ((JSONObject) coordinates.get(i));
                                    DefenseMissile dm = new DefenseMissile(coors.getDouble("X"), coors.getDouble("Y"));
                                    dm.setImage("static/sanitizer.png", Constants.missileWidth, Constants.missileHeight);
                                    opponentMissiles.add(dm);
                                }
                            } finally {
                                lock.unlock();
                            }

                            Platform.runLater(() -> {
                                opponent.setX(posX);
                                label = new Label("Matched with: " + opponentName.value);
                                label.setId("matched");
                                label.setFont(new Font(48));
                                label.setTextFill(Paint.valueOf("#970288"));
                                root.getChildren().add(label);

                                AnimationTimer renderOpponentMissiles = new AnimationTimer() {
                                    @Override
                                    public void handle(long l) {
                                        lock.lock();
                                        try {
                                            for (DefenseMissile dm : opponentMissiles) {
                                                dm.render(gc);
                                            }
                                        } finally {
                                            lock.unlock();
                                        }
                                    }
                                };
                                renderOpponentMissiles.start();
                            });
                        }
                    }
                    in.close();
                    inputStream.close();
                    receiver.close();
                    receiverSocket.close();
                    break;
                }
                break;
            }

        } catch (IOException | JSONException e1) {
            e1.printStackTrace();
        }
    }
    public String getUsername(){
        return opponentName.value;
    }
}