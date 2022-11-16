package com.capitol.pricing.services;

import com.capitol.pricing.exceptions.MissingArgumentException;
import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface Pricing {
    /**
     * Core service method required my capitol test that finds the price to apply based on a search date that should be
     * present between start and end dates "inclusively", a productId and a brandId, e.g. 2013-10-10T21:00:00, 35455 ad 1 (ZARA).
     * Notice that in case several prices are found during a certain date range then the one that has the highest priority is selected.
     * @param searchDate LocalDateTime
     * @param productId long
     * @param brandId int
     * @return Price found or exception in case of missing arguments or item not found.
     * @throws ItemNotFoundException
     * @throws MissingArgumentException
     */
    Price getByDateWithProductIdAndBrand(final LocalDateTime searchDate,
                                         long productId,
                                         int brandId) throws ItemNotFoundException, MissingArgumentException;



    /**
     * Retrieve all prices. Additional method outside of capitol test scope that that could serve
     * in the future in case of a more mature solution that provides more alternatives.
     * Additionally, if more prices were added then Pageable interface should by applied in order to return
     * results paginated when results can't be hold in memory.
     *
     * @return List<Price>
     */
    List<Price> getAll();

    /**
     * Retrieve resource by priceId. Additional method outside of capitol test scope that that could serve
     * in the future in case of a more mature solution that provides more alternatives.
     *
     * @param priceList Long
     * @return Price
     * @throws ItemNotFoundException
     */
    // price list is interpreted as price id:
    Price getByPriceList(Long priceList) throws ItemNotFoundException;
}
