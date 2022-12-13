package am.itspace.townrestaurantscommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime reservedAt;

    private LocalDate reservedDate;

    private LocalTime reservedTime;

    private int peopleCount;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private ReserveStatus status;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private User user;
}