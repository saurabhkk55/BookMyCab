package com.saurabhorg.uber.uberApllication.strategies.impl;

//Rider had 232, Driver had 500
//Ride cost is 100, commission = 30
//Rider -> 232-100 = 132
//Driver -> 500 + (100 - 30) = 570

import com.saurabhorg.uber.uberApllication.entities.DriverEntity;
import com.saurabhorg.uber.uberApllication.entities.PaymentEntity;
import com.saurabhorg.uber.uberApllication.entities.RiderEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.PaymentStatus;
import com.saurabhorg.uber.uberApllication.entities.enums.TransactionMethod;
import com.saurabhorg.uber.uberApllication.repositories.PaymentRepository;
import com.saurabhorg.uber.uberApllication.services.WalletService;
import com.saurabhorg.uber.uberApllication.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(PaymentEntity payment) {
        DriverEntity driver = payment.getRide().getDriver();
        RiderEntity rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),
                driversCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}

