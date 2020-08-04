package group24.server.models;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "scoreboard")
public class ScoreBoard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private int id;         /** Auto-generated id of the game played*/
    @Column(name = "uid", insertable = false, updatable = false)
    private int uid;        /** user id referencing users table*/
    @Column
    private Date date;      /** the date game played*/
    @Column
    private int score;      /** score got from the game session*/
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "uid")
    private User user;      /** referenced user*/

    public ScoreBoard() {
    }
    public ScoreBoard(User user,Date date, int score) {

        this.user = user;
        this.date = date;
        this.score = score;

    }

    /** getters and setters*/
    public int getId(){ return id; }
    public int getUserID() {
        return uid;
    }
    public Date getDate() { return date; }
    public int getScore() {
        return score;
    }
    public User getUser() { return user; }
    public void setScore(int score) { this.score = score; }
    public void setDate(Date date){ this.date = date; }
    public void setUser(User user){ this.user = user; }


}
