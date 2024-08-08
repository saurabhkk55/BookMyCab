package com.saurabhorg.uber.uberApllication.strategies.impl;

//Rider -> 100
//Driver -> 70 Deduct 30Rs from Driver's wallet

import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.PaymentEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.PaymentStatus;
import com.saurabhorg.uber.uberApllication.entities.enums.TransactionMethod;
import com.saurabhorg.uber.uberApllication.repositories.PaymentRepository;
import com.saurabhorg.uber.uberApllication.services.WalletService;
import com.saurabhorg.uber.uberApllication.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(PaymentEntity payment) {
        DriverEntity driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}

//10 ratingsCount -> 4.0
//new rating 4.6
//updated rating
//new rating 44.6/11 -> 4.05
