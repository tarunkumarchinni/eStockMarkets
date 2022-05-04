package com.estockmarket.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.estockmarket.app.bean.Stock;

public interface StockRepository extends MongoRepository<Stock, String> {

	  public Stock findByStockId(String stockId);
}
