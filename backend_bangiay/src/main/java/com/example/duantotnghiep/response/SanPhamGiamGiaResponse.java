package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamGiamGiaResponse {

    private UUID idSanPham;

    private String image;

    private String tenSp;

    private BigDecimal giaBan;

    private BigDecimal giaGiamGia;

    private Long mucGiam;

}
