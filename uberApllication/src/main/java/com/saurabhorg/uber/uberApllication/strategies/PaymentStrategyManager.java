package com.saurabhorg.uber.uberApllication.strategies;

import com.saurabhorg.uber.uberApllication.entities.enums.PaymentMethod;
import com.saurabhorg.uber.uberApllication.strategies.impl.CashPaymentStrategy;
import com.saurabhorg.uber.uberApllication.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
