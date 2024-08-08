package com.saurabhorg.uber.uberApllication.strategies;

import com.saurabhorg.uber.uberApllication.entities.PaymentEntity;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(PaymentEntity payment);
}

