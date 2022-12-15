package am.itspace.townrestaurantscommon.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cardNumber;

    private String cardHolder;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cardExpiresAt;

    private String cvv;

    @ManyToOne
    private User user;
}
