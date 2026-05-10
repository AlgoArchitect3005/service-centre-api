package com.servicecentre.service_centre_api.service;
import com.servicecentre.service_centre_api.exception.DuplicateResourceException;
import com.servicecentre.service_centre_api.exception.ResourceNotFoundException;
import com.servicecentre.service_centre_api.model.*;
import com.servicecentre.service_centre_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

   
    private final InvoiceRepository invoiceRepository;
    private final JobCardRepository jobCardRepository;
    private final JobPartRepository jobPartRepository;

    @Transactional
    public Invoice generateInvoice(Long jobCardId , BigDecimal serviceCharge){

        return null;
    }

}
