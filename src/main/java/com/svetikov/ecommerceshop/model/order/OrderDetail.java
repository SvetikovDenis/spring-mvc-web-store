package com.svetikov.ecommerceshop.model.order;

import com.svetikov.ecommerceshop.model.user.Address;
import com.svetikov.ecommerceshop.model.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "order_total")
    private Integer orderTotal;

    @ManyToOne
    @JoinColumn(name ="shipping_id")
    private Address shipping;

    @ManyToOne
    @JoinColumn(name ="billing_id")
    private Address billing;

    @OneToMany(mappedBy="orderDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "order_count")
    private Integer orderCount;

    @Column(name="order_date")
    private Timestamp orderDate;

}
