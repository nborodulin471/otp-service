package ru.otp.service.service.distribution;

import ru.otp.service.model.entity.DeliveryType;

public interface DeliveryService {
    void send(String message, String destination);

    DeliveryType getDeliveryType();
}
