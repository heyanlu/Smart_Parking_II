package com.example.demo.Smart_Parking.controller;

import com.example.demo.Smart_Parking.model.Member;
import com.example.demo.Smart_Parking.model.Vehicle;
import com.example.demo.Smart_Parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/smart_parking")
public class ParkingController {

  private final ParkingService parkingService;

  @Autowired
  public ParkingController(ParkingService parkingService) {
    this.parkingService = parkingService;
  }

  @PostMapping("/park")
  public ResponseEntity<Object> parkVehicle(@RequestBody String licensePlate) {
    Vehicle vehicle = new Vehicle();
    vehicle.setLicensePlate(licensePlate);

    String parkedVehicle = parkingService.parkVehicle(vehicle);
    return ResponseEntity.ok(parkedVehicle);
  }

  @PostMapping("/process-payment")
  public ResponseEntity<String> processPayment(@RequestBody String licensePlate) {
    String paymentResult = parkingService.processPayment(licensePlate);
    return ResponseEntity.ok(paymentResult);
  }

  @PostMapping("/process-to-leave")
  public ResponseEntity<String> processToLeave(@RequestBody String licensePlate) {
    String paymentResult = parkingService.processToLeave(licensePlate);
    return ResponseEntity.ok(paymentResult);
  }


  @GetMapping("/all-vehicles")
  public List<Vehicle> getAllVehicles() {
    return parkingService.getAllVehicles();
  }

  @PostMapping("/add-member")
  public ResponseEntity<String> addMember(@RequestBody Member member) {
    parkingService.addMember(member.getLicensePlate(), member.getMemberType());
    return ResponseEntity.ok("Member added successfully");
  }

  @DeleteMapping("/delete-member/{licensePlate}")
  public ResponseEntity<String> deleteMember(@PathVariable String licensePlate) {
    parkingService.deleteMember(licensePlate);
    return ResponseEntity.ok("Member with license plate " + licensePlate + " deleted successfully");
  }

  @GetMapping("/check-membership")
  public String checkMembership() {
    parkingService.checkMembershipEndTimes();
    return "Membership check triggered successfully";
  }
}
