package dao;

import classes.Role;
import connectionmanager.ConnectionPool;
import connectionmanager.TomcatConnectionPool;
import exceptions.RegisterUrlNotFoundException;
import exceptions.RoleDAOException;
import interfaces.dao.RoleDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAOImpl implements RoleDAO {

    private static final Logger logger = Logger.getLogger(RoleDAOImpl.class);
    private static final ConnectionPool pool = TomcatConnectionPool.getInstance();

    private static final String GET_BY_URL = "SELECT * FROM registration_url WHERE url = ?";

    @Override
    public Role getRoleByUrl(String url) throws RoleDAOException, RegisterUrlNotFoundException {
        Role result = null;
        try (Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_BY_URL)) {
            statement.setString(1, url);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = Role.valueOf(set.getString("role"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            logger.debug(e);
            throw new RoleDAOException();
        }
        if (result == null) {
            throw new RegisterUrlNotFoundException();
        }
        return result;
    }
}