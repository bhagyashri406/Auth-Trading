package com.assignment.tradeservice;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "ticker")
public class Ticker {

	@Id
	@NotBlank
	@NotEmpty
	private String ticker;

	@NotBlank
	@NotEmpty
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Ticker() {

	}

	public Ticker(@NotBlank @NotEmpty String ticker, @NotBlank @NotEmpty double price) {
		super();
		this.ticker = ticker;
		this.price = price;
	}
}
