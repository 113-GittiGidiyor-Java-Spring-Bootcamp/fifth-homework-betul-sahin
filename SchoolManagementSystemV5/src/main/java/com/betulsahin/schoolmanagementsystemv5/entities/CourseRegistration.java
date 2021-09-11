package com.betulsahin.schoolmanagementsystemv5.entities;

import com.betulsahin.schoolmanagementsystemv5.entities.Course;
import com.betulsahin.schoolmanagementsystemv5.entities.Student;
import com.betulsahin.schoolmanagementsystemv5.entities.abstracts.AbstractBaseEntity;
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
