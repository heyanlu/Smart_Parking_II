package com.example.demo.Smart_Parking.service;

import com.example.demo.Smart_Parking.model.Member;
import com.example.demo.Smart_Parking.model.MemberType;
import com.example.demo.Smart_Parking.model.Vehicle;
import java.util.List;
import java.util.Map;

public interface IParkingService {
  Map<String, Object> parkVehicle(String licensePlate);

  Map<String, Object> processPayment(String licensePlate);

  Map<String, Object> processToLeave(String licensePlate);
  List<Vehicle> getAllVehicles();

  Map<String, Object> addMember(String licensePlate, MemberType memberType);

  Map<String, Object> deleteMember(String licensePlate);

  void checkMembershipEndTimes();

  List<Member> getAllMember();
}
