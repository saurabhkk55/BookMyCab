package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.entities.WalletTransactionEntity;
import com.saurabhorg.uber.uberApllication.repositories.WalletTransactionRepository;
import com.saurabhorg.uber.uberApllication.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransactionEntity walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

}
