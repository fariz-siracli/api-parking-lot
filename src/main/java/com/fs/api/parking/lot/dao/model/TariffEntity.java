package com.fs.api.parking.lot.dao.model;

import com.fs.api.parking.lot.model.enums.TariffStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tariff", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TariffStatus paymentStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
