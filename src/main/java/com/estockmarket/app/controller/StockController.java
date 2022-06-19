package com.estockmarket.app.controller;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.app.bean.Company;
import com.estockmarket.app.bean.Stock;
import com.estockmarket.app.bean.serviceMessage;
import com.estockmarket.app.repository.StockRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.client.result.DeleteResult;

@CrossOrigin(origins = "*")
@RestController
public class StockController {
	@Autowired
	private StockRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(value = "/api/v1.0/market/stock/getall", method = RequestMethod.GET)
	public ResponseEntity<serviceMessage> getAllStocks() {
		HttpStatus httpResponse=HttpStatus.OK;
		serviceMessage response=new serviceMessage();
		List<Stock> stockList=new ArrayList<Stock>();
		stockList.addAll(repository.findAll());
        response = successMessage("stock List retrived successfully.","200", stockList);
        return new ResponseEntity<>(response,httpResponse);
	}
	
	@RequestMapping(value = "/api/v/1.0/market/stock/get/{companyCode}/{startDate}/{endDate}", method = RequestMethod.GET)
	public ResponseEntity<serviceMessage> getAllSelectedStocks(@PathVariable String companyCode,@PathVariable String startDate,@PathVariable String endDate) throws ParseException {
		HttpStatus httpResponse=HttpStatus.OK;
		serviceMessage response=new serviceMessage();
		try{ 
		    Date fromDate=new SimpleDateFormat("dd-MM-yyyy").parse(startDate);  
		    Date toDate=new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
		    if(fromDate.compareTo(toDate) < 0 && toDate.compareTo(fromDate) > 0) {
		    	Query query = new Query();
	            Criteria criteria = new Criteria();
	            criteria.andOperator(Criteria.where("stockDate").gte(fromDate).lt(toDate),Criteria.where("companyCode").is(companyCode));
	            query.addCriteria(criteria);
	            List<Stock> resultList = mongoTemplate.find(query, Stock.class);
	            response = successMessage("stock List retrived successfully.","200", resultList);
		    }else {
		    	httpResponse=HttpStatus.BAD_REQUEST;
				response = errorMessage("stock List date range in incorrect.please try again later","400", null);
			}
        } catch(PatternSyntaxException e) {
        	httpResponse=HttpStatus.INTERNAL_SERVER_ERROR;
			response = errorMessage("stock start and end date pattern is missmatching.please try again later","500", Collections.emptyList());
        }
		return new ResponseEntity<>(response,httpResponse);
	}
	
	public serviceMessage successMessage(String message,String code,List resultList) {
		serviceMessage result=new serviceMessage();
		result.setMessage(message);
		result.setCode(code);
		result.setResult(resultList);
		return result;
	}
	public serviceMessage errorMessage(String message,String code,List resultList) {
		serviceMessage result=new serviceMessage();
		result.setMessage(message);
		result.setCode(code);
		result.setResult(resultList);
		return result;
	}
	
	@RequestMapping(value = "/api/v1.0/market/stock/add/{companyCode}", method = RequestMethod.POST)
	public ResponseEntity<serviceMessage> addNewStock(@RequestBody Stock stock,@PathVariable String companyCode) {
		HttpStatus httpResponse=HttpStatus.OK;
		serviceMessage response=new serviceMessage();
		try {
			stock.setCompanyCode(companyCode);
			if(repository.save(stock) !=null) {
				response = successMessage("stock successfully added.","200", null);
			}else {
				httpResponse=HttpStatus.INTERNAL_SERVER_ERROR;
				response = errorMessage("stock doesnot added.please try again later","500", null);
			}
			
		}catch(Exception ex) {
			httpResponse=HttpStatus.INTERNAL_SERVER_ERROR;
			response = errorMessage("stock doesnot added.please try again later","500", null);
		}
		return new ResponseEntity<>(response,httpResponse);
	}
	
	@RequestMapping(value = "/api/v1.0/market/stock/delete/{companyCode}", method = RequestMethod.DELETE)
	public DeleteResult deleteStock(@PathVariable String companyCode) {
		Query query = new Query();
		Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("companyCode").is(companyCode));
        query.addCriteria(criteria);
		return mongoTemplate.remove(query, Stock.class, "stock");
	}
	
	
}