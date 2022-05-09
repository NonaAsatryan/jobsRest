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
@Table(name = "resume")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String personalDetails;
    private String education;
    private String workExperience;
    private String professionalSkills;
    private String qualifications;
    private String languageProficiency;
    private String awards;
    private String picUrl;
    private String resumeFile;
    private String address;
    @ManyToOne
    private User user;
}
