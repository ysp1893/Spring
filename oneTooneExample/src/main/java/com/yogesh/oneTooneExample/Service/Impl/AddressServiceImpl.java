package com.yogesh.oneTooneExample.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import com.yogesh.oneTooneExample.Model.Address;
import com.yogesh.oneTooneExample.Repository.IAddressRepository;
import com.yogesh.oneTooneExample.Service.IAddressService;

@Service
public class AddressServiceImpl implements IAddressService {
	
	@Autowired
	IAddressRepository iAddressRepository;
	
	@Transient
	@Override
	public void save(Address address) {
		iAddressRepository.save(address);
	}
	
	@Transient
	@Override
	public Address saveAndFlush(Address address) {
		return iAddressRepository.saveAndFlush(address) ;
	}

	@Transient
	@Override
	public void delete(Address address) {
		iAddressRepository.delete(address);
	}
}
