package com.capitol.pricing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "brand_id", nullable = false)
    int brandId;

    LocalDateTime startDate;

    LocalDateTime endDate;

    long priceList;

    long productId;

    int priority;

    BigDecimal price;

    String currency;
}
