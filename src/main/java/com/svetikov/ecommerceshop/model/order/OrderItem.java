package com.svetikov.ecommerceshop.model.order;

import com.svetikov.ecommerceshop.model.product.Product;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private OrderDetail orderDetail;

    @Column (name = "buying_price")
    @NotNull
    private Integer buyingPrice;

    @Column (name = "product_count")
    private Integer productCount;

    private Integer total;


}
