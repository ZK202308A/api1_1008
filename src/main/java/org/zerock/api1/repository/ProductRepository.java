package org.zerock.api1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.api1.domain.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
