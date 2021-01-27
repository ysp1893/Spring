package com.yogesh.OneToManyExample.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogesh.OneToManyExample.Model.Childs;
import com.yogesh.OneToManyExample.Repository.IChildRepository;
import com.yogesh.OneToManyExample.Services.IChildServices;

@Service
public class ChildServicesImpl implements IChildServices {

	@Autowired
	IChildRepository ichildRepository;
	@Override
	public void save(Childs child) {
		ichildRepository.save(child);
	}

	@Override
	public Childs saveAndFlush(Childs child) {
		return ichildRepository.saveAndFlush(child);
	}

	@Override
	public void delete(Childs child) {
		ichildRepository.delete(child);
	}

}
