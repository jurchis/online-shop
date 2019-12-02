package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Long wrapper class
public interface ProductRepository extends JpaRepository<Product, Long> {

    //method name is affecting functionality but not the name of the parameters
    Page<Product> findByNameContaining(String partialName, Pageable pageable);

    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(
            String partialName, int minQuantity, Pageable pageable
    );

    //below queries only for reading data in below format
    //JPQL Syntax (java persistence query language)
//    @Query("SELECT product FROM Product product WHERE name LIKE '%:partialName%'")
    //native MySQL query
    //Escape characters in MySQL ``
    @Query(value="SELECT * FROM product WHERE `name` LIKE '%?0%'", nativeQuery=true)

    Page<Product> findByPartialName(String partialName, Pageable pageable);

}
