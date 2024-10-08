package org.zerock.api1.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.api1.domain.ProductStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductRegisterDTO {

    private String pname;
    private String pdesc;
    private int price;

    private List<MultipartFile> files;

    private List<String> uploadFileNames;

}
