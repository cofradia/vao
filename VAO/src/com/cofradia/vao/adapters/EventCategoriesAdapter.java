package com.cofradia.vao.adapters;

public class EventCategoriesAdapter {
    String spinnerText;
    Integer value;
    
	public EventCategoriesAdapter(String spinnerText, Integer value) {
		super();
		this.spinnerText = spinnerText;
		this.value = value;
	}
	public String toString() {
        return spinnerText;
    }
	public String getSpinnerText() {
		return spinnerText;
	}
	public void setSpinnerText(String spinnerText) {
		this.spinnerText = spinnerText;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}
