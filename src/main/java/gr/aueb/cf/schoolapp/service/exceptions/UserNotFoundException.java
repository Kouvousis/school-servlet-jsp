package gr.aueb.cf.schoolapp.service.exceptions;

import gr.aueb.cf.schoolapp.model.User;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {}

    public UserNotFoundException(User user) {
        super("User with username" + user.getUsername() + " not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
