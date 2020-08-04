package group24.server.service;

import group24.server.models.User;
import group24.server.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    private User loggedInUser1 = null;
    private User loggedInUser2 = null;

    @Override
    public User addUser(User user) {
        try {
            if (this.userRepository.findByUsername(user.getUserName()) == null) {
                return this.userRepository.save(user);
            }
            return null;
        }
        catch(NullPointerException ignored){}
        return null;
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        try {
            User updatedUser = this.userRepository.findById(user.getId());

            updatedUser.setUserName(user.getUserName());
            updatedUser.setPassword(user.getPassword());
            return this.userRepository.save(updatedUser);
        }
        catch(NullPointerException ignored){}
        return null;
    }

    @Override
    public User deleteUser(int id) {
        try {
            User user = this.userRepository.findById(id);
            this.userRepository.delete(user);
            return user;
        }
        catch(NullPointerException ignored){}
        return null;
    }

    @Override
    public User login(User user){ // if two users are already logged in, clear here so that no need for restarting the server
        if (loggedInUser1 != null && loggedInUser2 != null) {
            loggedInUser1 = null;
            loggedInUser2 = null;
        }
        try {
            User emptyUser = new User();
            User user1 = this.userRepository.findByUsername(user.getUserName());
            if (user.getPassword().equals(user1.getPassword())) { // credentials correct
                if (loggedInUser1 == null) { // no user has logged in yet
                    loggedInUser1 = user1;
                } else if (!loggedInUser1.getUserName().equalsIgnoreCase(user1.getUserName())){ // second logged in user is different
                    loggedInUser2 = user1;
                } else { // attempt to logging in twice by the same user
                   return emptyUser;
                }
                return user1;
            }
            return null;
        }
        catch (NullPointerException ignored){}
        return null;
    }

    @Override
    public User updateHighScore(String username, int hs) {
        User user = userRepository.findByUsername(username);
        if (user.getHighScore() < hs){
            user.setHighScore(hs);
        }
        return userRepository.save(user);
    }

    @Override
    public String getUser(int id) {
        return userRepository.findById(id).getUserName();
    }

    @Override
    public User getUserbyName(String name) {
        return userRepository.findByUsername(name);
    }
}
