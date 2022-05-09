package com.example.jobsrest.dto;


import com.example.jobsrest.entity.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDto {


    private int id;
    @NotEmpty(message = "name should not be empty")
    private String name;
    private int categoryId;
    private String description;
    private double salary;
    private JobType jobType;
    private int companyId;


}
