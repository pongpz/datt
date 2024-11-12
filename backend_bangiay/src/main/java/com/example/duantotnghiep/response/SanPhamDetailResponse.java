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
public class SanPhamDetailResponse {

    private UUID id;

    private String name;

    private BigDecimal giaBan;

    private BigDecimal donGiaKhiGiam;
}
