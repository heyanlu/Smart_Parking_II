package com.example.demo.Smart_Parking.service;

import com.example.demo.Smart_Parking.model.Member;
import com.example.demo.Smart_Parking.model.Vehicle;
import java.util.List;

public interface IParkingService {
  String parkVehicle(Vehicle vehicle);

  String processPayment(String licensePlateJson);

  String processToLeave(String licensePlate);
  List<Vehicle> getAllVehicles();

  void addMember(String memberInfoJson);

  String deleteMember(String licensePlateJson);

  void checkMembershipEndTimes();

  List<Member> getAllMember();
}
