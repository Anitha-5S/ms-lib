package com.c2.lc.lib.topics.rill.orderexchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BillDetail {

    @JsonProperty("ExternalOrderId")
    String ExternalOrderId;

    @JsonProperty("TotalAmount")
    String TotalAmount;

    @JsonProperty("InvoiceNumber")
    String InvoiceNumber;

    @JsonProperty("TotalTax")
    String TotalTax;

    @JsonProperty("CustomerId")
    String CustomerId;

    @JsonProperty("StoreCustomerId")
    String StoreCustomerId;

    @JsonProperty("CustomerAddressId")
    String CustomerAddressId;

    @JsonProperty("OrderItems")
    List<OrderItems> orderItems;

    @JsonProperty("PaymentModes")
    List<PaymentModes> paymentModes;

    @JsonProperty("SaleDateTime")
    String SaleDateTime;

    @JsonProperty("SaleType")
    String SaleType;

    @JsonProperty("SaleStatus")
    String SaleStatus;

    @JsonProperty("SpecialDiscount")
    String SpecialDiscount;

    @JsonProperty("TaxableAmount")
    String TaxableAmount;

    @JsonProperty("TotalCouponValue")
    String TotalCouponValue;

    @JsonProperty("TotalDiscount")
    String TotalDiscount;

}
