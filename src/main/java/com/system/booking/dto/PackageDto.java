package com.system.booking.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.system.booking.entity.Country;

public class PackageDto {

	private String name;
	private Boolean isExpired;
	private BigDecimal price;
	private Integer credit;
	private Date expiredDate;
	private Country country;
	
	public PackageDto(String name, Boolean isExpired, BigDecimal price, Integer credit, Date expiredDate,
			Country country) {
		this.name = name;
		this.isExpired = isExpired;
		this.price = price;
		this.credit = credit;
		this.expiredDate = expiredDate;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
