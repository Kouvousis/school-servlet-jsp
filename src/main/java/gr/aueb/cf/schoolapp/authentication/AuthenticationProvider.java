package gr.aueb.cf.schoolapp.authentication;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserLogInDTO;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;

public class AuthenticationProvider {
    private final static IUserDAO userDAO = new UserDAOImpl();
    private final static IUserService userService = new UserServiceImpl(userDAO);

    private AuthenticationProvider() {}

    public static boolean authenticate(UserLogInDTO userLogInDTO) throws UserDAOException {
        return userService.isUserValid(userLogInDTO.getUsername(), userLogInDTO.getPassword());
    }
}
