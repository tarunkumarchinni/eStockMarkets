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
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.app.bean.Company;
import com.estockmarket.app.bean.Stock;
import com.estockmarket.app.bean.serviceMessage;
import com.estockmarket.app.repository.StockRepository;
import com.estockmarket.app.service.StockService;
import com.fasterxml.jackson.annotation.JsonFormat;
//import com.mongodb.client.result.DeleteResult;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
@CrossOrigin(origins = "*")
@RestController
public class StockController {
	public static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private StockService stockService;

	DynamoDBMapper mapper = new DynamoDBMapper(client);
//	@Autowired
//	private MongoTemplate mongoTemplate;
	
	@GetMapping(value = "/api/v1.0/market/stock/getall")
	public ResponseEntity<serviceMessage> getAllStocks() {
		HttpStatus httpResponse=HttpStatus.OK;
		serviceMessage response=new serviceMessage();
        response = successMessage("stock List retrived successfully.","200",stockService.getAllStocks());
        return new ResponseEntity<>(response,httpResponse);
	}
	

	@GetMapping(value = "/api/v/1.0/market/stock/get/{companyCode}/{startDate}/{endDate}")
	public ResponseEntity<serviceMessage> getAllSelectedStocks(@PathVariable String companyCode,@PathVariable String startDate,@PathVariable String endDate) throws ParseException {
		HttpStatus httpResponse=HttpStatus.OK;
		serviceMessage response=new serviceMessage();
		try{ 
		    Date fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);  
		    Date toDate=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
		    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	        String fromDate1 = dateFormatter.format(fromDate);
	        String toDate1 = dateFormatter.format(toDate);
		    if(fromDate.compareTo(toDate) < 0 && toDate.compareTo(fromDate) > 0) {
		        
		    	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		        eav.put(":val1", new AttributeValue().withS(companyCode));
		        eav.put(":val2", new AttributeValue().withS(fromDate1));
		        eav.put(":val3", new AttributeValue().withS(toDate1));
		        DynamoDBQueryExpression<Stock> queryExpression = new DynamoDBQueryExpression<Stock>()
		        		.withConsistentRead(false)
		        		.withIndexName("companyCode-stockDate-index")
			            .withKeyConditionExpression("companyCode =:val1 and stockDate between :val2 and :val3")
			            .withExpressionAttributeValues(eav);

		        List<Stock> resultList =  mapper.query(Stock.class, queryExpression);
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
	
	@PostMapping(value = "/api/v1.0/market/stock/add/{companyCode}")
	public ResponseEntity<serviceMessage> addNewStock(@RequestBody Stock stock,@PathVariable String companyCode) {
		HttpStatus httpResponse=HttpStatus.OK;
		serviceMessage response=new serviceMessage();
		try {
			stock.setCompanyCode(companyCode);
			if(stockRepository.save(stock) !=null) {
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
	
//	@RequestMapping(value = "/api/v1.0/market/stock/delete/{companyCode}", method = RequestMethod.DELETE)
//	public DeleteResult deleteStock(@PathVariable String companyCode) {
//		Query query = new Query();
//		Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("companyCode").is(companyCode));
//        query.addCriteria(criteria);
//		return mongoTemplate.remove(query, Stock.class, "stock");
//	}
	
	
}