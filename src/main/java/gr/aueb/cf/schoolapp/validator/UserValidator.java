package gr.aueb.cf.schoolapp.validator;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.BaseUserDTO;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class UserValidator<T> {

    private final static IUserDAO userDAO = new UserDAOImpl();
    private final static IUserService userService = new UserServiceImpl(userDAO);

    private UserValidator() {}

    public static <T extends BaseUserDTO> Map<String, String> validate(T dto)
            throws UserDAOException {
        Map<String, String> errors = new HashMap<>();

        if (!dto.getPassword().equals(dto.getConfirmedPassword())) {
            errors.put("confirmedPassword", "Passwords do not match");
        }

        if (dto.getPassword().length() < 5 || dto.getPassword().length() > 32) {
            errors.put("confirmedPassword", "Password must be between 5 and 32 characters");
        }

        if (dto.getUsername().matches("^.*\\s+.*$")) {
            errors.put("username", "Username must not contain spaces");
        }

        if (dto.getPassword().matches("^.*\\s+.*$")) {
            errors.put("password", "Password must not contain spaces");
        }

        if (userService.isEmailExists(dto.getUsername())) {
            errors.put("username", "Username already exists");
        }

        return errors;
    }
}
