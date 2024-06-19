package com.assessment.eventbookingsystem.repository;

import com.assessment.eventbookingsystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {
    Optional<Users> findUsersByEmail(String email);
}
