package com.example.demo.Smart_Parking.controller;

import com.example.demo.Smart_Parking.model.LicensePlateRequest;
import com.example.demo.Smart_Parking.model.Member;
import com.example.demo.Smart_Parking.model.MemberType;
import com.example.demo.Smart_Parking.model.ResponseMessage;
import com.example.demo.Smart_Parking.model.Vehicle;
import com.example.demo.Smart_Parking.service.ParkingService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/smart_parking")
@CrossOrigin(origins = "http://localhost:5174")
public class ParkingController {

  private final ParkingService parkingService;

  @Autowired
  public ParkingController(ParkingService parkingService) {
    this.parkingService = parkingService;
  }

  @PostMapping("/park")
  public ResponseEntity<Map<String, Object>> parkVehicle(@RequestBody Map<String, String> request) {
    String licensePlate = request.get("licensePlate");
    Map<String, Object> response = parkingService.parkVehicle(licensePlate);

    //ResponseMessage response = new ResponseMessage(result);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/process-payment")
  public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, String> request) {
    String licensePlate = request.get("licensePlate");
    Map<String, Object> response = parkingService.processPayment(licensePlate);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/process-to-leave")
  public ResponseEntity<Map<String, Object>> processToLeave(@RequestBody Map<String, String> request) {
    String licensePlate = request.get("licensePlate");
    Map<String, Object> response = parkingService.processToLeave(licensePlate);
    return ResponseEntity.ok(response);
  }


  @GetMapping("/all-vehicles")
  public List<Vehicle> getAllVehicles() {
    return parkingService.getAllVehicles();
  }

  @PostMapping("/add-member")
  public ResponseEntity<Map<String, Object>> addMember(@RequestBody Map<String, String> request) {
    String licensePlate = request.get("licensePlate");
    MemberType memberType = MemberType.valueOf(request.get("memberType"));

    Map<String, Object> response = parkingService.addMember(licensePlate, memberType);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete-member")
  public ResponseEntity<Map<String, Object>> deleteMember(@RequestBody Map<String, String> request) {
    String licensePlate = request.get("licensePlate");
    Map<String, Object> response = parkingService.deleteMember(licensePlate);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/check-membership")
  public String checkMembership() {
    parkingService.checkMembershipEndTimes();
    return "Membership check triggered successfully";
  }

  @GetMapping("/all-member")
  public List<Member> getAllMember() {
    return parkingService.getAllMember();
  }

}
