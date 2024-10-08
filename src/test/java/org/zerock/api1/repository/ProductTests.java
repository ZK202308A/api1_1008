package org.zerock.api1.repository;



import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api1.domain.ProductEntity;
import org.zerock.api1.domain.ProductStatus;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@DataJpaTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTests {

    @Autowired
    ProductRepository repo;

    @Disabled
    @Test
    @Transactional
    @Commit
    public void insertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            ProductEntity entity = ProductEntity.builder()
                    .pname("Product " + i)
                    .pdesc("Product desc " + i)
                    .price(i)
                    .build();

            repo.save(entity);
        });
    }

    @Test
    public void testRead() {
        Long pno = 50L;

        Optional<ProductEntity> result = repo.findById(pno);

        ProductEntity entity = result.orElse(null);

        log.info(entity);

    }

    @Test
    @Transactional
    @Commit
    public void testUpdate() {

        Long pno = 50L;

        Optional<ProductEntity> result = repo.findById(pno);

        ProductEntity entity = result.orElse(null);

        entity.changeStatus(ProductStatus.NOT_SALE);

    }

    @Test
    @Transactional
    @Commit
    public void insertOne() {

        ProductEntity entity = ProductEntity.builder()
                .pname("Test Product ")
                .pdesc("Test Product desc ")
                .price(3000)
                .build();

        repo.save(entity);

        log.info("-----------------------");
        log.info(entity.getPno());
    }

    @Test
    @Transactional
    public void testPage() {

        int page = 0;
        int size = 20;

        Pageable pageable =
                PageRequest.of(page,size, Sort.by("pno").descending());

        Page<ProductEntity> result = repo.findAll(pageable);

        log.info(result.getTotalElements());
        log.info(result.getTotalPages());
        log.info(result.getNumber());
        log.info(result.getSize());

        result.get().forEach( productEntity ->  log.info(productEntity));


    }

    @Test
    public void testLike() {

        int page = 0;
        int size = 5;

        Pageable pageable =
                PageRequest.of(page,size, Sort.by("pno").descending());

        String keyword = "1";

        Page<ProductEntity> result = repo.findByPnameContaining(keyword, pageable);

        result.get().forEach( productEntity -> log.info(productEntity));

    }

    @Test
    public void testLike2() {

        int page = 0;
        int size = 5;

        Pageable pageable =
                PageRequest.of(page,size, Sort.by("pno").descending());

        String keyword = "1";

        Page<ProductEntity> result = repo.getByPname(keyword, pageable);

        result.get().forEach( productEntity -> log.info(productEntity));

    }
}

