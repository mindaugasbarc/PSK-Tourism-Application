package com.tourism.psk.model;

import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

@Entity
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    @ColumnTransformer(read = "pgp_sym_decrypt(password, 'geckoKey')", write = "pgp_sym_encrypt(?, 'geckoKey')")
    private String password;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserLogin() {
    }

    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
