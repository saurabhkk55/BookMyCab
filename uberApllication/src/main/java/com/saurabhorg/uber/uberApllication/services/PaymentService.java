package com.saurabhorg.uber.uberApllication.services;

import com.saurabhorg.uber.uberApllication.entities.PaymentEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(RideEntity ride);

    PaymentEntity createNewPayment(RideEntity ride);

    void updatePaymentStatus(PaymentEntity payment, PaymentStatus status);
}
