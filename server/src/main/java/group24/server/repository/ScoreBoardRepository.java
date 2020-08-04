package group24.server.repository;
/**
 * The repository corresponding to ScoreBoard class.
 */

import group24.server.models.ScoreBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public interface ScoreBoardRepository extends CrudRepository<ScoreBoard, Integer> {
   ScoreBoard findById(int id);
}