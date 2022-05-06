package com.estockmarket.app.bean;

import java.sql.Time;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "stock")
public class Stock {
	@Id
	private String stockId;
	private String stockName;
	private String companyCode;
	private double stockPrice;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date stockDate;
	private String stockTime;
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public double getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}
	public Date getStockDate() {
		return stockDate;
	}
	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	public String getStockTime() {
		return stockTime;
	}
	public void setStockTime(String stockTime) {
		this.stockTime = stockTime;
	}
	public Stock(String stockId, String stockName, String companyCode, double stockPrice, Date stockDate,
			String stockTime) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.companyCode = companyCode;
		this.stockPrice = stockPrice;
		this.stockDate = stockDate;
		this.stockTime = stockTime;
	}
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
