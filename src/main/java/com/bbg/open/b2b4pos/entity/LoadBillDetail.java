package com.bbg.open.b2b4pos.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bbg.open.b2b4pos.common.BaseEntity;

public class LoadBillDetail extends BaseEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 494453426632657680L;

	private String sellerCode;

    private BigDecimal factPrice;

    private Integer qty;

    private Integer outQty;

    private String saleUnit;

    private String isn;

    private Integer baleCount;

    private BigDecimal couponAmount;

    private BigDecimal discountAmount;

    private String isGift;
    
    private String goodsCode;

    private String loadCode;

    private String orderCode;

    private String outbillCode;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getLoadCode() {
        return loadCode;
    }

    public void setLoadCode(String loadCode) {
        this.loadCode = loadCode == null ? null : loadCode.trim();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getOutbillCode() {
        return outbillCode;
    }

    public void setOutbillCode(String outbillCode) {
        this.outbillCode = outbillCode == null ? null : outbillCode.trim();
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode == null ? null : sellerCode.trim();
    }

    public BigDecimal getFactPrice() {
        return factPrice;
    }

    public void setFactPrice(BigDecimal factPrice) {
        this.factPrice = factPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public String getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit == null ? null : saleUnit.trim();
    }

    public String getIsn() {
        return isn;
    }

    public void setIsn(String isn) {
        this.isn = isn == null ? null : isn.trim();
    }

    public Integer getBaleCount() {
        return baleCount;
    }

    public void setBaleCount(Integer baleCount) {
        this.baleCount = baleCount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getIsGift() {
        return isGift;
    }

    public void setIsGift(String isGift) {
        this.isGift = isGift == null ? null : isGift.trim();
    }
}