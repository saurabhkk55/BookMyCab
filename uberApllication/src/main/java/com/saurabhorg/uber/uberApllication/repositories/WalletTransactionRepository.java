package com.saurabhorg.uber.uberApllication.repositories;

import com.saurabhorg.uber.uberApllication.entities.WalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransactionEntity, Long> {
}
