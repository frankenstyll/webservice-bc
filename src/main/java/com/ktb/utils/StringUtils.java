package com.ktb.utils;

import java.util.Random;
import java.util.UUID;

public class StringUtils {
	
	public static String generateOTP(int range) {
		String otpNum = "";
		for(int i = 0 ; i < range ; i++) {
			Random random = new Random();
			otpNum += String.valueOf((0 + random.nextInt(9)));
		}
		return otpNum;
	}
	
    public static String generateRandomStringByUUID() {
    	return UUID.randomUUID().toString();
    }

    public static String generateRandomStringByUUIDNoDash() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    public static Boolean isNullOrEmpty(String v) {
    	if(null == v) {
    		return true;
    	}else if("".equals(v.trim())) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
//    public static void main(String[] args) {
//		System.out.println(generateRandomStringByUUIDNoDash(10));
//	}
    
}
