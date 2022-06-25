package com.estockmarket.app.repository;

//import org.springframework.data.mongodb.repository.MongoRepository;

import com.estockmarket.app.bean.Stock;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//@Repository
@EnableScan
public interface StockRepository extends CrudRepository<Stock, String> {

	  public Stock findByStockId(String stockId);
	  public List<Stock> findByCompanyCode(String companyCode);
}
