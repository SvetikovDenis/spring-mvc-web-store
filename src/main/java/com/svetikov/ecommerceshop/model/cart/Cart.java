package com.svetikov.ecommerceshop.model.cart;

import com.svetikov.ecommerceshop.model.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn( name = "user_id")
    @NotNull
    private User user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<CartLine> cartLines;

}
