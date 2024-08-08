package com.saurabhorg.uber.uberApllication.services.impl;


import com.saurabhorg.uber.uberApllication.entities.*;
import com.saurabhorg.uber.uberApllication.entities.enums.TransactionMethod;
import com.saurabhorg.uber.uberApllication.entities.enums.TransactionType;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.repositories.WalletRepository;
import com.saurabhorg.uber.uberApllication.services.WalletService;
import com.saurabhorg.uber.uberApllication.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public WalletEntity addMoneyToWallet(UserEntity user, Double amount, String transactionId, RideEntity ride, TransactionMethod transactionMethod) {
        WalletEntity wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransactionEntity walletTransaction = WalletTransactionEntity.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public WalletEntity deductMoneyFromWallet(UserEntity user, Double amount,
                                              String transactionId, RideEntity ride,
                                              TransactionMethod transactionMethod) {
        WalletEntity wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransactionEntity walletTransaction = WalletTransactionEntity.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

//        wallet.getTransactions().add(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public WalletEntity findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: "+walletId));
    }

    @Override
    public WalletEntity createNewWallet(UserEntity user) {
        WalletEntity wallet = new WalletEntity();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public WalletEntity findByUser(UserEntity user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user with id: "+user.getUserId()));
    }
}

