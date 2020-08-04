package group24.server.repository;
/**
 * The repository corresponding to User class.
 */
import group24.server.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
    User findById(int id);

}
