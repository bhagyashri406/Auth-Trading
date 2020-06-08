package com.assignment.registerservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "balance")
public class Balance {

	@Id
	@Column(unique = true)
	private int id;

	private double balance = 100000;

	private int quantity = 0;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Balance(int id, double balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

	public Balance(int id, int quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public Balance() {

	}

}
