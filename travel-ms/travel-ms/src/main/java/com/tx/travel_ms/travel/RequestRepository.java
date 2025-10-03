package com.tx.travel_ms.travel;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    List<Request> findByEmployeeId(Long employeeId);
    @Transactional
    void deleteAllByEmployeeId(Long employeeId);
}
