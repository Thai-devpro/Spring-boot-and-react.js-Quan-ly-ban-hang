package com.javaweb.springjwt.repository;

import com.javaweb.springjwt.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findAllByUserIdAndProductId(Long userId, Long productId);

    List<Cart> findAllByUserId(Long userId);

    Cart findByProductId(Long productId);

    void deleteByIdAndUserId(Long id, Long userId);

}
