package com.wcc.repositories;

import com.wcc.entity.Ukpostcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UkpostcodeRepository extends JpaRepository<Ukpostcode, Integer> {
    Optional<Ukpostcode> findByPostcode(String key);
}
