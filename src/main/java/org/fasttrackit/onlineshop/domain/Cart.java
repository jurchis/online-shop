package org.fasttrackit.onlineshop.domain;

import javax.persistence.*;

@Entity
public class Cart {

    @Id
    private Long id;

    //one-to-one relationship
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
