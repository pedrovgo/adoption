package com.pet.adoption.repository;

import com.pet.adoption.po.DataLoader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataLoaderRepository extends JpaRepository<DataLoader, String> {
    Optional<DataLoader> findByFinishedFalse();
}
