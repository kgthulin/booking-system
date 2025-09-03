package com.system.booking.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "class")
public class Classes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "class_id")
    private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "available")
	private Integer available;
	@Column(name = "credit")
	private Integer credit;
	@Column(name = "start_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@Column(name = "end_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "package_id")
	private Package packagge;
	
	public Classes() {}
	
	public Classes(String name, Integer available, Integer credit, Date startTime, Date endTime) {
		this.name = name;
		this.available = available;
		this.credit = credit;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Classes(String name, Integer available, Integer credit, Date startTime, Date endTime, Package packagge) {
		this.name = name;
		this.available = available;
		this.credit = credit;
		this.startTime = startTime;
		this.endTime = endTime;
		this.packagge = packagge;
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

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Package getPackagge() {
		return packagge;
	}

	public void setPackagge(Package packagge) {
		this.packagge = packagge;
	}
}
