package ua.training.model.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private int id;
    private String surname;
    private String email;
    private String password;
    private ROLE role;

    public enum ROLE {
        CLIENT, ADMIN
    }

    public void setRoleFromString(String role){
        this.role = ROLE.valueOf(role.toUpperCase());
    }

    List<User> users = new ArrayList<>();
}
