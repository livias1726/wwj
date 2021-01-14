package logic.presentation.bean;

import java.util.List;

public class BusinessInCountryBean extends BusinessBean{

	private Float averageCost;
	private Float averageEarnings;
	private List<Float> popularity;
	private List<Integer> competitors;
	private List<Float> taxes;
	private String description;
	private Float startExpense;
	private CountryBean country;
	
	public Float getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(Float averageCost) {
		this.averageCost = averageCost;
	}

	public List<Float> getPopularity() {
		return popularity;
	}

	public void setPopularity(List<Float> popularity) {
		this.popularity = popularity;
	}

	public List<Integer> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<Integer> competitors) {
		this.competitors = competitors;
	}

	public Float getAverageEarnings() {
		return averageEarnings;
	}

	public void setAverageEarnings(Float averageEarnings) {
		this.averageEarnings = averageEarnings;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CountryBean getCountry() {
		return country;
	}

	public void setCountry(CountryBean country) {
		this.country = country;
	}
	
	public Float getStartExpense() {
		return startExpense;
	}

	public void setStartExpense(Float startExpense) {
		this.startExpense = startExpense;
	}
	
	public List<Float> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Float> taxes) {
		this.taxes = taxes;
	}
}
