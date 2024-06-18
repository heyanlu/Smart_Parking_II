package com.example.demo.Smart_Parking.service;


import com.example.demo.Smart_Parking.model.Member;
import com.example.demo.Smart_Parking.model.MemberType;
import com.example.demo.Smart_Parking.model.ParkingLot;
import com.example.demo.Smart_Parking.model.Vehicle;
import com.example.demo.Smart_Parking.model.VehicleType;
import com.example.demo.Smart_Parking.repository.MemberRepository;
import com.example.demo.Smart_Parking.repository.ParkingLotRepository;
import com.example.demo.Smart_Parking.repository.VehicleRepository;
import com.example.demo.Smart_Parking.service.ParkingFeeCalculator;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

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

  // Customer - Park a vehicle by license plate
  public String parkVehicle(Vehicle vehicle) {
    Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findFirstByParkingPositionsContains(false);
    if (optionalParkingLot.isEmpty()) {
      throw new RuntimeException("No available parking spots");
    }

    ParkingLot parkingLot = optionalParkingLot.get();
    Integer parkingPosition = parkingLot.findAvailablePositionAndPark();

    parkingLotRepository.save(parkingLot);

    vehicle.setArrivalTime(LocalDateTime.now());
    vehicle.setParkingPosition(parkingPosition);

    vehicleRepository.save(vehicle);

    return "Welcome, " + vehicle.getLicensePlate() + " . Your parking position: " + parkingPosition;

  }

  public String processPayment(String licensePlate) {
    Optional<Vehicle> optionalVehicle = vehicleRepository.findByLicensePlate(licensePlate);
    if (optionalVehicle.isPresent()) {
      Vehicle vehicle = optionalVehicle.get();
      LocalDateTime startTime = vehicle.getArrivalTime();
      LocalDateTime endTime = LocalDateTime.now();

      Duration duration = Duration.between(startTime, endTime);
      float parkingFee = ParkingFeeCalculator.calculateParkingFee(duration);

      Optional<Member> optionalMember = memberRepository.findByLicensePlate(licensePlate);
      boolean isMember = optionalMember.isPresent();

      String paymentConfirmation;
      if (isMember) {
        parkingFee = 0.0f;
        paymentConfirmation = "Payment successful!";
      } else if (parkingFee != 0.0f) {
        paymentConfirmation = "Payment successful!";
      } else {
        paymentConfirmation = "Payment not required.";
      }

      if ("Payment successful!".equals(paymentConfirmation) || "Payment not required.".equals(paymentConfirmation)) {
        vehicle.setPaymentTime(LocalDateTime.now());
        vehicleRepository.save(vehicle);

        return paymentConfirmation + " Please leave within 20 minutes.";
      } else {
        throw new RuntimeException("Payment processing failed. Please try again.");
      }
    } else {
      throw new RuntimeException("Vehicle not found with license plate: " + licensePlate);
    }
  }


  public String processToLeave(String licensePlate) {
    Optional<Vehicle> optionalVehicle = vehicleRepository.findByLicensePlate(licensePlate);
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
      if (isMember) {
        parkingFee = 0.0f;
        paymentConfirmation = "Payment successful!";
      } else if (parkingFee != 0.0f) {
        paymentConfirmation = "Payment successful!";
      } else {
        paymentConfirmation = "Payment not required.";
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

        String formattedDuration = formatDuration(duration);

        return "Parking Duration: " + formattedDuration + ". " + paymentConfirmation + " See you next time!";
      } else {
        throw new RuntimeException("Payment processing failed. Please try again.");
      }
    } else {
      throw new RuntimeException("Vehicle not found with license plate: " + licensePlate);
    }
  }


  private String formatDuration(Duration duration) {
    long hours = duration.toHours();
    long minutes = duration.toMinutes() % 60;
    long seconds = duration.getSeconds() % 60;

    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }


  // Admin - Get all vehicles
  public List<Vehicle> getAllVehicles() {
    return vehicleRepository.findAll();
  }

  // Admin - Add a member
  public void addMember(String licensePlate, MemberType memberType) {
    Member member = new Member();
    member.setLicensePlate(licensePlate);
    member.setMemberType(memberType);
    member.setMemberStartTime(LocalDateTime.now());

    if (memberType == MemberType.YEARLY) {
      member.setMemberEndTime(LocalDateTime.now().plusYears(1));
    } else if (memberType == MemberType.MONTHLY) {
      member.setMemberEndTime(LocalDateTime.now().plusMonths(1));
    } else if (memberType == MemberType.WEEKLY) {
      member.setMemberEndTime(LocalDateTime.now().plusWeeks(1));
    }

    memberRepository.save(member);
  }


  // Delete a member manually
  public void deleteMember(String licensePlate) {
    Optional<Member> memberOptional = memberRepository.findByLicensePlate(licensePlate);
    if (memberOptional.isPresent()) {
      memberRepository.delete(memberOptional.get());
    } else {
      throw new RuntimeException("Member not found with license plate: " + licensePlate);
    }
  }


  // Delete a member automatically
  @Scheduled(cron = "0 0 0 * * ?")
  public void checkMembershipEndTimes() {
    LocalDateTime now = LocalDateTime.now();
    List<Member> expiredMembers = memberRepository.findByMemberEndTimeBefore(now);

    for (Member member : expiredMembers) {
      memberRepository.delete(member);
      System.out.println("Member with ID " + member.getId() + " has expired.");
    }
  }

}


