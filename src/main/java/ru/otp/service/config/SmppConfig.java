package ru.otp.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransmitter;

@Configuration
public class SmppConfig {

    @Bean
    @ConfigurationProperties(prefix = "smpp")
    public SmppProperties smppProperties() {
        return new SmppProperties();
    }

    @Bean
    public Session smppSession(SmppProperties properties) throws Exception {
        var connection = new TCPIPConnection(
                properties.getHost(),
                properties.getPort()
        );

        connection.setConnectionTimeout(properties.getConnectionTimeout());
        connection.setReceiveTimeout(properties.getReceiveTimeout());
        connection.setCommsTimeout(properties.getCommsTimeout());

        var session = new Session(connection);
        BindRequest bindRequest = new BindTransmitter();
        bindRequest.setSystemId(properties.getSystemId());
        bindRequest.setPassword(properties.getPassword());
        bindRequest.setSystemType(properties.getSystemType());
        bindRequest.setInterfaceVersion((byte) 0x34);
        bindRequest.setAddressRange(properties.getSourceAddr());

        BindResponse response = session.bind(bindRequest);
        if (response.getCommandStatus() != 0) {
            throw new IllegalStateException("SMPP bind failed: " + response.getCommandStatus());
        }

        return session;
    }

    @Getter
    @Setter
    public static class SmppProperties {
        private String host;
        private int port;
        private String systemId;
        private String password;
        private String systemType;
        private String sourceAddr;
        private int connectionTimeout = 5000;
        private int receiveTimeout = 60000;
        private int commsTimeout = 10000;
    }
}