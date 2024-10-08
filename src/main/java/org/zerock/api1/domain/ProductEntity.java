package org.zerock.api1.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_product_ex")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ProductEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    @Column(nullable = false, length = 200)
    private String pname;

    @Lob
    private String pdesc;

    private int price;

    @Builder.Default
    private ProductStatus status = ProductStatus.SALE;

    private boolean delFlag;

    public void changeStatus(ProductStatus newStatus) {
        this.status = newStatus;
    }

    public void changePrice(int newPrice) {
        this.price = newPrice;
    }

    public void changeDelFlag(boolean newDelFlag) {
        this.delFlag = newDelFlag;
    }
}
