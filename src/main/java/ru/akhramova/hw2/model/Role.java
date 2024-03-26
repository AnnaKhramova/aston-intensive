package ru.akhramova.hw2.model;

import java.util.Objects;
import java.util.Set;

public class Role extends Entity {
    private String name;
    private Set<User> users;

    public Role() {
    }

    public Role(Long id, String name, Set<User> users) {
        super(id);
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name) && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, users);
    }
}
