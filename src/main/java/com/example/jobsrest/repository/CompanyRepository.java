package com.example.jobsrest.repository;

import com.example.jobsrest.entity.Company;
import com.example.jobsrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

    List<Company> findAll(User user);
}
