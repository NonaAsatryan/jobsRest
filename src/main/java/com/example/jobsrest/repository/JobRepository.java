package com.example.jobsrest.repository;

import com.example.jobsrest.entity.Job;
import com.example.jobsrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    List<Job> findAllByUser(User user);
}
