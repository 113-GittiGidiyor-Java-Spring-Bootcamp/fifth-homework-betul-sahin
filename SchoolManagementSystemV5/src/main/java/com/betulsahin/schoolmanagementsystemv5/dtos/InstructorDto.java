package com.betulsahin.schoolmanagementsystemv5.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PermanentInstructorDto.class, name = "PermanentInstructorDto"),
        @JsonSubTypes.Type(value = VisitingResearcherDto.class, name = "VisitingResearcherDto")
})
public class InstructorDto {
    private long id;

    //@ApiModelProperty(example = "Aybike Güliz Enzel Yağmur Eflinnisa Nebioğulları")
    @NotEmpty
    @Size(max = 50, message = "Your name cannot be greater than 50 characters")
    private String name;

    @NotEmpty
    @Size(max = 250, message = "Your address cannot be greater than 250 characters")
    private String address;

    @NotEmpty
    //@ApiModelProperty(example = "05551234466")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private String phoneNumber;
}
