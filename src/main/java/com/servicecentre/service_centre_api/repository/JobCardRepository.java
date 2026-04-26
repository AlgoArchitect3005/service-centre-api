package com.servicecentre.service_centre_api.repository;

import com.servicecentre.service_centre_api.model.JobCard;
import com.servicecentre.service_centre_api.model.JobStatus;
import com.servicecentre.service_centre_api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {
    List<JobCard> findByStatus(JobStatus status);
    List<JobCard> findByCustomer(Customer customer);
}