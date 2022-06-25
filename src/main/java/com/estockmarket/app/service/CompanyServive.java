package com.estockmarket.app.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.app.bean.Company;
import com.estockmarket.app.repository.CompanyRepository;
@Service
public class CompanyServive {
	
	@Autowired
	private CompanyRepository repository;
	
	public List<Company> getAllCompanies() {		
		return (List<Company>) repository.findAll();
	}
}
