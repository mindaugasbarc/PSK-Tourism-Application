package com.tourism.psk.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private @NonNull Date start;
    @Temporal(TemporalType.DATE)
    private @NonNull Date end;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
