package ru.otp.service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Операция защищаемая OTP кодом.
 *
 * @implNote Данный класс это пример операции т.к данная сущность в задании не описана.
 */
@Data
@Entity
@Table(name = "operation")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sum; // Сумма операции в копейках/центах

    @Column(nullable = false, length = 50)
    private String destination; // Получатель (счёт, карта, телефон и т.д.)

    @Column(nullable = false, length = 20)
    private String operationType; // Тип операции: TRANSFER, PAYMENT, WITHDRAWAL и т.д.

    @Column(nullable = false, length = 20)
    private String status = "PENDING"; // Статус: PENDING, COMPLETED, FAILED

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 50)
    private String initiator; // Инициатор операции (логин пользователя)

    @Column(length = 100)
    private String description; // Дополнительное описание

    @ManyToOne
    private User user;
}
