package com.guavapay.parcel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String region;
    private Integer postalCode;
    private String country;
    private String city;
    private String phone;
    private String longitude;
    private String latitude;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
}
