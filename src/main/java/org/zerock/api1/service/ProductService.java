package org.zerock.api1.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api1.domain.ProductEntity;
import org.zerock.api1.domain.ProductStatus;
import org.zerock.api1.dto.PageRequestDTO;
import org.zerock.api1.dto.PageResponseDTO;
import org.zerock.api1.dto.ProductListDTO;
import org.zerock.api1.dto.ProductRegisterDTO;
import org.zerock.api1.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO){

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Page<ProductEntity> result = productRepository.findAll(pageable);

        java.util.List<ProductListDTO> dtoList = result.get().map(productEntity ->  {

            ProductListDTO dto = ProductListDTO.builder()
                    .pno(productEntity.getPno())
                    .pname(productEntity.getPname())
                    .price(productEntity.getPrice())
                    .pdesc(productEntity.getPdesc())
                    .img1(productEntity.getImg1())
                    .img2(productEntity.getImg2())
                    .img3(productEntity.getImg3())
                    .status(productEntity.getStatus())
                    .createdDate(productEntity.getCreatedDate())
                    .build();
            return dto;
        }).collect(Collectors.toUnmodifiableList());

        long total = result.getTotalElements();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(total)
                .build();
    }

    public Long register(ProductRegisterDTO dto){

        ProductEntity productEntity = ProductEntity.builder()
                .pname(dto.getPname())
                .pdesc(dto.getPdesc())
                .price(dto.getPrice())
                .status(ProductStatus.SALE)
                .build();
        List<String> uploadedFileNames = dto.getUploadFileNames();

        //Don't do this!! Stupid!!
        if(uploadedFileNames.size() >= 1) {
            if (uploadedFileNames.get(0) != null) {
                productEntity.changeImg1(uploadedFileNames.get(0));
            }
        }
        if(uploadedFileNames.size() >= 2) {
            if (uploadedFileNames.get(1) != null) {
                productEntity.changeImg2(uploadedFileNames.get(1));
            }
        }
        if(uploadedFileNames.size() >= 3) {
            if(uploadedFileNames.get(2) != null){
                productEntity.changeImg3(uploadedFileNames.get(2));
            }
        }



        productRepository.save(productEntity);

        return productEntity.getPno();

    }

}













