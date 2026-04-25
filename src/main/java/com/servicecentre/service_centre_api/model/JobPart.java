package com.servicecentre.service_centre_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "job_parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_card_id", nullable = false)
    private JobCard jobCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private Inventory inventory;

    @Column(nullable = false)
    private Integer quantityUsed;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPriceAtTime;

    public BigDecimal getSubtotal() {
        return unitPriceAtTime.multiply(BigDecimal.valueOf(quantityUsed));
    }
}