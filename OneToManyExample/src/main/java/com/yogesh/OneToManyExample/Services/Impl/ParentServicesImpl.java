package com.yogesh.OneToManyExample.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogesh.OneToManyExample.Model.Parents;
import com.yogesh.OneToManyExample.Repository.IParentRepository;
import com.yogesh.OneToManyExample.Services.IParentServices;

@Service
public class ParentServicesImpl implements IParentServices {

	@Autowired
	IParentRepository iparentRepository;
	
	@Override
	public void save(Parents parents) {
		iparentRepository.save(parents);
	}

	@Override
	public void saveAndFlush(Parents parents) {
		iparentRepository.saveAndFlush(parents);
	}

	@Override
	public void delete(Parents parents) {
		iparentRepository.delete(parents);
	}

}
