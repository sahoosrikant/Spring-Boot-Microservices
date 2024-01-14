package com.microservice.OrderService.model;

import com.microservice.OrderService.model.OrderLineItems;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name ="OrderApplied")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderlineitemslist", referencedColumnName = "id")
    private List<OrderLineItems> orderLineItemsList;
}
