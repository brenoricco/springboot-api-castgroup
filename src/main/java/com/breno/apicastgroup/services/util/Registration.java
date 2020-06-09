package com.breno.apicastgroup.services.util;

import java.util.Random;

public class Registration {
	
	public static String generator() {
		
		Random random = new Random();
		
		return Integer.toString(random.hashCode());
	}
}
