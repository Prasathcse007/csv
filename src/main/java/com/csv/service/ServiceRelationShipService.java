package com.csv.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csv.entity.ServiceRelationship;
import com.csv.repo.ServiceRelationshipRepo;
import com.csv.utils.CSVReader;

@Service
public class ServiceRelationShipService {
	
  @Autowired
  ServiceRelationshipRepo repository;

  public void save(MultipartFile file) {
    try {
      List<ServiceRelationship> serviceRelationship = CSVReader.csvToObject(file.getInputStream());
      repository.saveAll(serviceRelationship);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  
  public void update(MultipartFile file) {
	    try {
	      List<ServiceRelationship> serviceRelationship = CSVReader.csvToObject(file.getInputStream());
	      for(ServiceRelationship serviceRelation : serviceRelationship) {
	    	  if(repository.findByIdParentAndIdChild(serviceRelation.getId().getParent(),serviceRelation.getId().getChild()) != null) {
	    		  repository.save(serviceRelation);
	    	  } else {
	    		  repository.delete(serviceRelation);
	    	  }
	      }
	      repository.saveAll(serviceRelationship);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }
  public ByteArrayInputStream load() {
    List<ServiceRelationship> serviceRelationship = repository.findAll();

    ByteArrayInputStream in = CSVReader.objectToCSV(serviceRelationship);
    return in;
  }

  public List<ServiceRelationship> getAllService() {
    return repository.findAll();
  }
  
  public List<ServiceRelationship> getService(String parent) {
	    return repository.findByIdParent(parent);
  }
}
