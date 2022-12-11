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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private Double price;

    @ManyToOne
    private ProductCategory productCategory;

    @ManyToOne
    private Restaurant restaurant;

    @ElementCollection
    private List<String> pictures;

    @ManyToOne
    private User user;
}

