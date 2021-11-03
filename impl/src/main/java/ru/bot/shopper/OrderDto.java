package ru.bot.shopper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @JsonProperty("number")
    private Long number;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Moscow")
    @JsonProperty("date")
    private ZonedDateTime date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Moscow")
    @JsonProperty("lastChangeDate")
    private ZonedDateTime lastChangeDate;

    @JsonProperty("supplierArticle")
    private String supplierArticle;//

    @JsonProperty("techSize")
    private String techSize;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("quantity")
    private Integer quantity;//

    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;//

    @JsonProperty("discountPercent")
    private Integer discountPercent;//

    @JsonProperty("warehouseName")
    private String warehouseName;

    @JsonProperty("oblast")
    private String oblast;

    @JsonProperty("incomeID")
    private Integer incomeID;

    @JsonProperty("odid")
    private Long odid;

    @JsonProperty("nmId")
    private Integer nmId;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("category")
    private String category;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("isCancel")
    private Boolean isCancel;

    @JsonProperty("cancel_dt")
    private String cancelDate;

    @JsonProperty("gNumber")
    private String gNumber;

    public String toString() {
        return "\nOrder:\n" +
                "subject='" + subject + '\'' +
                ",\nquantity=" + quantity +
                ",\nnumber=" + number +
                ",\ndate=" + date +
                ",\ntotalPrice=" + totalPrice +
                ",\nsupplierArticle=" + supplierArticle +
                ",\nlastChangeDate=" + lastChangeDate +
                "\n";
    }
}
