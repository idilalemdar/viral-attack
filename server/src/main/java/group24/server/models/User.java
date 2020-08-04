package group24.server.models;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;                 /** Auto-generated id of the user*/
    @Column(unique = true, nullable = false)
    private String username;        /** username of the user*/
    @Column(nullable = false)
    private String password;        /** password of the user*/
    @Column
    private int highScore;          /** highest score that the user got*/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ScoreBoard> games;  /** game list of the user*/

    public User() {
        username = null;
        password = null;
        highScore = 0;
    }
    public User(String username, String password, int highScore) {
        this.username = username;
        this.password = password;
        this.highScore = highScore;
    }

    /** getters and setters*/
    public int getId(){ return id; }
    public String getUserName() {
        return username;
    }
    public String getPassword() { return password; }
    public int getHighScore() {
        return highScore;
    }
    public void setUserName(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setHighScore(int highScore){ this.highScore = highScore;}
    public boolean isEmpty() {return (username == null & password == null && highScore == 0);}

}
