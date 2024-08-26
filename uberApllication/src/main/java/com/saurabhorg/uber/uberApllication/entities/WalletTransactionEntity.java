package com.saurabhorg.uber.uberApllication.entities;

import com.saurabhorg.uber.uberApllication.entities.enums.TransactionMethod;
import com.saurabhorg.uber.uberApllication.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wallet_transaction", indexes = {
        @Index(name = "idx_wallet_transaction_wallet", columnList = "wallet_id"),
        @Index(name = "idx_wallet_transaction_ride", columnList = "ride_id")
})
public class WalletTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_transaction_id")
    private Long id;

    private String transactionId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    @CreationTimestamp
    private LocalDateTime timeStamp;

    private String creditTo;

    private String debitFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    // @OneToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    private RideEntity ride;
}
