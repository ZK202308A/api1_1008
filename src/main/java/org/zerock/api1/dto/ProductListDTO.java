package org.zerock.api1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.api1.domain.ProductStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {

    private Long pno;
    private String pname;
    private String pdesc;
    private int price;
    private String img1;
    private String img2;
    private String img3;

    private ProductStatus status;

    private LocalDateTime createdDate;
}
