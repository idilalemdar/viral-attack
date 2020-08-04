package group24.server.controller;

import group24.server.models.User;
import group24.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * Adds given user with given parameters to the users table.
     * @param user: user that is going to added.
     * @return response with added user.
     */
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.addUser(user));
    }
    /**
     * Logs given user in with given parameters.
     * @param user: user that is going to logged.
     * @return response with logged in user.
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.login(user));
    }

    /**
     * Check whether or not the user achieved a new high score.
     * If so, update.
     * @param username: user whose high score is to be checked.
     *        hs: new candidate high score.
     * @return response including the user
     */

    @PutMapping("/highscore")
    public ResponseEntity<User> updateHighScore(@RequestParam() String username, @RequestParam() int hs) {
        return ResponseEntity.ok().body(this.userService.updateHighScore(username, hs));
    }

    /**
     * Updates given user with given parameters.
     * @param user: user that is going to updated.
     * @return response with updated user.
     */

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }

    /**
     * Gets the user given the userID
     * @return response with the username.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<String> getUser(@PathVariable int id) {
        return ResponseEntity.ok().body(this.userService.getUser(id));
    }

    /**
     * Gets the user given the username
     * @return response with the username.
     */
    @GetMapping("/get/name/{name}")
    public ResponseEntity<User> getUserbyName(@PathVariable String name) {
        return ResponseEntity.ok().body(this.userService.getUserbyName(name));
    }

    /**
     * Gets all users in the table users.
     * @return response with the user list.
     */
    @GetMapping("/getAll")
    public ResponseEntity<Iterable<User>> getUsers(){
        return ResponseEntity.ok().body(this.userService.getUsers());
    }

    /**
     * Deletes user with given id.
     * @param id: user id that is going to deleted.
     * @return response with the deleted user.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestParam(value = "id") int id){
        return ResponseEntity.ok().body(this.userService.deleteUser(id));
    }

}
