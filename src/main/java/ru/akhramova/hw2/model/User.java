package ru.akhramova.hw2.model;

import java.util.Objects;
import java.util.Set;

public class User extends Entity {
    private String name;
    private City city;
    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String name, City city, Set<Role> roles) {
        super(id);
        this.name = name;
        this.city = city;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(city, user.city) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, city, roles);
    }
}
