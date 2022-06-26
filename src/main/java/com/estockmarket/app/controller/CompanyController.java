package com.estockmarket.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.app.bean.Company;
import com.estockmarket.app.repository.CompanyRepository;
import com.estockmarket.app.service.CompanyServive;
//import com.estockmarket.app.service.KafkaSender;
//import com.estockmarket.app.service.Producer;
//import com.mongodb.client.result.DeleteResult;
@CrossOrigin(origins = "*")
@RestController
public class CompanyController {
	
	@Autowired
	private CompanyRepository repository;
//	@Autowired
//	private MongoTemplate mongoTemplate;
	
	@Autowired
	private CompanyServive companyService;
//	@Autowired
//	KafkaSender kafkaSender;
	
//	private final Producer producer;
//
//    @Autowired
//    CompanyController(Producer producer) {
//        this.producer = producer;
//    }
    
    @GetMapping(value = "/")
	public String getMessage() {
		return "welcome to the page";
	}
	
    @GetMapping(value = "/api/v1.0/market/company/getall")
	public List<Company> getAllCompanies() {
//		producer.sendMessage("kafka to get the data was retrived to get call successfully");
		return companyService.getAllCompanies();
	}
	
    @PostMapping(value = "/api/v1.0/market/company/register")
	public Company addNewCompany(@RequestBody Company company) {
		return repository.save(company);
	}
	
	@GetMapping(value = "/api/v1.0/market/company/info/{companyCode}")
	public Stream<Company> getCompanyByCode(@PathVariable String companyCode) {
		List<Company> companyList=new ArrayList<Company>();
		repository.findAll().forEach(companyList::add);
		return companyList.stream().filter(t->t.getCompanyCode().equals(companyCode));
	}
	
//	@RequestMapping(value = "/api/v1.0/market/company/delete/{companyCode}", method = RequestMethod.DELETE)
//	public DeleteResult deleteUserTweet(@PathVariable String companyCode) {
//		Query query = new Query();
//		Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("companyCode").is(companyCode));
//        query.addCriteria(criteria);
//		return mongoTemplate.remove(query, Company.class, "company");
//	}
}
