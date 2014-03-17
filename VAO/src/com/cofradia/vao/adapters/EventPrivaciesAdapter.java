package com.cofradia.vao.adapters;

public class EventPrivaciesAdapter {
    String spinnerText;
    String value;
    
	public EventPrivaciesAdapter(String spinnerText, String value) {
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
