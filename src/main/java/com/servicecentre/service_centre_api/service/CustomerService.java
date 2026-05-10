package com.servicecentre.service_centre_api.service;

import com.servicecentre.service_centre_api.exception.DuplicateResourceException;
import com.servicecentre.service_centre_api.exception.ResourceNotFoundException;
import com.servicecentre.service_centre_api.model.Customer;
import com.servicecentre.service_centre_api.model.JobCard;
import com.servicecentre.service_centre_api.repository.CustomerRepository;
import com.servicecentre.service_centre_api.repository.JobCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JobCardRepository jobCardRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByPhone(customer.getPhone()).isPresent()) {
            throw new DuplicateResourceException(
                    "Customer with this phone already exists: " + customer.getPhone());
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + id));
    }

    public List<Customer> searchByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    public Customer searchByPhone(String phone) {
        return customerRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with phone: " + phone));
    }

    public List<JobCard> getJobHistory(Long customerId) {
        Customer customer = getCustomerById(customerId);
        return jobCardRepository.findByCustomer(customer);
    }
}