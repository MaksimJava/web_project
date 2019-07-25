package ru.example.max.dao;

import org.springframework.stereotype.Component;
import ru.example.max.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class UserDao {
    private static Connection connection;

    static {
        String url = null;
        String username = null;
        String password = null;

        //load db properties
        try (InputStream in = UserDao.class.getClassLoader().
                              getResourceAsStream("persistence.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //acquire db connection
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(
                            "select * from users");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            User user = new User();
            user.setName(rs.getString(1));
            user.setSurname(rs.getString(2));
            user.setEmail(rs.getString(3));
            users.add(user);
        }
        return users;
    }

    public User getUserFromEmail(String email) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "select * from users where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setName(rs.getString(1));
                user.setSurname(rs.getString(2));
                user.setEmail(rs.getString(3));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into users values ( ?, ?, ? )");
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        ps.setString(3, user.getEmail());
        ps.execute();
    }
}
