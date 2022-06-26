package com.estockmarket.app.repository;


//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;

import com.estockmarket.app.bean.Company;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

	  public Company findByCompanyCode(String companyCode);

}
