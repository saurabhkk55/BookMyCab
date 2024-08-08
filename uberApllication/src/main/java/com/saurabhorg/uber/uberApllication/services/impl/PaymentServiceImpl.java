package com.saurabhorg.uber.uberApllication.services.impl;

import com.saurabhorg.uber.uberApllication.entities.PaymentEntity;
import com.saurabhorg.uber.uberApllication.entities.RideEntity;
import com.saurabhorg.uber.uberApllication.entities.enums.PaymentStatus;
import com.saurabhorg.uber.uberApllication.exceptions.ResourceNotFoundException;
import com.saurabhorg.uber.uberApllication.repositories.PaymentRepository;
import com.saurabhorg.uber.uberApllication.services.PaymentService;
import com.saurabhorg.uber.uberApllication.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(RideEntity rideEntity) {
        PaymentEntity paymentEntity = paymentRepository.findByRide(rideEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ride with id: " + rideEntity.getId()));
        paymentStrategyManager.paymentStrategy(paymentEntity.getPaymentMethod()).processPayment(paymentEntity);
    }

    @Override
    public PaymentEntity createNewPayment(RideEntity rideEntity) {
        PaymentEntity payment = PaymentEntity.builder()
                .ride(rideEntity)
                .paymentMethod(rideEntity.getPaymentMethod())
                .amount(rideEntity.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(PaymentEntity payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
