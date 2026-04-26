package com.servicecentre.service_centre_api.repository;

import com.servicecentre.service_centre_api.model.Invoice;
import com.servicecentre.service_centre_api.model.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByJobCard(JobCard jobCard);
}