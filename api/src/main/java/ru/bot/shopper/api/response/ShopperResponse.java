package ru.bot.shopper.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopperResponse {
    private Long number;
    private LocalDateTime date;
    private LocalDateTime lastChangeDate;
    private String supplierArticle;
    private String techSize;
    private String barcode;
    private Integer quantity;
    private Integer totalPrice;
    private Integer discountPercent;
    private String warehouseName;
    private String oblast;
    private Integer incomeID;
    private Integer odid;
    private Integer nmId;
    private String subject;
    private String category;
    private String brand;
    private boolean isCancel;
    @JsonProperty("cancel_dt")
    private LocalDateTime cancelDt;
    private String gNumber;

}