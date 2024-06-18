package com.example.demo.Smart_Parking.repository;

import com.example.demo.Smart_Parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
  Optional<Vehicle> findByLicensePlate(String licensePlate);
}
