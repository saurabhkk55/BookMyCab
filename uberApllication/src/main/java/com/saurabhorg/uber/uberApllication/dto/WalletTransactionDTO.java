package com.saurabhorg.uber.uberApllication.dto;

import com.saurabhorg.uber.uberApllication.entities.enums.TransactionMethod;
import com.saurabhorg.uber.uberApllication.entities.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDTO {
    private Long id;

    private String transactionId;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private LocalDateTime timeStamp;

    private WalletDTO wallet;

    private RideDTO ride;
}
