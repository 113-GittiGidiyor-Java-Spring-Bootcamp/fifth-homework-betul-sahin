package com.betulsahin.schoolmanagementsystemv5.dtos;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermanentInstructorDto extends InstructorDto {

    @NotEmpty
    @ApiModelProperty(example = "12500.0")
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double fixedSalary;
}
