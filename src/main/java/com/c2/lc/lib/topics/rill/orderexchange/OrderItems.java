package com.c2.lc.lib.topics.rill.orderexchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItems {

    @JsonProperty("CESSAmount")
    String CESSAmount;

    @JsonProperty("CGSTAmount")
    String CGSTAmount;

    @JsonProperty("IGSTAmount")
    String IGSTAmount;

    @JsonProperty("SGSTAmount")
    String SGSTAmount;

    @JsonProperty("ADCAmount")
    String ADCAmount;

    @JsonProperty("Barcode")
    String Barcode;

    @JsonProperty("CESSRate")
    String CESSRate;

    @JsonProperty("CGSTRate")
    String CGSTRate;

    @JsonProperty("SGSTRate")
    String SGSTRate;

    @JsonProperty("IGSTRate")
    String IGSTRate;

    @JsonProperty("ProductName")
    String ProductName;

    @JsonProperty("ProductSKU")
    String ProductSKU;

    @JsonProperty("ProductSKUUQC")
    String ProductSKUUQC;

    @JsonProperty("MRP")
    String MRP;

    @JsonProperty("Quantity")
    String Quantity;

    @JsonProperty("SalePrice")
    String SalePrice;

    @JsonProperty("TaxableAmount")
    String TaxableAmount;

    @JsonProperty("TotalTax")
    String TotalTax;

}
