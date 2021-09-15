package com.betulsahin.schoolmanagementsystemv5.models;

import com.betulsahin.schoolmanagementsystemv5.models.abstracts.AbstractBaseEntity;
import com.betulsahin.schoolmanagementsystemv5.models.abstracts.Instructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Course extends AbstractBaseEntity {
    private String name;
    private String code;
    private int creditScore;

    //@JsonBackReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;

    @JsonManagedReference
    // @JsonIgnore
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<CourseRegistration> registrations = new HashSet<>();

    public Course(String name, String code, int creditScore) {
        this.name = name;
        this.code = code;
        this.creditScore = creditScore;
    }
}
