package com.estockmarket.app.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.estockmarket.app.bean.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

	  public Company findByCompanyCode(String companyCode);

}
