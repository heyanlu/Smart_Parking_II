package com.example.demo.Smart_Parking.repository;

import com.example.demo.Smart_Parking.model.Member;
import com.example.demo.Smart_Parking.model.Vehicle;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByLicensePlate(String licensePlate);
  List<Member> findByMemberEndTimeBefore(LocalDateTime dateTime);

}
