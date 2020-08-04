package group24.client.models;

/**
 * ScoreBoard class for populating the leaderboard table
 * after get scoreboard entries types of queries.
 */

public class ScoreBoard {
    private final String Date;
    private final int Score;
    private String Username;
    private final int HighScore;

    public ScoreBoard(String date, int score, String user, int hs) {
        this.Username = user;
        this.Date = date;
        this.Score = score;
        this.HighScore = hs;
    }

    public String getUser() { return Username; }
    public int getHighScore() {return HighScore; }
    public void setUsername(String uname) {Username = uname;}
    public String getDate(){
        return Date;
    }
    public int getScore(){
        return Score;
    }
}