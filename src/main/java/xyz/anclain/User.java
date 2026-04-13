package xyz.anclain;

import lombok.Data;

@Data
public class User {

    private String name;
    private String password;
    private Integer age;

    public User(String name, String password, Integer age) {}

}