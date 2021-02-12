package com.csv.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.csv.entity.ServiceRelationshipPK;
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
	      List<ServiceRelationship> serviceRelationshipList = CSVReader.csvToObject(file.getInputStream());
            Map<ServiceRelationshipPK, ServiceRelationship> serviceRelationshipMap = serviceRelationshipList.stream()
                    .collect(Collectors.toMap(ServiceRelationship::getId, serviceRelationship -> serviceRelationship));
            List<ServiceRelationship> datatable = repository.findAll();
            List<ServiceRelationship> removeList  = datatable.stream().filter(data -> serviceRelationshipMap.get(data.getId()) == null).collect(Collectors.toList());
            if(removeList.size() > 0){
                repository.deleteAll(removeList);
            }
            Map<ServiceRelationshipPK, ServiceRelationship> datatableMap = datatable.stream()
                    .collect(Collectors.toMap(ServiceRelationship::getId, serviceRelationship -> serviceRelationship));
            List<ServiceRelationship> modifyList  = serviceRelationshipList.stream()
                    .filter(data -> datatableMap.get(data.getId()) != null)
                    .filter(data -> !datatableMap.get(data.getId()).getLabel().equals(data.getLabel()))
                    .filter(data -> datatableMap.get(data.getId()).getImpact().compareTo(data.getImpact()) != 0)
                    .collect(Collectors.toList());
            if(modifyList.size() > 0){
                repository.saveAll(modifyList);
            }

            List<ServiceRelationship> insertList  = serviceRelationshipList.stream()
                    .filter(data -> datatableMap.get(data.getId()) == null).collect(Collectors.toList());
            if(insertList.size() > 0){
                repository.saveAll(insertList);
            }
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
