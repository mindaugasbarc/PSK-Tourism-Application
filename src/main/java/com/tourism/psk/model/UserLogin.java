package com.tourism.psk.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private @NonNull String username;
    private @NonNull String password;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
