package com.csv.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csv.entity.ServiceRelationship;
import com.csv.entity.ServiceRelationshipPK;

public interface ServiceRelationshipRepo extends JpaRepository<ServiceRelationship, ServiceRelationshipPK>{

	ServiceRelationship findByIdParentAndIdChild(String parent, String child);
	List<ServiceRelationship> findByIdParent(String parent);
}
