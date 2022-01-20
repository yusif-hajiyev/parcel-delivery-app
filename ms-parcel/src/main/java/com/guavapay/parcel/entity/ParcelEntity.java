package com.guavapay.parcel.entity;

import com.guavapay.parcel.model.enums.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParcelEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parcel_code")
    private String parcelCode;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "courier_id")
    private Long courierId;

    private Integer quantity;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_from")
    private AddressEntity addressFrom;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_to")
    private AddressEntity addressTo;

    private String description;

    @Column(name = "parcel_status")
    @Enumerated(EnumType.ORDINAL)
    private ParcelStatus parcelStatus;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;
}
