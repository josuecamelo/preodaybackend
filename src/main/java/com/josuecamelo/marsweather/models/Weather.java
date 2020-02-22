package com.josuecamelo.marsweather.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "weather")
@NamedQuery(name = "Weather.findBySol", query = "SELECT w FROM Weather w WHERE w.sol = ?1")
public class Weather {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="weather_Seq")
	@SequenceGenerator(name="weather_Seq", sequenceName="weather_seq_id", allocationSize=1)
	@Column
	private Long id;
		
	@Column(unique=true)
	private Integer sol;

	@Column
	private Double av;
	
	@Column
	private Double max;
	
	@Column
	private Double min;
		
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSol() {
		return sol;
	}

	public void setSol(Integer sol) {
		this.sol = sol;
	}

	public Double getAv() {
		return av;
	}

	public void setAv(Double av) {
		this.av = av;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", sol=" + sol + ", av=" + av + ", max=" + max + ", min=" + min + "]";
	}	
	
	
}
