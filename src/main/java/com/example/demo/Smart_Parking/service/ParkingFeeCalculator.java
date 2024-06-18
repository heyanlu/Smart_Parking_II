package com.example.demo.Smart_Parking.service;

import java.time.Duration;

public class ParkingFeeCalculator {
  public static final float ratePerHour = 5.0F;

  public static float calculateParkingFee(Duration duration) {
    long seconds = duration.getSeconds();

    float minutes = (float) Math.ceil(seconds / 60.0);
    float hours = (float) Math.ceil(seconds / 3600.0);

    float parkingFee;
    if (minutes <= 20) {
      parkingFee = 0.0f;
    } else {
      parkingFee = hours * ratePerHour;
    }
    return parkingFee;
  }
}
