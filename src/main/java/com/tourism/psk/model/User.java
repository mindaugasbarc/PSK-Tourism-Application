package com.tourism.psk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourism.psk.constants.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private @NonNull String fullname;
    private @NonNull String email;
    @OneToMany
    @JoinColumn
    private List<Trip> trips;
    private @NonNull UserRole role;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserLogin userLogin;
}
