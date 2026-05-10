package com.servicecentre.service_centre_api.controller;

import com.servicecentre.service_centre_api.model.Invoice;
import com.servicecentre.service_centre_api.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/job/{jobCardId}")
    public ResponseEntity<Invoice> getInvoiceByJobCard(
            @PathVariable Long jobCardId) {
        return ResponseEntity.ok(invoiceService.getInvoiceByJobCard(jobCardId));
    }

    @PostMapping("/job/{jobCardId}/generate")
    public ResponseEntity<Invoice> generateInvoice(
            @PathVariable Long jobCardId,
            @RequestBody Map<String, Object> body) {
        BigDecimal serviceCharge = new BigDecimal(
                body.get("serviceCharge").toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(invoiceService.generateInvoice(jobCardId, serviceCharge));
    }

    @PatchMapping("/{id}/payment")
    public ResponseEntity<Invoice> updatePayment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        BigDecimal amountPaid = new BigDecimal(
                body.get("amountPaid").toString());
        return ResponseEntity.ok(invoiceService.updatePayment(id, amountPaid));
    }
}