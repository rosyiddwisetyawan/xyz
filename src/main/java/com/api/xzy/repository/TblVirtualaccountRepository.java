package com.api.xzy.repository;

import com.api.xzy.model.TblVirtualaccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TblVirtualaccountRepository extends JpaRepository<TblVirtualaccount, Integer> {
    List<TblVirtualaccount> findByVirtualaccount(String virtualaccount);
}