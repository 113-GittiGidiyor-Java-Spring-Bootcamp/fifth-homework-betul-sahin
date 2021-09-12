package com.betulsahin.schoolmanagementsystemv5.mappers;

import com.betulsahin.schoolmanagementsystemv5.dtos.InstructorDto;
import com.betulsahin.schoolmanagementsystemv5.dtos.PermanentInstructorDto;
import com.betulsahin.schoolmanagementsystemv5.dtos.VisitingResearcherDto;
import com.betulsahin.schoolmanagementsystemv5.entities.Instructor;
import com.betulsahin.schoolmanagementsystemv5.entities.PermanentInstructor;
import com.betulsahin.schoolmanagementsystemv5.entities.VisitingResearcher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
    PermanentInstructor toPermanentInstructor(PermanentInstructorDto permanentInstructorDto);

    VisitingResearcher toVisitingResearcher(VisitingResearcherDto VisitingResearcherDto);

    default Instructor map(InstructorDto instructorDto){
        if(instructorDto instanceof PermanentInstructorDto){
            return toPermanentInstructor((PermanentInstructorDto)instructorDto);
        }
        else if(instructorDto instanceof VisitingResearcherDto){
            return toVisitingResearcher((VisitingResearcherDto)instructorDto);
        }
        return null;
    }

    PermanentInstructorDto toPermanentInstructorDto(PermanentInstructor permanentInstructor);

    VisitingResearcherDto toVisitingResearcherDto(VisitingResearcher visitingResearcher);

    default InstructorDto mapToDto(Instructor instructor){
        if(instructor instanceof PermanentInstructor){
            return toPermanentInstructorDto(
                    (PermanentInstructor)instructor);
        }
        else if(instructor instanceof VisitingResearcher){
            return toVisitingResearcherDto(
                    (VisitingResearcher)instructor);
        }
        return null;
    }
}
