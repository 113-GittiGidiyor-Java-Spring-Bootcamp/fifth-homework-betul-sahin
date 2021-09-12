package com.betulsahin.schoolmanagementsystemv5.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private long id;

    @NotEmpty
    @Size(max=50, message = "Course name cannot be greater than 50")
    private String name;

    @NotEmpty
    @Size(max = 10, message = "Course code cannot be greater than 10")
    private String code;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int creditScore;
}
