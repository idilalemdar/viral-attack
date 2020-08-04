package group24.client.network;

import group24.client.models.entities.missiles.DefenseMissile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Communication element that sends messages to the opponent.
 * Only for level 5.
 */
public class Sender extends Thread {
    private final String destination_IP;
    private final int destination_port;
    private Socket client;
    private boolean gameFinished;

    public Sender(String destinationIP, int destinationPort) {
        this.destination_IP = destinationIP;
        this.destination_port = destinationPort;
    }

    @Override
    public void run() {
        client = null;
        while (client == null) {
            try {
                client = new Socket(destination_IP, destination_port);
            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

     /**
      * Message sending method for sender.
      * @param posX position of the player
      * @param username username of the current player
      * @param opponentMissiles missiles belonging to opponent
      * @param hpTaken hit points taken from the final boss
      * @param playerDead boolean value indicating whether the player is dead
      * @param gameFinished boolean value indicating that the game is finished
      */
    public void sendMessage(double posX,
                            String username,
                            ArrayList<DefenseMissile> opponentMissiles,
                            long hpTaken,
                            boolean playerDead,
                            boolean gameFinished) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("X", posX);
        JSONArray positions = new JSONArray();
        for (DefenseMissile missile: opponentMissiles) {
            JSONObject coordinates = new JSONObject()
                    .put("X", missile.getPosX())
                    .put("Y", missile.getPosY());
            positions.put(coordinates);
        }
        jsonObject.put("missiles", positions);
        jsonObject.put("hpTaken", hpTaken);
        jsonObject.put("opponentDead", playerDead);
        this.gameFinished = gameFinished;
        jsonObject.put("gameFinished", gameFinished);

        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF(jsonObject.toString());
        if (gameFinished) {
            outToServer.close();
            out.close();
            client.close();
            client = null;
        }
    }
    public Socket getClient(){
        return client;
    }

}
