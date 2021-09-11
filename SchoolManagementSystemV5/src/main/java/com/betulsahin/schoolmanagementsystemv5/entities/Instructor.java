package com.betulsahin.schoolmanagementsystemv5.entities;

import com.betulsahin.schoolmanagementsystemv5.entities.abstracts.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Instructor extends AbstractBaseEntity {

    private String name;
    private String address;
    private String phoneNumber;

    @JsonManagedReference
    //@JsonIgnore
    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    public Instructor(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
