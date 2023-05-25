package com.james.coindesk.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "currency_name")
@Entity
public class CurrencyNameEntity {
    @Id
    @Column(name="code", nullable=false)
    private String code;

    @Column(name="name", length=100, nullable=false, unique=false)
    private String name;
}
