package com.exe201.opalwed.utils;

import java.util.Random;

public class OpalUtils {

    private static final Random random = new Random();

    public static String generateRandomOTP() {
        StringBuilder otp = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);  // Generate random number between 0 and 9
            otp.append(digit);
        }
        return otp.toString();
    }

}
