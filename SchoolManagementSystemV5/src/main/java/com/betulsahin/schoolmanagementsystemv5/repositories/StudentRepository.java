package com.betulsahin.schoolmanagementsystemv5.repositories;

import com.betulsahin.schoolmanagementsystemv5.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
