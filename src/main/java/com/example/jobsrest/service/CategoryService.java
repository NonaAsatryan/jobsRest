package com.example.jobsrest.service;

import com.example.jobsrest.entity.Category;
import com.example.jobsrest.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Value("${images.upload.path}")
    public String imagePath;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category getById(int id) {
        return categoryRepository.getById(id);
    }

}
