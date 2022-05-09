package com.example.jobsrest.service;

import com.example.jobsrest.entity.Category;
import com.example.jobsrest.entity.Company;
import com.example.jobsrest.entity.Job;
import com.example.jobsrest.entity.User;
import com.example.jobsrest.exception.ResourceNotFoundException;
import com.example.jobsrest.repository.CategoryRepository;
import com.example.jobsrest.repository.CompanyRepository;
import com.example.jobsrest.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;


    public Job save(Job job, User user, int categoryId, int companyId) {
        Category category = categoryRepository.getById(categoryId);
        job.setCategory(category);
        Company company = companyRepository.getById(companyId);
        job.setCompany(company);
        job.setUser(user);

        return jobRepository.save(job);
    }

    public void deleteById(int id) {
        jobRepository.deleteById(id);
    }

    public Job findJobById(int id) {
        Optional<Job> byId = jobRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ResourceNotFoundException("Product does not found"+byId.get().getName());
    }

    public List<Job> findAllByUser(User user) {
        return jobRepository.findAllByUser(user);
    }
}
