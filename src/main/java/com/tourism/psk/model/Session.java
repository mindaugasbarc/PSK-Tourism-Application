package com.tourism.psk.model;

import com.tourism.psk.constants.SessionStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(indexes = {@Index(name = "access_token", columnList = "token", unique = true)})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;
    private @NonNull String token;
    private @NonNull SessionStatus status;
    private @NonNull Date created;
    private @NonNull Date expires;
}
