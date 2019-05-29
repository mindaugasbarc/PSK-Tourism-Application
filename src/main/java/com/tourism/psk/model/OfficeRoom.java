package com.tourism.psk.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class OfficeRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "office_id")
    @JsonBackReference
    private Office office;

    @OneToMany(mappedBy = "officeRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OfficeRoomOccupation> occupations;

    public OfficeRoom() {

    }

    public OfficeRoom(String name) {
        this.name = name;
    }

    public OfficeRoom(String name, Office office) {
        this.name = name;
        this.office = office;
    }

    public List<OfficeRoomOccupation> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<OfficeRoomOccupation> occupations) {
        this.occupations = occupations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
