package com.api.xzy.repository;

import com.api.xzy.model.TblUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblUserRepository extends JpaRepository<TblUser, Integer> {
    TblUser findByUsernameAndStatus(String username, Integer status);

    boolean existsByMobilenumber(String mobilenumber);

    boolean existsByUsername(String username);

    TblUser findByUsername(String username);
}