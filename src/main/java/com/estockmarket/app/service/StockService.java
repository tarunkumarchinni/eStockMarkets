package com.estockmarket.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.app.bean.Stock;
import com.estockmarket.app.repository.StockRepository;

@Service
public class StockService {
	
	@Autowired
	private StockRepository repository;
	
	public List<Stock> getAllStocks() {		
		return (List<Stock>) repository.findAll();
	}

}
