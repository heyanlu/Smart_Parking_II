package com.example.demo.Smart_Parking;

import com.example.demo.Smart_Parking.model.ParkingLot;
import com.example.demo.Smart_Parking.model.Vehicle;
import com.example.demo.Smart_Parking.repository.ParkingLotRepository;
import com.example.demo.Smart_Parking.repository.VehicleRepository;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class ParkingConfig {
  public static final int TOTAL_CAPACITY = 100;

  @Bean
  CommandLineRunner commandLineRunner(VehicleRepository vehicleRepository, ParkingLotRepository parkingLotRepository) {
    return args -> {
      Vehicle vehicle1 = new Vehicle(
          "ABC123",
          0,
          LocalDateTime.now(),
          LocalDateTime.now(),
          LocalDateTime.now()
      );

      Vehicle vehicle2 = new Vehicle(
          "ABC124",
          1,
          LocalDateTime.now(),
          null,
          null
      );

      vehicleRepository.saveAll(List.of(vehicle1, vehicle2));

      ParkingLot parkingLot = new ParkingLot();
      List<Boolean> initialParkingPositions = new ArrayList<>(
          Collections.nCopies(ParkingConfig.TOTAL_CAPACITY, false));
      initialParkingPositions.set(0, true);
      initialParkingPositions.set(1, true);

      parkingLot.setParkingPositions(initialParkingPositions);

      parkingLotRepository.save(parkingLot);
    };
  }
}
