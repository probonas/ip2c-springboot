package com.example.ip2c.playgrounds.dao.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

@Entity
@Table(name = "countries")
@ToString
@Data
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_id_seq_gen")
    @SequenceGenerator(name = "countries_id_seq_gen", sequenceName = "countries_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Column(name = "two_letter_code")
    private String twoLetterCode;

    @Column(name = "three_letter_code")
    private String threeLetterCode;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
