package am.itspace.townrestaurantscommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String email;

    private String phone;

    @ManyToOne
    private RestaurantCategory restaurantCategory;

    private Double deliveryPrice;

    @ElementCollection
    private List<String> pictures;

    @ManyToOne
    private User user;
}