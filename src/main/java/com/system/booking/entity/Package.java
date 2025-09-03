package com.system.booking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "package")
public class Package implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "package_id")
    private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "is_expired")
	private Boolean isExpired;
	@Column(name = "is_purchased")
	private Boolean isPurchased;
	@Column(name = "price")
	private BigDecimal price;
	@Column(name = "credit")
	private Integer credit;
	@Column(name = "expired_date")
	@Temporal(TemporalType.DATE)
	private Date expiredDate;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
	private Country country;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private AppUser user;
	
	public Package() {}
	
	public Package(String name, BigDecimal price, Integer credit, Date expiredDate) {
		this.name = name;
		this.price = price;
		this.credit = credit;
		this.expiredDate = expiredDate;
	}

	public Package(String name, Boolean isExpired, Boolean isPurchased, BigDecimal price, Integer credit, Date expiredDate, Country country,
			AppUser user) {
		this.name = name;
		this.isExpired = isExpired;
		this.isPurchased = isPurchased;
		this.price = price;
		this.credit = credit;
		this.expiredDate = expiredDate;
		this.country = country;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getIsPurchased() {
		return isPurchased;
	}

	public void setIsPurchased(Boolean isPurchased) {
		this.isPurchased = isPurchased;
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

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}
}
