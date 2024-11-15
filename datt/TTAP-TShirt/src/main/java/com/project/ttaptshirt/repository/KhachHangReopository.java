package com.project.ttaptshirt.repository;


import com.project.ttaptshirt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangReopository extends JpaRepository<User, Long> {
//    User findByUsername(String userName);
    Optional<User> findByEmail(String email);

    List<User> findByHoTenContainingIgnoreCase(String name);
}
