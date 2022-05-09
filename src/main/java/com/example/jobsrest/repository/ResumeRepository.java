package com.example.jobsrest.repository;

import com.example.jobsrest.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    Resume findByUser_Id(int userId);
}
