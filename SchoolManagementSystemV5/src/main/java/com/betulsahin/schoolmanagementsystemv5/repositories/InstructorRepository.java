package com.betulsahin.schoolmanagementsystemv5.repositories;

import com.betulsahin.schoolmanagementsystemv5.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
