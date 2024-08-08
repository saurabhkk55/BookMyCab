package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.entities.UserEntity;
import com.saurabhorg.uber.uberApllication.entities.WalletEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.TransactionMethod;
import org.springframework.transaction.annotation.Transactional;

public interface WalletService {


    @Transactional
    WalletEntity addMoneyToWallet(UserEntity user, Double amount, String transactionId, RideEntity ride, TransactionMethod transactionMethod);


    @Transactional
    WalletEntity deductMoneyFromWallet(UserEntity user, Double amount,
                                       String transactionId, RideEntity ride,
                                       TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    WalletEntity findWalletById(Long walletId);

    WalletEntity createNewWallet(UserEntity user);

    WalletEntity findByUser(UserEntity user);
}
