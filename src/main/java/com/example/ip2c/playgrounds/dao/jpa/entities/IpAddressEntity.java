package com.example.ip2c.playgrounds.dao.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Entity
@Table(name = "ip_addresses")
@ToString
@Data
public class IpAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ip_addresses_id_seq_gen")
    @SequenceGenerator(name = "ip_addresses_id_seq_gen", sequenceName = "ip_addresses_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private CountryEntity countryEntity;

    private String ip;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
