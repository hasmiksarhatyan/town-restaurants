package am.itspace.townrestaurantscommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double totalAmount;

    private LocalDateTime paidAt;

    @CreationTimestamp
    private LocalDateTime paymentCreateDate;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToOne
    private Order order;

    @ManyToOne
    private User user;
}