package com.servicecentre.service_centre_api.controller;

import com.servicecentre.service_centre_api.model.JobCard;
import com.servicecentre.service_centre_api.model.JobPart;
import com.servicecentre.service_centre_api.model.JobStatus;
import com.servicecentre.service_centre_api.service.JobCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobCardController {

    private final JobCardService jobCardService;

    @GetMapping
    public ResponseEntity<List<JobCard>> getAllJobCards() {
        return ResponseEntity.ok(jobCardService.getAllJobCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCard> getJobCardById(@PathVariable Long id) {
        return ResponseEntity.ok(jobCardService.getJobCardById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobCard>> getByStatus(
            @PathVariable JobStatus status) {
        return ResponseEntity.ok(jobCardService.getJobCardsByStatus(status));
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<JobCard> createJobCard(
            @PathVariable Long customerId,
            @RequestBody JobCard jobCard) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobCardService.createJobCard(customerId, jobCard));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JobCard> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        JobStatus newStatus = JobStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(jobCardService.updateStatus(id, newStatus));
    }

    @PostMapping("/{jobCardId}/parts")
    public ResponseEntity<JobPart> addPartToJob(
            @PathVariable Long jobCardId,
            @RequestBody Map<String, Object> body) {
        Long inventoryId = Long.valueOf(body.get("inventoryId").toString());
        Integer quantity = Integer.valueOf(body.get("quantityUsed").toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobCardService.addPartToJob(jobCardId, inventoryId, quantity));
    }
}