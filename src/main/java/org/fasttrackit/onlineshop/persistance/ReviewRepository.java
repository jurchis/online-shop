package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //a query done by a nested property
    Page<Review> findByProductId(Long productId, Pageable pageable);
}