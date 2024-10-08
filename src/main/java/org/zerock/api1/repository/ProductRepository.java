package org.zerock.api1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.api1.domain.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByPnameContaining(String keyword, Pageable pageable);

    @Query("""
        select p from ProductEntity p 
        where 
        p.pname like %:keyword% 
        and
        p.status = 0
        """)
    Page<ProductEntity> getByPname(@Param("keyword") String keyword,
                                   Pageable pageable);


}
