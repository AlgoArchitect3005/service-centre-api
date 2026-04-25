package com.servicecentre.service_centre_api.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;


@Entity
@Table(name = "job_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String printerBrand;

    @Column(nullable = false)
    private String printerModel;

    @Column(nullable = false)
    private String problemDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(nullable = false)
    private LocalDate receivedDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private LocalDateTime deliveryDate;

    private String engineerNotes;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = JobStatus.RECEIVED;
        this.receivedDate = LocalDate.now();
    }

}
