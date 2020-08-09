package com.svetikov.ecommerceshop.model.cart;

import com.svetikov.ecommerceshop.model.product.Product;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cart_line" )
public class CartLine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @NotNull
    private Cart cart;

    @Column(name = "product_count")
    private Integer productCount;

    public CartLine() {
    }

    @Builder
    public CartLine(Long id, Product product, Cart cart, Integer productCount) {
        this.id = id;
        this.product = product;
        this.cart = cart;
        this.productCount = productCount;
    }
}
