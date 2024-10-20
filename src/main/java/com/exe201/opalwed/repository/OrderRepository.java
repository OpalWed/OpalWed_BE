package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
