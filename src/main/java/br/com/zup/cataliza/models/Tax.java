package br.com.zup.cataliza.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "taxes")
public class Tax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Double taxRate;

	public Tax() {
	}

	public Tax(String name, String description, Double taxRate) {
		this.name = name;
		this.description = description;
		this.taxRate = taxRate;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getTaxRate() {
		return taxRate;
	}
}
