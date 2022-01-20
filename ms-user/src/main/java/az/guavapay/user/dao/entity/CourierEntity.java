package az.guavapay.user.dao.entity;

import az.guavapay.user.model.enums.CourierStatus;
import az.guavapay.user.model.enums.CourierTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "courier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "courier_type")
    @Enumerated(EnumType.STRING)
    private CourierTypes courierType;

    @Column(name = "courier_company_name")
    private String courierCompanyName;

    private String description;

    @OneToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @Column(name = "courier_status")
    @Enumerated(EnumType.ORDINAL)
    private CourierStatus courierStatus;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;
}
