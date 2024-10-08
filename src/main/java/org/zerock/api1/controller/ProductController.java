package org.zerock.api1.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.api1.dto.PageRequestDTO;
import org.zerock.api1.dto.PageResponseDTO;
import org.zerock.api1.dto.ProductListDTO;
import org.zerock.api1.dto.ProductRegisterDTO;
import org.zerock.api1.service.ProductService;
import org.zerock.api1.util.CustomFileUtil;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CustomFileUtil fileUtil;

    @GetMapping("/list")
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        return productService.list(pageRequestDTO);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(ProductRegisterDTO productRegisterDTO) {

        List<String> uploadedFileNames = fileUtil.saveFiles(productRegisterDTO.getFiles());

        log.info(uploadedFileNames);

        productRegisterDTO.setUploadFileNames(uploadedFileNames);

        return ResponseEntity.ok(productService.register(productRegisterDTO));
    }

    @GetMapping("/img/{fileName}")
    public ResponseEntity<Resource> getImg(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }
}









