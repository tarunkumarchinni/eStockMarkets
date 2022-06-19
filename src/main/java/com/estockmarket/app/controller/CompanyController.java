package com.estockmarket.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.app.bean.Company;
import com.estockmarket.app.repository.CompanyRepository;
import com.estockmarket.app.service.KafkaSender;
import com.estockmarket.app.service.Producer;
import com.mongodb.client.result.DeleteResult;
@CrossOrigin(origins = "*")
@RestController
public class CompanyController {
	
	@Autowired
	private CompanyRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	KafkaSender kafkaSender;
	
	private final Producer producer;

    @Autowired
    CompanyController(Producer producer) {
        this.producer = producer;
    }
	
	@RequestMapping(value = "/api/v1.0/market/company/getall", method = RequestMethod.GET)
	public List<Company> getAllCompanies() {
//		producer.sendMessage("kafka to get the data was retrived to get call successfully");
		List<Company> companyList=new ArrayList<Company>();
		companyList.addAll(repository.findAll());
		return companyList;
	}
	
	@RequestMapping(value = "/api/v1.0/market/company/register", method = RequestMethod.POST)
	public Company addNewCompany(@RequestBody Company company) {
		return repository.save(company);
	}
	
	@RequestMapping(value = "/api/v1.0/market/company/info/{companyCode}", method = RequestMethod.GET)
	public Stream<Company> getCompanyByCode(@PathVariable String companyCode) {
		List<Company> companyList=new ArrayList<Company>();
		repository.findAll().forEach(companyList::add);
		return companyList.stream().filter(t->t.getCompanyCode().equals(companyCode));
	}
	
	@RequestMapping(value = "/api/v1.0/market/company/delete/{companyCode}", method = RequestMethod.DELETE)
	public DeleteResult deleteUserTweet(@PathVariable String companyCode) {
		Query query = new Query();
		Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("companyCode").is(companyCode));
        query.addCriteria(criteria);
		return mongoTemplate.remove(query, Company.class, "company");
	}
}
