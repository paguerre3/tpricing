package com.capitol.pricing.repositories;

import com.capitol.pricing.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    // price list is interpreted as price id:
    Optional<Price> findByPriceList(Long priceListOrId);
}
