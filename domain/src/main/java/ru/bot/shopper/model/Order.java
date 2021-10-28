package ru.bot.shopper.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {


    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "DATE")
    private ZonedDateTime date;

    @Column(name = "LAST_CHANGE_DATE")
    private ZonedDateTime lastChangeDate;

    @Column(name = "SUPPLIER_ARTICLE")
    private String supplierArticle;

    @Column(name = "TECH_SIZE")
    private String techSize;

    @Column(name = "BARCODE")
    private String barcode;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @Column(name = "DISCOUNT_PERCENT")
    private Integer discountPercent;

    @Column(name = "WARE_HOUSE_NAME")
    private String warehouseName;

    @Column(name = "OBLAST")
    private String oblast;

    @Column(name = "INCOME_ID")
    private Integer incomeID;

    @Column(name = "ODID")
    private Long odid;

    @Column(name = "NMID")
    private Integer nmId;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "IS_CANCEL")
    private Boolean isCancel;

    @Column(name = "CANCEL_DATE")
    private ZonedDateTime cancelDate;

    @Column(name = "G_NUMBER")
    private String gNumber;
}

