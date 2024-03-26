package ru.akhramova.hw2.repository;

import ru.akhramova.hw2.model.City;
import ru.akhramova.hw2.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CityRepository {
    private final String GET_ALL = "SELECT c.id, c.name FROM cities c ";
    private final String GET_BY_ID = "SELECT c.name FROM cities c WHERE id=";
    private final String GET_BY_NAME = "SELECT c.id FROM cities c WHERE name=";
    private final String GET_USERS_BY_ID = "SELECT u.id, u.name FROM users u LEFT JOIN cities c ON u.city_id = c.id WHERE c.id=";
    private final String SAVE_CITY = "INSERT INTO cities (name) VALUES (?)";
    private final String UPDATE_USER = "UPDATE users SET city_id=? WHERE id=?";
    private final String DELETE_CITY_FROM_USERS = "UPDATE users SET city_id=NULL WHERE city_id=?";
    private final String DELETE_CITY = "DELETE FROM cities WHERE id=?";

    private final PostgresJDBC postgresJDBC = new PostgresJDBC();

    public List<City> all() {
        List<City> cities = new ArrayList<>();
        try(PreparedStatement statement = postgresJDBC.getConnection().prepareStatement(GET_ALL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                City city = new City();
                city.setId(rs.getLong("id"));
                city.setName(rs.getString("name"));
                setUsers(city);
                cities.add(city);
            }
            rs.close();
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<City> getById(long id) {
        City city = null;
        try(PreparedStatement statement = postgresJDBC.getConnection().prepareStatement(GET_BY_ID + id)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                city = new City();
                city.setId(id);
                city.setName(name);
                if (city != null) {
                    setUsers(city);
                }
            }
            rs.close();
            return Optional.ofNullable(city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public City save(City city) {
        try(PreparedStatement statement = postgresJDBC.getConnection().prepareStatement(SAVE_CITY)) {
            statement.setString(1,city.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Optional<City> cityFromDb = getByName(city.getName());
        if (cityFromDb.isPresent()) {
            Set<User> users = city.getUsers();
            for (var user : users) {
                updateUser(cityFromDb.get(), user);
            }
        }
        return city;
    }

    public void removeById(Long id) {
        removeAtUsersById(id);
        try(PreparedStatement statement = postgresJDBC.getConnection().prepareStatement(DELETE_CITY)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAtUsersById(Long id) {
        try(PreparedStatement statement = postgresJDBC.getConnection().prepareStatement(DELETE_CITY_FROM_USERS)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUsers(City city) {
        Set<User> users = new HashSet<>();
        city.setUsers(users);
        try(PreparedStatement usersStatement = postgresJDBC.getConnection().prepareStatement(GET_USERS_BY_ID + city.getId())) {
            ResultSet usersRs = usersStatement.executeQuery();
            while (usersRs.next()) {
                User user = new User();
                user.setId(usersRs.getLong("id"));
                user.setName(usersRs.getString("name"));
                user.setCity(city);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<City> getByName(String name) {
        City city = null;
        try(PreparedStatement statement = postgresJDBC.getConnection().prepareStatement(GET_BY_NAME + name)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                city = new City();
                city.setId(id);
                city.setName(name);
                if (city != null) {
                    setUsers(city);
                }
            }
            rs.close();
            return Optional.ofNullable(city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateUser(City city, User user) {
        try (PreparedStatement statement = postgresJDBC.getConnection().prepareStatement(UPDATE_USER)) {
            statement.setLong(1, city.getId());
            statement.setLong(2, user.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
