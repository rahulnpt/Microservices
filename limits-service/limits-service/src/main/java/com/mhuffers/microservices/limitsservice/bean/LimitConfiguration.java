package com.mhuffers.microservices.limitsservice.bean;

public class LimitConfiguration {

	protected LimitConfiguration(){};
	
	private int minimum;
	private int maximum;
	
	public LimitConfiguration(int min, int max) {
	this.minimum = min;
	this.maximum=max;
	}

	public int getMinimum() {
		return minimum;
	}

	public int getMaximum() {
		return maximum;
	}

}
