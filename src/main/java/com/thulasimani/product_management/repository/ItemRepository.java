package com.thulasimani.product_management.repository;

import com.thulasimani.product_management.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByProductId(Long productId);
    boolean existsByProductId(Long productId);

}
