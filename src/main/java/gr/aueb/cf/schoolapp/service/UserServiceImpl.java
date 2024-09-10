package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.InsertUserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.UserNotFoundException;

public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO;

    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User insertUser(InsertUserDTO dto) throws UserDAOException {
        User user;

        try {
            user = mapToUser(dto);
            return userDAO.insertUser(user);
        } catch (UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserDAOException, UserNotFoundException {
        User user;

        try {
            user = userDAO.getByUsername(username);

            if (user == null) {
                throw new UserNotFoundException("User with username: " + username + " not found");
            }

            return userDAO.insertUser(user);
        } catch (UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean isUserValid(String username, String password) throws UserDAOException {
        try {
            return userDAO.isUserValid(username, password);
        } catch (UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean isEmailExists(String username) throws UserDAOException {
        try {
            return userDAO.isEmailExists(username);
        } catch (UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private User mapToUser(InsertUserDTO dto)  {
        return new User(null, dto.getUsername(), dto.getPassword());
    }
}
