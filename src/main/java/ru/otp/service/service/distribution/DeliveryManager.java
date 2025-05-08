package ru.otp.service.service.distribution;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.otp.service.model.entity.DeliveryType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeliveryManager {

    private final Map<DeliveryType, DeliveryService> deliveryServiceMap;

    @Autowired
    public DeliveryManager(List<DeliveryService> services) {
        this.deliveryServiceMap = services.stream()
                .collect(Collectors.toMap(DeliveryService::getDeliveryType, service -> service));
    }

    public DeliveryService getDeliveryService(DeliveryType deliveryType) {
        return deliveryServiceMap.get(deliveryType);
    }

}
