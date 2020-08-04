package group24.server.service;
/**
 * Service interface for User class
 */
import group24.server.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    /**
     * Adds given user with given parameters to the users table.
     * @param user: user that is going to added.
     * @return the added user.
     */
    User addUser(User user);

    /**
     * Gets all users in the table users.
     * @return the user list.
     */
    Iterable<User> getUsers();

    /**
     * Updates given user with given parameters.
     * @param user: user that is going to updated.
     * @return the updated user.
     */
    User updateUser(User user);

    /**
     * Deletes user with given id.
     * @param id: user id that is going to deleted.
     * @return the deleted user.
     */
    User deleteUser(int id);

    /**
     * Logs given user with given parameters.
     * @param user: user that is going to logged.
     * @return the logged user.
     */
    User login(User user);
    User updateHighScore(String username, int hs);
    String getUser(int id);
    User getUserbyName(String name);
}