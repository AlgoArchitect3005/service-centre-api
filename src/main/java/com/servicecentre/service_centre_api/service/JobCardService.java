package com.servicecentre.service_centre_api.service;

import com.servicecentre.service_centre_api.exception.ResourceNotFoundException;
import com.servicecentre.service_centre_api.model.*;
import com.servicecentre.service_centre_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCardService {

    private final JobCardRepository jobCardRepository;
    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;
    private final JobPartRepository jobPartRepository;

    // Create new job card
    public JobCard createJobCard(Long customerId, JobCard jobCard) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + customerId));
        jobCard.setCustomer(customer);
        return jobCardRepository.save(jobCard);
    }

    // Get all job cards
    public List<JobCard> getAllJobCards() {
        return jobCardRepository.findAll();
    }

    // Get job card by id
    public JobCard getJobCardById(Long id) {
        return jobCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job card not found with id: " + id));
    }

    // Get job cards by status
    public List<JobCard> getJobCardsByStatus(JobStatus status) {
        return jobCardRepository.findByStatus(status);
    }

    // Update job status
    public JobCard updateStatus(Long id, JobStatus newStatus) {
        JobCard jobCard = getJobCardById(id);
        jobCard.setStatus(newStatus);
        return jobCardRepository.save(jobCard);
    }

    // Add part to job card
    @Transactional
    public JobPart addPartToJob(Long jobCardId, Long inventoryId, 
                                 Integer quantityUsed) {
        JobCard jobCard = getJobCardById(jobCardId);

        Inventory item = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory item not found with id: " + inventoryId));

        // Check stock available hai ya nahi
        if (item.getQuantity() < quantityUsed) {
            throw new ResourceNotFoundException(
                    "Insufficient stock for item: " + item.getItemName());
        }

        // Stock deduct karo
        item.setQuantity(item.getQuantity() - quantityUsed);
        inventoryRepository.save(item);

        // JobPart record banao
        JobPart jobPart = JobPart.builder()
                .jobCard(jobCard)
                .inventory(item)
                .quantityUsed(quantityUsed)
                .unitPriceAtTime(item.getUnitPrice())
                .build();

        return jobPartRepository.save(jobPart);
    }
}