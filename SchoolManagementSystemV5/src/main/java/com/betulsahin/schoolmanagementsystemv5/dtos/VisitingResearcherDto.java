package com.betulsahin.schoolmanagementsystemv5.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitingResearcherDto extends InstructorDto {

    @NotEmpty
    @ApiModelProperty(example = "80")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double hourlySalary;
}
