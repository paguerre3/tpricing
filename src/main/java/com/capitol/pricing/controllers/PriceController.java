package com.capitol.pricing.controllers;

import com.capitol.pricing.exceptions.MissingArgumentException;
import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;
import com.capitol.pricing.models.views.PriceRate;
import com.capitol.pricing.services.Pricing;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {
    private final Pricing pricing;

    // Injection of "interface" via constructor instead of autowiring via
    // attribute / method:
    public PriceController(Pricing pricing) {
        this.pricing = pricing;
    }

    /**
     * API method required by capitol tests that finds the price to apply based on a search date that should be
     * present between start and end dates "inclusively", a productId and a brandId, e.g. 2020-06-14T10:00:00, 35455 ad 1 (ZARA).
     * Notice that in case several prices are found during a certain date range then the one that has the highest priority is selected.
     *
     * @param dateToApply LocalDateTime from ISO format
     * @param productId long
     * @param brandId int
     * @return ResponseEntity<PriceRate>
     * @throws ItemNotFoundException
     * @throws MissingArgumentException
     */
    @ApiOperation(value = "API required by capitol challenge that finds the price to apply" +
            " based on a search date that should be present between start and end dates \"inclusively\", a productId and a brandId," +
            " e.g. 2020-06-14T10:00:00, 35455 ad 1 (ZARA)",
            notes = "In case several prices are found during a certain date range then the one that has the highest priority is selected.\n Search date must be specified in ISO.DATE_TIME format")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Price to apply successfully found"),
            @ApiResponse(code = 404, message = "Not price to apply was found"),
            @ApiResponse(code = 500, message = "General error")
    })
    @GetMapping("/search/{dateToApply}/{productId}/{brandId}")
    public ResponseEntity<PriceRate> searchPriceToApply(@ApiParam(name = "dateToApply", value = "2020-06-14T10:00:00", required = true)
                                                        @PathVariable(value = "dateToApply")
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        LocalDateTime dateToApply,
                                                        @ApiParam(name = "productId", value = "35455", required = true)
                                                    @PathVariable(value = "productId") long productId,
                                                        @ApiParam(name = "brandId", value = "1", required = true)
                                                    @PathVariable(value = "brandId")  int brandId)
            throws ItemNotFoundException, MissingArgumentException {
        // price rate ignores "priority" on display, i.e. @JsonIgnore:
        return ResponseEntity.ok().body(Optional.of(this.pricing.getByDateWithProductIdAndBrand(dateToApply,
                productId, brandId)).map(p -> new PriceRate(p.getBrandId(), p.getStartDate(), p.getEndDate(),
                p.getPriceList(), p.getProductId(), p.getPrice(), p.getCurrency())).get());
    }



    /**
     * Retrieve all prices. Additional API outside of capitol test scope that could serve
     * in the future in case of a more mature solution that provides more alternatives.
     * Additionally, if more prices were added then Pageable interface should by applied in order to return
     * results paginated when results can't be hold in memory.
     *
     * @return List<Price>
     */
    @ApiOperation(value = "Retrieve all prices API. Additional API outside of capitol test scope" +
            " that is simply being used for verification purposes",
            notes = "If more prices were added then Pageable interface should by applied in order to return" +
                    " results paginated when they can't be hold in memory because of the size")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete price list"),
    })
    @GetMapping("/all")
    public List<Price> getAllPrices() {
        return this.pricing.getAll();
    }
}
