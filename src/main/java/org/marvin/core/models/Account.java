package org.marvin.core.models;



import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Account  {

    private long id;
    private String username;
    private String password;
    private List<Todo> todos;
    private List<Role> roles;
    private boolean isActive;

    public Account(long id , String username, String password,boolean isActive, List<Todo> plans, List<Role> roles) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.todos = plans;
        this.roles = roles;

    }

    public Account(){}

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", nickname='" + username + '\n' +
                ", password='" + password + '\n' +
                ", plans=" + todos + '\n'+
                ", Role = " + roles + '\n'+
                '}';
    }
}
