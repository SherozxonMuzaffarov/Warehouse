package com.warehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Output {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp timestamp;

    private  String factureNumber;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne
    private WareHouse wareHouse;

    @ManyToOne
    private Clients client;

    @ManyToOne
    private Currency currency;


}

