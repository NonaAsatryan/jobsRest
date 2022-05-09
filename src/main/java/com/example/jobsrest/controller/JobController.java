package com.example.jobsrest.controller;

import com.example.jobsrest.entity.Job;
import com.example.jobsrest.security.CurrentUser;
import com.example.jobsrest.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping("/job/post")
    public ResponseEntity addJob(@RequestBody Job jobs,
                                 @AuthenticationPrincipal CurrentUser currentUser) {
        jobs.setUser(currentUser.getUser());
        Job JobById = jobService.findJobById(jobs.getJobType().ordinal());
        List<Job> allByUser = jobService.findAllByUser(currentUser.getUser());
        jobService.save(jobs);
        for (Job job : allByUser) {
            if (job.getJobType().ordinal() == jobs.getJobType().ordinal()) {
                jobService.deleteById(jobs.getId());
            }
        }
        return ResponseEntity
                .ok(jobs);
    }

    @DeleteMapping("/job/deleteJob")
    public ResponseEntity deleteJob(@RequestParam("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        Job job = jobService.findJobById(id);
        jobService.deleteById(id);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/job/details")
    public ResponseEntity detailsPage() {
        return ResponseEntity
                .ok(detailsPage());
}

    @GetMapping("/job/jobDetails")
    public ResponseEntity jobDetailsPage() {
        return ResponseEntity
                .ok(jobDetailsPage());
    }

    @GetMapping("/job/jobList")
    public ResponseEntity jobListPage() {
        return ResponseEntity
                .ok(jobListPage());
    }

    @GetMapping("/job/appliedJob")
    public ResponseEntity appliedJobPage() {
        return ResponseEntity
                .ok(appliedJobPage());
    }


}
