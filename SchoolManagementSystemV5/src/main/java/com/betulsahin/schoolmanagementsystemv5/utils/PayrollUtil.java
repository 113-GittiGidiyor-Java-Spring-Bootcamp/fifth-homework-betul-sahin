package com.betulsahin.schoolmanagementsystemv5.utils;

import com.betulsahin.schoolmanagementsystemv5.entities.abstracts.Instructor;
import com.betulsahin.schoolmanagementsystemv5.entities.enums.SalaryUpdateType;

public class PayrollUtil {
    public static double calculateSalary(Instructor instructor,
                                         double salaryPercentage,
                                         SalaryUpdateType salaryUpdateType){
        double currentSalary = instructor.getSalary();

        double payout = 0.0;
        if(SalaryUpdateType.UP.equals(salaryUpdateType)){
            payout = currentSalary + (currentSalary * salaryPercentage);
        }
        else{
            payout = currentSalary - (currentSalary * salaryPercentage);
        }

        return payout;
    }
}
