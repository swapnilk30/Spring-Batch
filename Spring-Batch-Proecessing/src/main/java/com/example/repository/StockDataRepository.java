package com.example.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.StockData;

public interface StockDataRepository extends JpaRepository<StockData, Long>{

}
