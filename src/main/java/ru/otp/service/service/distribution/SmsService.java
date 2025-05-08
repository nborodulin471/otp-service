package ru.otp.service.service.distribution;

import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smpp.Session;
import org.smpp.SmppException;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.WrongLengthOfStringException;
import ru.otp.service.config.SmppConfig;
import ru.otp.service.exception.DeliveryException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService implements DeliveryService {

    private final Session smppSession;
    private final SmppConfig.SmppProperties properties;

    @PreDestroy
    public void destroy() {
        try {
            if (smppSession != null && smppSession.isBound()) {
                smppSession.unbind();
                smppSession.close();
            }
        } catch (Exception e) {
            log.error("Failed to unbind SMPP session", e);
        }
    }

    @Override
    public void send(String code, String destination, String template) {
        try {
            validateDestination(destination);
            var submitSM = createSubmitSm(code, destination, template);
            var response = smppSession.submit(submitSM);

            if (response.getCommandStatus() != 0) {
                throw new SmppException("SMPP submit failed: " + response.getCommandStatus());
            }

            log.info("SMS sent to {} with message ID: {}", destination, response.getMessageId());
        } catch (Exception e) {
            log.error("Failed to send SMS to {}", destination, e);
            throw new DeliveryException("SMS delivery failed", e);
        }
    }

    private SubmitSM createSubmitSm(String code, String destination, String template)
            throws WrongLengthOfStringException {

        var message = template.replace("{code}", code);
        var submitSM = new SubmitSM();
        submitSM.setSourceAddr(properties.getSourceAddr());
        submitSM.setDestAddr(destination);
        submitSM.setShortMessage(Arrays.toString(message.getBytes(StandardCharsets.UTF_16BE)));
        submitSM.setDataCoding((byte) 0x08);
        submitSM.setRegisteredDelivery((byte) 1);

        return submitSM;
    }

    private void validateDestination(String destination) {
        if (!destination.matches("^7\\d{10}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }
}

