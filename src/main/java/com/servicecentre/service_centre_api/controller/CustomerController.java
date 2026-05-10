package com.servicecentre.service_centre_api.controller;

import com.servicecentre.service_centre_api.model.Customer;

import com.servicecentre.service_centre_api.model.JobCard;
import com.servicecentre.service_centre_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchByName(
            @RequestParam String name) {
        return ResponseEntity.ok(customerService.searchByName(name));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Customer> searchByPhone(
            @PathVariable String phone) {
        return ResponseEntity.ok(customerService.searchByPhone(phone));
    }

    @GetMapping("/{id}/jobs")
    public ResponseEntity<List<JobCard>> getJobHistory(
            @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getJobHistory(id));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customer));
    }
}