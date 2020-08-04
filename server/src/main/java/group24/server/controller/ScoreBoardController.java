package group24.server.controller;

import group24.server.models.ScoreBoard;
import group24.server.repository.ScoreBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@RequestMapping("/scoreboard")
@RestController
public class ScoreBoardController {

    @Autowired
    private ScoreBoardRepository scoreBoardRepository;

    @PersistenceContext
    EntityManager em;

    /**
     * Gets all games in the table scoreboard.
     * @return response with the game list.
     */
    @GetMapping("/get")
    public ResponseEntity<Iterable<ScoreBoard>> getScoreBoard(){
        return ResponseEntity.ok().body(this.scoreBoardRepository.findAll());
    }

    /**
     * Gets the leader board on specified interval
     * @param day: number of days, can be 7 or 30
     * @return response with the game list.
     */
    @GetMapping("/{day}")
    public ResponseEntity<Iterable<ScoreBoard>> getIntervalDays(@PathVariable int day){
        if (day == 7 || day == 30) {
            return ResponseEntity.ok().body(em.createQuery("SELECT game FROM ScoreBoard game WHERE DATEDIFF(NOW(), game.date) <= "
                            + day + " ORDER BY game.score DESC",
                    ScoreBoard.class).setMaxResults(day).getResultList());
        }
        return ResponseEntity.noContent().build();
    }
    /**
     * Updates given game with given parameters.
     * @param game: game that is going to updated.
     * @return response with updated game.
     */
    @PutMapping("/update")
    public ResponseEntity<ScoreBoard> updateUser(@RequestBody ScoreBoard game){
        ScoreBoard updatedGame = this.scoreBoardRepository.findById(game.getId());
        updatedGame.setUser(game.getUser());
        updatedGame.setDate(game.getDate());
        updatedGame.setScore(game.getScore());
        return ResponseEntity.ok().body(this.scoreBoardRepository.save(updatedGame));
    }
    /**
     * Adds given game with given parameters to the scoreboard table.
     * @param game: game that is going to added.
     * @return response with added game.
     */
    @PostMapping("/add")
    public ResponseEntity<ScoreBoard> addGame(@RequestBody ScoreBoard game){
        return ResponseEntity.ok().body(this.scoreBoardRepository.save(game));
    }

}
