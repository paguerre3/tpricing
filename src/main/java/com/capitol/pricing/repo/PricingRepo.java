package com.capitol.pricing.repo;

import com.capitol.pricing.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepo extends JpaRepository<Price, Long> {
}
