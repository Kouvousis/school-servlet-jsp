package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.core.RoleType;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.security.SecUtil;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements IUserDAO {
    @Override
    public User insertUser(User user) throws UserDAOException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            // Extract model info
            String username = user.getUsername();
            String password = user.getPassword();
            RoleType role = user.getRoleType();

            ps.setString(1, username);
            ps.setString(2, SecUtil.hashPassword(password));
            ps.setString(3, role.name());

            ps.executeUpdate();
            // logging
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new UserDAOException("Insert SQL error. User: " + user + " not inserted.");
        }
    }

    @Override
    public User getByUsername(String username) throws UserDAOException {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        RoleType.valueOf(rs.getString("role")));
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new UserDAOException("SQL error in get user with username: " + username);
        }
    }

    @Override
    public boolean isUserValid(String username, String password) throws UserDAOException {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        RoleType.valueOf(rs.getString("role")));    // we assume that DB role is not null
            } else {
                return false;
            }

            return SecUtil.checkPassword(password, user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new UserDAOException("SQL error in is user valid with username: " + username);
        }
    }

    @Override
    public boolean isEmailExists(String username) throws UserDAOException {
        String sql = "SELECT count(*) FROM users WHERE username = ?";
        User user = null;
        ResultSet rs;
        int count = 0;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new UserDAOException("SQL error for user with username " + username);
        }
    }
}
