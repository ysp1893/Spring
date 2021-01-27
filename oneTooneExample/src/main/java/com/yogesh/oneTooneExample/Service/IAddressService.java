package com.yogesh.oneTooneExample.Service;

import com.yogesh.oneTooneExample.Model.Address;

public interface IAddressService {

	public void save(Address address);

	public Address saveAndFlush(Address address);

	public void delete(Address address);
}
