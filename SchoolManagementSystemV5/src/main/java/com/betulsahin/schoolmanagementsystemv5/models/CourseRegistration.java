package com.betulsahin.schoolmanagementsystemv5.models;

import com.betulsahin.schoolmanagementsystemv5.models.abstracts.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class CourseRegistration extends AbstractBaseEntity {
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    private LocalDate registeredDate;
}
