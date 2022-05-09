package com.example.jobsrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "job")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String mobileNumber;
    private String description;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private double salary;

    @ManyToOne
    private Company company;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

}
