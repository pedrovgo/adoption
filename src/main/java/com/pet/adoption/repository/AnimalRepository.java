package com.pet.adoption.repository;

import com.pet.adoption.enums.Status;
import com.pet.adoption.po.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, String> {
    @Query(value="SELECT a.id, a.name, a.description, a.image, a.category, a.status, a.created_at, a.updated_at " +
            "FROM Animal as a " +
            "WHERE (:search is null OR a.name like '%'||:search||'%' OR a.description like '%'||:search||'%')" +
            " AND (:category is null OR a.category like '%'||:category||'%')" +
            " AND (:status is null OR a.status = :status)" +
            " AND (:createdAtStart is null OR a.created_at >= :createdAtStart)" +
            " AND (:createdAtEnd is null OR a.created_at <= :createdAtEnd) ORDER BY a.name",
            countQuery = "SELECT COUNT(1) FROM Animal as a " +
            "WHERE (:search is null OR a.name like '%'||:search||'%' OR a.description like '%'||:search||'%')" +
            " AND (:category is null OR a.category like '%'||:category||'%')" +
            " AND (:status is null OR a.status = :status)" +
            " AND (:createdAtStart is null OR a.created_at >= :createdAtStart)" +
            " AND (:createdAtEnd is null OR a.created_at <= :createdAtEnd) ", nativeQuery = true)
    Page<Animal> search(
            @Param("search") String search,
            @Param("category") String category,
            @Param("status") String status,
            @Param("createdAtStart") LocalDateTime createdAtStart,
            @Param("createdAtEnd") LocalDateTime createdAtEnd,
            Pageable pageable);
}
