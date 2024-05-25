package nl.fontys.sem6.auth.config;

import java.io.Serializable;

public class UserDetailsRequest implements Serializable {
    private String username;

    // Getters and Setters

    public UserDetailsRequest() {}

    public UserDetailsRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
