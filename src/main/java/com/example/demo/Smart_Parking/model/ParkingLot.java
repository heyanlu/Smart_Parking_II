package com.example.demo.Smart_Parking.model;

import com.example.demo.Smart_Parking.ParkingConfig;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "parking_lot")
public class ParkingLot {
  @Id
  @SequenceGenerator(
      name = "parking_lot_sequence",
      sequenceName = "parking_lot_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "parking_lot_sequence"
  )
  private Long id;

  @ElementCollection
  private List<Boolean> parkingPositions;

  @OneToOne
  private Vehicle vehicle;


  public ParkingLot() {
    this.parkingPositions = new ArrayList<>(Collections.nCopies(ParkingConfig.TOTAL_CAPACITY, false));
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public void setVehicle(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public List<Boolean> getParkingPositions() {
    return parkingPositions;
  }

  public void setParkingPositions(List<Boolean> parkingPositions) {
    this.parkingPositions = parkingPositions;
  }

  public int findAvailablePositionAndPark() {
    for (int i = 0; i < parkingPositions.size(); i++) {
      if (!parkingPositions.get(i)) {
        parkingPositions.set(i, true);

        return i;
      }
    }
    return -1;
  }
}
