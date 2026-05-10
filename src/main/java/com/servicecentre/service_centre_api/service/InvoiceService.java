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
    public Invoice generateInvoice(Long jobCardId, BigDecimal serviceCharge) {

        JobCard jobCard = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCard not found with id: " + jobCardId));

        // Duplicate invoice check
        if (invoiceRepository.findByJobCard(jobCard).isPresent()) {
            throw new DuplicateResourceException(
                    "Invoice already exists for job card: " + jobCardId);
        }

        // Parts total calculate karo
        List<JobPart> parts = jobPartRepository.findByJobCard(jobCard);
        BigDecimal partsTotal = parts.stream()
                .map(JobPart::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Invoice build karo
        Invoice invoice = Invoice.builder()
                .jobCard(jobCard)
                .serviceCharge(serviceCharge)
                .partsTotal(partsTotal)
                .grandTotal(serviceCharge.add(partsTotal))
                .build();

        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invoice not found with id: " + id));
    }

    @Transactional
    public Invoice updatePayment(Long id, BigDecimal amountPaid) {
        Invoice invoice = getInvoiceById(id);
        invoice.setAmountPaid(amountPaid);

        // Payment status auto set
        if (amountPaid.compareTo(BigDecimal.ZERO) == 0) {
            invoice.setPaymentStatus(PaymentStatus.UNPAID);
        } else if (amountPaid.compareTo(invoice.getGrandTotal()) >= 0) {
            invoice.setPaymentStatus(PaymentStatus.PAID);
        } else {
            invoice.setPaymentStatus(PaymentStatus.PARTIAL);
        }

        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceByJobCard(Long jobCardId) {
        JobCard jobCard = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCard not found with id: " + jobCardId));
        return invoiceRepository.findByJobCard(jobCard)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invoice not found for job card: " + jobCardId));
    }
}