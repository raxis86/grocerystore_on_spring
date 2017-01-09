package grocerystore.Services.Models;

import grocerystore.Domain.Entities.Role;
import grocerystore.Domain.Entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 29.12.2016.
 * Класс для хранения пользователя и роли
 */
public class AuthUser {
    private static final Logger logger = LoggerFactory.getLogger(AuthUser.class);

    private User user;
    private Role role;

    public AuthUser(User user, Role role){
        this.user=user;
        this.role=role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
