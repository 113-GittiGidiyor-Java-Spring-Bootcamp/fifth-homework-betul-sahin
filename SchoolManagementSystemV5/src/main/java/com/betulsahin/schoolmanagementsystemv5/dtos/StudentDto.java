package com.betulsahin.schoolmanagementsystemv5.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private long id;

    @ApiModelProperty(example = "Aybike Güliz Enzel Yağmur Eflinnisa Nebioğulları :) ")
    @NotEmpty
    @Size(max=50, message = "Your name cannot be greater than 50 characters")
    private String name;

    @NotEmpty
    private LocalDate birthdate;

    @NotEmpty
    @Size(max=250, message = "Your address cannot be greater than 250 characters")
    private String address;

    @NotEmpty
    private String gender;
}
