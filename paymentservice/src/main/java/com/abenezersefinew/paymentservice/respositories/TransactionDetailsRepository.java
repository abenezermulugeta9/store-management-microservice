package com.abenezersefinew.paymentservice.respositories;

import com.abenezersefinew.paymentservice.entities.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
}
