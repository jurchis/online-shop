package org.fasttrackit.onlineshop.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {

    @Id
    private Long id;

    //one-to-one relationship
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Customer customer;

    //(cascade = CascadeType.MERGE) to delete only from cart not from the entire DB when deleting from cart
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    public void addToCart(Product product) {
        //adding the received product into the cart- the current cart
        products.add(product);

        //"this" represents aways the current object, adding the current cart to the cart set of the received product
        product.getCarts().add(this);
    }

    public void removeFromCart(Product product) {
        products.remove(product);

        product.getCarts().remove(this);

    }

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return id.equals(cart.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
