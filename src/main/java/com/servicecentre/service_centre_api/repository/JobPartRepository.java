package com.servicecentre.service_centre_api.repository;

import com.servicecentre.service_centre_api.model.JobCard;
import com.servicecentre.service_centre_api.model.JobPart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPartRepository extends JpaRepository<JobPart, Long> {
    List<JobPart> findByJobCard(JobCard jobCard);
}