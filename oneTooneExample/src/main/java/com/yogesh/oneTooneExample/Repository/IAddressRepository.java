package com.yogesh.oneTooneExample.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yogesh.oneTooneExample.Model.Address;

@Repository
public interface IAddressRepository extends JpaRepository<Address, String> {
	
//	@Query("select a from Address a left join fetch a.employee")
	public List<Address> findAll();

}
