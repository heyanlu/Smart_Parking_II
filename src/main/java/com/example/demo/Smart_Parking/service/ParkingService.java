package com.example.demo.Smart_Parking.service;

import com.example.demo.Smart_Parking.model.Member;
import com.example.demo.Smart_Parking.model.MemberType;
import com.example.demo.Smart_Parking.model.ParkingLot;
import com.example.demo.Smart_Parking.model.Vehicle;
import com.example.demo.Smart_Parking.repository.MemberRepository;
import com.example.demo.Smart_Parking.repository.ParkingLotRepository;
import com.example.demo.Smart_Parking.repository.VehicleRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.List;
import java.util.Optional;



@Service
public class ParkingService implements IParkingService{

  private VehicleRepository vehicleRepository;
  private MemberRepository memberRepository;
  private ParkingLotRepository parkingLotRepository;

  @Autowired
  public ParkingService(VehicleRepository vehicleRepository, MemberRepository memberRepository,
      ParkingLotRepository parkingLotRepository) {
    this.vehicleRepository = vehicleRepository;
    this.memberRepository = memberRepository;
    this.parkingLotRepository = parkingLotRepository;
  }

   //Customer - Park a vehicle by license plate
  @Override
  public Map<String, Object> parkVehicle(String licensePlate) {
    Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findFirstByParkingPositionsContains(false);

    Map<String, Object> response = new HashMap<>();

    if (optionalParkingLot.isEmpty()) {
      response.put("status", "failure");
      response.put("message", "No available parking spots. ");
    }

    ParkingLot parkingLot = optionalParkingLot.get();
    Integer parkingPosition = parkingLot.findAvailablePositionAndPark();

    parkingLotRepository.save(parkingLot);

    //
    Vehicle vehicle = new Vehicle();
    vehicle.setLicensePlate(licensePlate);

    vehicle.setArrivalTime(LocalDateTime.now());
    vehicle.setParkingPosition(parkingPosition);

    vehicleRepository.save(vehicle);

    response.put("status", "success");
    response.put("message", "Vehicle parked successfully. ");
    return response;
  }

  @Override
  public Map<String, Object> processPayment(String licensePlate) {
    Optional<Vehicle> optionalVehicle = vehicleRepository.findByLicensePlate(licensePlate);

    Map<String, Object> response = new HashMap<>();

    if (optionalVehicle.isPresent()) {
      Vehicle vehicle = optionalVehicle.get();

      LocalDateTime startTime = vehicle.getArrivalTime();
      LocalDateTime endTime = LocalDateTime.now();

      Duration duration = Duration.between(startTime, endTime);
      float parkingFee = ParkingFeeCalculator.calculateParkingFee(duration);

      Optional<Member> optionalMember = memberRepository.findByLicensePlate(licensePlate);
      boolean isMember = optionalMember.isPresent();

      String paymentConfirmation;
      if (isMember || parkingFee != 0.0f) {
        paymentConfirmation = "Payment successful!";
      } else {
        paymentConfirmation = "Payment successful!";
      }

      if ("Payment successful!".equals(paymentConfirmation) || "Payment not required.".equals(paymentConfirmation)) {
        vehicle.setPaymentTime(LocalDateTime.now());
        vehicleRepository.save(vehicle);

        String formattedDuration = formatDuration(duration);

        response.put("status", "success");
        response.put("duration", formattedDuration);
        response.put("fee", parkingFee);
        response.put("message", paymentConfirmation);
      } else {
        response.put("status", "failure");
        response.put("message", "Payment processing failed. Please try again. ");
      }
    } else {
      response.put("status", "failure");
      response.put("message", "Vehicle with this license plate not found. ");
    }
    return response;

  }

  @Override
  public Map<String, Object> processToLeave(String licensePlate) {
    Optional<Vehicle> optionalVehicle = vehicleRepository.findByLicensePlate(licensePlate);

    Map<String, Object> response = new HashMap<>();

    if (optionalVehicle.isPresent()) {
      Vehicle vehicle = optionalVehicle.get();

      Optional<Member> optionalMember = memberRepository.findByLicensePlate(licensePlate);
      boolean isMember = optionalMember.isPresent();

      LocalDateTime startTime;
      if (vehicle.getPaymentTime() != null) {
        startTime = vehicle.getPaymentTime();
      } else {
        startTime = vehicle.getArrivalTime();
      }

      LocalDateTime endTime = LocalDateTime.now();
      Duration duration = java.time.Duration.between(startTime, endTime);
      float parkingFee = ParkingFeeCalculator.calculateParkingFee(duration);

      String paymentConfirmation;
      if (isMember || parkingFee == 0.0f) {
        paymentConfirmation = "Payment not required.";
      } else {
        //mimic the payment is successful
        paymentConfirmation = "Payment successful!";
      }

      if ("Payment successful!".equals(paymentConfirmation) || "Payment not required.".equals(paymentConfirmation)) {
        vehicle.setLeaveTime(LocalDateTime.now());
        vehicleRepository.save(vehicle);

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findFirstByParkingPositionsContains(false);

        if (optionalParkingLot.isPresent()) {
          ParkingLot parkingLot = optionalParkingLot.get();

          Integer parkingSpotIndex = vehicle.getParkingPosition();
          if (parkingSpotIndex != null) {
            parkingLot.getParkingPositions().set(parkingSpotIndex, false);
          }

          parkingLotRepository.save(parkingLot);
        }
        vehicleRepository.delete(vehicle);
        response.put("status", "success");
        response.put("message", paymentConfirmation);

      } else {
        response.put("status", "failure");
        response.put("message", "Payment processing failed. Please try again.");
      }
    } else {
      response.put("status", "failure");
      response.put("message", "Vehicle with this license plate not found. ");
    }
    return response;
  }


  private String formatDuration(Duration duration) {
    long hours = duration.toHours();
    long minutes = duration.toMinutes() % 60;
    long seconds = duration.getSeconds() % 60;

    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  @Override
  public List<Vehicle> getAllVehicles() {
    return vehicleRepository.findAll();
  }

  @Override
  public Map<String, Object> addMember(String licensePlate, MemberType memberType) {
    Member member = new Member();
    member.setLicensePlate(licensePlate);
    member.setMemberType(memberType);
    member.setMemberStartTime(LocalDateTime.now());

    switch (memberType) {
      case YEARLY:
        member.setMemberEndTime(LocalDateTime.now().plusYears(1));
        break;
      case MONTHLY:
        member.setMemberEndTime(LocalDateTime.now().plusMonths(1));
        break;
      case WEEKLY:
        member.setMemberEndTime(LocalDateTime.now().plusWeeks(1));
        break;
      default:
        break;
    }

    memberRepository.save(member);
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("terminate", member.getMemberEndTime());
    response.put("message", "Member added");

    return response;
  }


  // Delete a member manually
  @Override
  public Map<String, Object> deleteMember(String licensePlate) {
    Optional<Member> memberOptional = memberRepository.findByLicensePlate(licensePlate);
    Map<String, Object> response = new HashMap<>();

    if (memberOptional.isPresent()) {
      memberRepository.delete(memberOptional.get());
      response.put("status", "success");
      response.put("message", "Membership deleted.");
    } else {
      response.put("status", "failure");
      response.put("message", "Member not found with this license plate");
    }
    return response;
  }


  @Override
  @Scheduled(cron = "0 0 0 * * ?")
  public void checkMembershipEndTimes() {
    LocalDateTime now = LocalDateTime.now();
    List<Member> expiredMembers = memberRepository.findByMemberEndTimeBefore(now);

    for (Member member : expiredMembers) {
      memberRepository.delete(member);
      System.out.println("Member with ID " + member.getId() + " has expired.");
    }
  }

  @Override
  public List<Member> getAllMember() {
    return memberRepository.findAll();
  }


}


