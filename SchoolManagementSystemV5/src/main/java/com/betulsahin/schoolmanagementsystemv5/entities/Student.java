package com.betulsahin.schoolmanagementsystemv5.entities;

import com.betulsahin.schoolmanagementsystemv5.entities.abstracts.AbstractBaseEntity;
import com.betulsahin.schoolmanagementsystemv5.entities.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Student extends AbstractBaseEntity {
    private String name;
    private LocalDate birthdate;
    private String address;
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @JsonManagedReference
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<CourseRegistration> registrations = new HashSet<>();

    public Student(String name, LocalDate birthdate, String address, GenderType gender) {
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.gender = gender;
    }
}

/*

GetAllList..() requestinde sonsuz dongu hatasına iliskin bu anotasyonlar kullanılıyor.

@JsonManagedReference -> liste kismina (Set<CourseRegistration> registrations gibi)
@JsonBackReference -> tekil kisma (Student student)

 */