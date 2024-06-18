package com.example.demo.Smart_Parking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
public class Member {

  @Id
  @SequenceGenerator(
      name = "member_sequence",
      sequenceName = "member_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "member_sequence"
  )
  private Long id;

  private String licensePlate;
  private MemberType memberType;
  private LocalDateTime memberStartTime;
  private LocalDateTime memberEndTime;

  public Member() {
  }

  public Member(String licensePlate, MemberType memberType, LocalDateTime memberStartTime,
      LocalDateTime memberEndTime) {
    this.licensePlate = licensePlate;
    this.memberType = memberType;
    this.memberStartTime = memberStartTime;
    this.memberEndTime = memberEndTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }

  public MemberType getMemberType() {
    return memberType;
  }

  public void setMemberType(MemberType memberType) {
    this.memberType = memberType;
  }

  public LocalDateTime getMemberStartTime() {
    return memberStartTime;
  }

  public void setMemberStartTime(LocalDateTime memberStartTime) {
    this.memberStartTime = memberStartTime;
  }

  public LocalDateTime getMemberEndTime() {
    return memberEndTime;
  }

  public void setMemberEndTime(LocalDateTime memberEndTime) {
    this.memberEndTime = memberEndTime;
  }
}