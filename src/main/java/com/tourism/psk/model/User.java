package com.tourism.psk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tourism.psk.constants.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private @NonNull String fullname;
    private @NonNull String email;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Trip> trips;
    private @NonNull UserRole role;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private UserLogin userLogin;
}
