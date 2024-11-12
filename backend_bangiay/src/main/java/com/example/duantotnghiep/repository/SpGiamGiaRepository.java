package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.mapper.not_login.*;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpGiamGiaRepository extends JpaRepository<SpGiamGia, UUID> {

        // load sanpham on shop
        @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam,sp.idThuongHieu\n"
                        +
                        "FROM SanPham sp\n" +
                        "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
                        "LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
                        "JOIN Image i ON sp.id = i.idsanpham\n" +
                        "JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
                        "JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
                        "JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
                        "JOIN kieude kd ON kd.id = sp.idkieude\n" +
                        "WHERE i.isDefault = 'true'\n" +
                        "AND (\n" +
                        "    spgg.id IS NULL \n" +
                        "    OR (\n" +
                        "        spgg.id IS NOT NULL\n" +
                        "        AND spgg.id = (\n" +
                        "            SELECT MIN(spgg_inner.id)\n" +
                        "            FROM spgiamgia spgg_inner\n" +
                        "            WHERE spgg_inner.idsanpham = sp.id\n" +
                        "        )\n" +
                        "    )\n" +
                        ")", nativeQuery = true)
        List<loadsanpham_not_login> getAllSpGiamGia();

        // load sanpham lien quan
        @Query(value = "SELECT \n" +
                        "    sp.id, \n" +
                        "    i.tenImage, \n" +
                        "    sp.tenSanPham, \n" +
                        "    sp.giaBan, \n" +
                        "    spgg.donGiaKhiGiam, \n" +
                        "    spgg.mucGiam,sp.idThuongHieu\n" +
                        "FROM SanPham sp\n" +
                        "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
                        "LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
                        "JOIN Image i ON sp.id = i.idsanpham\n" +
                        "JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
                        "JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
                        "JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
                        "JOIN kieude kd ON kd.id = sp.idkieude\n" +
                        "WHERE i.isDefault = 'true'\n" +
                        "AND th.id = ?\n" +
                        "AND (\n" +
                        "    spgg.id IS NULL \n" +
                        "    OR (\n" +
                        "        spgg.id IS NOT NULL\n" +
                        "        AND spgg.id = (\n" +
                        "            SELECT MIN(spgg_inner.id)\n" +
                        "            FROM spgiamgia spgg_inner\n" +
                        "            WHERE spgg_inner.idsanpham = sp.id\n" +
                        "        )\n" +
                        "    )\n" +
                        ")", nativeQuery = true)
        List<loadsanpham_not_login> getSanPhamLienQuan(UUID idthuonghieu);

        // load thong tin san pham detail
        @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam,sp.idThuongHieu\n"
                        +
                        "                        FROM SanPham sp\n" +
                        "                        LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
                        "                        LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
                        "                        JOIN Image i ON sp.id = i.idsanpham\n" +
                        "                        JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
                        "                        JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
                        "                        JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
                        "                        JOIN kieude kd ON kd.id = sp.idkieude\n" +
                        "                        WHERE i.isDefault = 'true' AND sp.tensanpham=?\n" +
                        "            AND (\n" +
                        "                spgg.id IS NULL \n" +
                        "                OR (\n" +
                        "                    spgg.id IS NOT NULL\n" +
                        "                    AND spgg.id = (\n" +
                        "                        SELECT MIN(spgg_inner.id)\n" +
                        "                        FROM spgiamgia spgg_inner\n" +
                        "                        WHERE spgg_inner.idsanpham = sp.id\n" +
                        "                    )\n" +
                        "                )\n" +
                        "            )", nativeQuery = true)
        loadsanpham_not_login getNamePriceImageByIdSanPham(String tenSanPham);

        // load all mau sac
        @Query(value = "SELECT DISTINCT ms.id, ms.tenmausac\n" +
                        "FROM sanpham sp\n" +
                        "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
                        "JOIN mausac ms ON spct.idmausac = ms.id\n" +
                        "WHERE sp.tensanpham = ?", nativeQuery = true)
        List<loadmausac_not_login> getAllMauSac(String tensanpham);

        // load all size
        @Query(value = "SELECT DISTINCT s.id, s.size\n" +
                        "FROM sanpham sp\n" +
                        "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
                        "JOIN size s ON spct.idsize = s.id\n" +
                        "WHERE sp.tensanpham = ?", nativeQuery = true)
        List<loadsize_not_login> getAllSize(String tensanpham);

        // load all chatlieu
        @Query(value = "SELECT DISTINCT cl.id, cl.tenchatlieu\n" +
                        "            FROM sanpham sp\n" +
                        "            JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
                        "            JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
                        "            WHERE sp.tensanpham = ?", nativeQuery = true)
        List<loadchatlieu_not_login> getAllChatLieu(String tensanpham);

        // find by //SIZE// load //MAUSAC + CHATLIEU//
        @Query(value = "SELECT DISTINCT spct.idsize,ms.tenMauSac ,cl.tenChatLieu\n" +
                        "        FROM sanpham sp\n" +
                        "        JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
                        "        JOIN size s ON spct.idsize = s.id\n" +
                        "        JOIN mausac ms ON spct.idmausac = ms.id\n" +
                        "        JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
                        "        WHERE sp.tensanpham = ?1\n" +
                        "        AND s.id = ?2", nativeQuery = true)
        List<loadmausac_chatlieu_not_login> findMauSacChatLieu(String tensanpham, UUID idsize);

        // find by //MAUSAC// load //SIZE + CHATLIEU//
        @Query(value = "\t\tSELECT DISTINCT spct.idmausac,s.size ,cl.tenchatlieu\n" +
                        "        FROM sanpham sp\n" +
                        "        JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
                        "        JOIN size s ON spct.idsize = s.id\n" +
                        "        JOIN mausac ms ON spct.idmausac = ms.id\n" +
                        "        JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
                        "        WHERE sp.tensanpham = :tensanpham\n" +
                        "        AND ms.id = :idmausac", nativeQuery = true)
        List<loadsize_chatlieu_not_login> findSizeChatLieu(@Param("tensanpham") String tensanpham,
                        @Param("idmausac") UUID idmausac);

        // find by //CHATLIEU// load //SIZE + MAUSAC//
        @Query(value = "SELECT DISTINCT  spct.idchatlieu,s.size,ms.tenmausac\n" +
                        "        FROM sanpham sp\n" +
                        "        JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
                        "        JOIN size s ON spct.idsize = s.id\n" +
                        "        JOIN mausac ms ON spct.idmausac = ms.id\n" +
                        "        JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
                        "        WHERE sp.tensanpham = :tensanpham\n" +
                        "        AND cl.id = :idchatlieu", nativeQuery = true)
        List<loadmausac_size_not_login> findSizeMauSac(@Param("tensanpham") String tensanpham,
                        @Param("idchatlieu") UUID idchatlieu);

        // load sp chi tiet
        @Query(value = "SELECT spct.id,soluong FROM SanPham sp\n" +
                        "                        LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
                        "                        LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
                        "                        JOIN Image i ON sp.id = i.idsanpham\n" +
                        "                        JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
                        "                        JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
                        "                       JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
                        "                       JOIN sanphamchitiet spct on spct.idsanpham = sp.id\n" +
                        "                        JOIN chatlieu cl on spct.idchatlieu = cl.id\n" +
                        "                        JOIN mausac ms on spct.idmausac = ms.id\n" +
                        "                        JOIN kieude kd on sp.idkieude = kd.id\n" +
                        "                        JOIN size s on spct.idsize = s.id\n" +
                        "                        WHERE i.isDefault = 'true' and ms.id= :idmausac and s.id= :idsize and cl.id = :idchatlieu \n"
                        +
                        "                        and sp.tensanpham = :tensanpham\n" +
                        "\t\t\t\t\t\tAND (\n" +
                        "                            spgg.id IS NULL\n" +
                        "                            OR (\n" +
                        "                                spgg.id IS NOT NULL\n" +
                        "                                AND spgg.id = (\n" +
                        "                                    SELECT MIN(spgg_inner.id)\n" +
                        "                                   FROM spgiamgia spgg_inner\n" +
                        "                                   WHERE spgg_inner.idsanpham = sp.id\n" +
                        "                                )\n" +
                        "                            )\n" +
                        "                        )", nativeQuery = true)
        findIdSpctAndSoLuong_not_login findIdspctAndSoluong(@Param("idmausac") UUID idmausac,
                        @Param("idsize") UUID idsize, @Param("idchatlieu") UUID idchatlieu,
                        @Param("tensanpham") String tensanpham);

        @Query("SELECT spgg FROM SpGiamGia spgg " +
                "JOIN spgg.giamGia gg " +
                "JOIN spgg.sanPham sp " +
                "WHERE gg.trangThai = 1 AND sp.trangThai = 1 AND sp.id = :id")
        List<SpGiamGia> findBySanPham_Id(@Param("id") UUID id);

        @Query("SELECT COUNT(spgg) FROM SpGiamGia spgg JOIN spgg.giamGia gg WHERE gg.trangThai = 1 AND spgg.sanPham.id = :id")
        Long countSpGiamGia(@Param("id") UUID id);

        @Modifying
        @Query("DELETE FROM SpGiamGia spgg WHERE spgg.giamGia = :giamGia")
        void deleteByGiamGia(@Param("giamGia") GiamGia giamGia);

        // load sanpham on shop by key
        @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam, sp.idThuongHieu "
                        +
                        "FROM SanPham sp " +
                        "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham " +
                        "LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id " +
                        "JOIN Image i ON sp.id = i.idsanpham " +
                        "JOIN ThuongHieu th ON sp.idthuonghieu = th.id " +
                        "JOIN DanhMuc dm ON sp.iddanhmuc = dm.id " +
                        "JOIN XuatXu xx ON sp.idxuatxu = xx.id " +
                        "JOIN kieude kd ON kd.id = sp.idkieude " +
                        "WHERE (  th.id = :id OR dm.id = :id OR xx.id = :id OR kd.id = :id) " +
                        "AND ( " +
                        "    spgg.id IS NULL " +
                        "    OR ( " +
                        "        spgg.id IS NOT NULL " +
                        "        AND spgg.id = ( " +
                        "            SELECT MIN(spgg_inner.id) " +
                        "            FROM spgiamgia spgg_inner " +
                        "            WHERE spgg_inner.idsanpham = sp.id " +
                        "        ) " +
                        "    ) " +
                        ")", nativeQuery = true)
        List<loadsanpham_not_login> getAllSpGiamGiabyDanhMuc(@Param("id") UUID id);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
                "FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "WHERE i.isDefault = TRUE " +
                "AND kd.trangThai = 1 " +
                "AND sp.trangThai = 1 " +
                "AND th.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 " +
                "ORDER BY sp.ngayTao DESC")
        Page<Object[]> getAllShop(Pageable pageable);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
                "FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "WHERE i.isDefault = TRUE " +
                "AND kd.trangThai = 1 " +
                "AND sp.trangThai = 1 " +
                "AND th.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 AND sp.tenSanPham like %:name%" +
                "ORDER BY sp.ngayTao DESC")
        Page<Object[]> findByShopName(Pageable pageable,@Param("name") String name);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
                "FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "WHERE i.isDefault = TRUE " +
                "AND kd.trangThai = 1 " +
                "AND sp.trangThai = 1 " +
                "AND th.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 AND sp.giaBan BETWEEN :key1 AND :key2 " +
                "ORDER BY sp.ngayTao DESC")
        Page<Object[]> findByGia(Pageable pageable,@Param("key1") BigDecimal key1,@Param("key2") BigDecimal key2);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
                "FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "WHERE i.isDefault = TRUE " +
                "AND kd.trangThai = 1 " +
                "AND sp.trangThai = 1 " +
                "AND th.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 AND dm.id = :id " +
                "ORDER BY sp.ngayTao DESC")
        Page<Object[]> filterCategoryShop(Pageable pageable,@Param("id") UUID id);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
                "FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "WHERE i.isDefault = TRUE " +
                "AND kd.trangThai = 1 " +
                "AND sp.trangThai = 1 " +
                "AND th.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 AND th.id = :id " +
                "ORDER BY sp.ngayTao DESC")
        Page<Object[]> filterBrandShop(Pageable pageable,@Param("id") UUID id);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
                "FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "WHERE i.isDefault = TRUE " +
                "AND kd.trangThai = 1 " +
                "AND sp.trangThai = 1 " +
                "AND th.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 AND kd.id = :id " +
                "ORDER BY sp.ngayTao DESC")
        Page<Object[]> filterSoleShop(Pageable pageable,@Param("id") UUID id);

        @Query("SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, th.id " +
                "FROM SanPham sp " +
                "JOIN sp.listImage i " +
                "JOIN sp.kieuDe kd " +
                "JOIN sp.thuongHieu th " +
                "JOIN sp.danhMuc dm " +
                "JOIN sp.xuatXu xx " +
                "WHERE i.isDefault = TRUE " +
                "AND kd.trangThai = 1 " +
                "AND sp.trangThai = 1 " +
                "AND th.trangThai = 1 " +
                "AND dm.trangThai = 1 " +
                "AND xx.trangThai = 1 AND xx.id = :id " +
                "ORDER BY sp.ngayTao DESC")
        Page<Object[]> filterXuatXuShop(Pageable pageable,@Param("id") UUID id);

        @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam, sp.idThuongHieu "
                +
                "FROM SanPham sp " +
                "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham " +
                "LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id " +
                "JOIN Image i ON sp.id = i.idsanpham " +
                "JOIN ThuongHieu th ON sp.idthuonghieu = th.id " +
                "JOIN DanhMuc dm ON sp.iddanhmuc = dm.id " +
                "JOIN XuatXu xx ON sp.idxuatxu = xx.id " +
                "JOIN kieude kd ON kd.id = sp.idkieude " +
                "WHERE (  sp.giaBan BETWEEN :key1 AND :key2 OR spgg.donGiaKhiGiam BETWEEN :key1 AND :key2) " +
                "AND ( " +
                "    spgg.id IS NULL " +
                "    OR ( " +
                "        spgg.id IS NOT NULL " +
                "        AND spgg.id = ( " +
                "            SELECT MIN(spgg_inner.id) " +
                "            FROM spgiamgia spgg_inner " +
                "            WHERE spgg_inner.idsanpham = sp.id " +
                "        ) " +
                "    ) " +
                ")", nativeQuery = true)
        List<loadsanpham_not_login> getAllSpGiamGiabyTien(@Param("key1") BigDecimal key1, @Param("key2") BigDecimal key2);


        // load sanpham on shop by ten
        @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam, sp.idThuongHieu "
                +
                "FROM SanPham sp " +
                "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham " +
                "LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id " +
                "JOIN Image i ON sp.id = i.idsanpham " +
                "JOIN ThuongHieu th ON sp.idthuonghieu = th.id " +
                "JOIN DanhMuc dm ON sp.iddanhmuc = dm.id " +
                "JOIN XuatXu xx ON sp.idxuatxu = xx.id " +
                "JOIN kieude kd ON kd.id = sp.idkieude " +
                "WHERE (  sp.tenSanPham  LIKE :key ) " +
                "AND ( " +
                "    spgg.id IS NULL " +
                "    OR ( " +
                "        spgg.id IS NOT NULL " +
                "        AND spgg.id = ( " +
                "            SELECT MIN(spgg_inner.id) " +
                "            FROM spgiamgia spgg_inner " +
                "            WHERE spgg_inner.idsanpham = sp.id " +
                "        ) " +
                "    ) " +
                ")", nativeQuery = true)
        List<loadsanpham_not_login> getAllSpGiamGiabyKey(@Param("key") String key);
        SpGiamGia findByGiamGiaAndSanPham(GiamGia giamGia, SanPham sanPham);
}
