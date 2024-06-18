package com.example.demo.Smart_Parking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle")
public class Vehicle {

  @Id
  @SequenceGenerator(
      name = "vehicle_sequence",
      sequenceName = "vehicle_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "vehicle_sequence"
  )
  private Long id;

  private String licensePlate;

  private Integer parkingPosition;

  private LocalDateTime arrivalTime;
  private LocalDateTime paymentTime;
  private LocalDateTime leaveTime;

  public Vehicle() {
  }

  public Integer getParkingPosition() {
    return parkingPosition;
  }

  public void setParkingPosition(Integer parkingPosition) {
    this.parkingPosition = parkingPosition;
  }

  public Vehicle(String licensePlate, Integer parkingPosition, LocalDateTime arrivalTime, LocalDateTime paymentTime, LocalDateTime leaveTime) {
    this.licensePlate = licensePlate;
    this.parkingPosition = parkingPosition;
    this.arrivalTime = arrivalTime;
    this.paymentTime = paymentTime;
    this.leaveTime = leaveTime;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }


  public void setArrivalTime(LocalDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public void setPaymentTime(LocalDateTime paymentTime) {
    this.paymentTime = paymentTime;
  }

  public void setLeaveTime(LocalDateTime leaveTime) {
    this.leaveTime = leaveTime;
  }

  public Long getId() {
    return id;
  }

  public String getLicensePlate() {
    return licensePlate;
  }


  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  public LocalDateTime getPaymentTime() {
    return paymentTime;
  }

  public LocalDateTime getLeaveTime() {
    return leaveTime;
  }
}
