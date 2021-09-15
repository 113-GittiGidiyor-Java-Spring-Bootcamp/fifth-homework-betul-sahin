package com.betulsahin.schoolmanagementsystemv5.models;

import com.betulsahin.schoolmanagementsystemv5.models.enums.SalaryUpdateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InstructorServiceTransactionLogger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long instructorId;
    private double salaryBeforeUpdate;
    private double salaryAfterUpdate;
    private double salaryPercentage;
    private LocalDate requestDateTime;
    private String clientIpAdress;
    private String clientUrl;
    private String sessionActivityId;
    @Enumerated(EnumType.STRING)
    private SalaryUpdateType salaryUpdateType;
}
