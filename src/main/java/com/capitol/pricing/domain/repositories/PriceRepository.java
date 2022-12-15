package com.capitol.pricing.repositories;

import com.capitol.pricing.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    /**
     * Obtain top 3 candidate prices to apply ordered by priority in descendant order browsed between start and end dates
     * passing productId and brandId, e.g. 2013-10-10T21:00:00 - 2013-10-10T21:00:00, 35455 ad 1 (ZARA).
     *
     * @param startDate LocalDateTime
     * @param endDate LocalDateTime
     * @param productId Long
     * @param brandId Integer
     * @return List<Price> found having top 3 prices in order or less.
     */
    List<Price> findTop3ByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderByPriorityDesc(LocalDateTime startDate,
                                                                                                                    LocalDateTime endDate,
                                                                                                                    Long productId,
                                                                                                                    Integer brandId);



    /**
     * Retrieve by priceId. Additional method outside of capitol test scope that is being used as helper to check
     * JPA find by PK.
     *
     * @param priceListOrId Long
     * @return Optional<Price>
     */
    // price list is interpreted as price id:
    Optional<Price> findByPriceList(Long priceListOrId);
}
