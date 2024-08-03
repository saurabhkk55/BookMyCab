package com.saurabhorg.uber.uberApllication.entities.enums;

public enum Role {
    /**
     * Represents an administrative user who can onboard drivers
     * and manage overall system settings and operations.
     */
    ADMIN,

    /**
     * Represents a customer who can book rides through the application.
     * This user type is typically referred to as a rider.
     */
    RIDER,

    /**
     * Represents a driver who accepts and fulfills ride requests from riders.
     * This user type is responsible for providing transportation services.
     */
    DRIVER
}

