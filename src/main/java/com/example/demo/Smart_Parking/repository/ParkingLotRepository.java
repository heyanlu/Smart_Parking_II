package com.example.demo.Smart_Parking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Smart_Parking.model.ParkingLot;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
  Optional<ParkingLot> findFirstByParkingPositionsContains(boolean isOccupied);
}
