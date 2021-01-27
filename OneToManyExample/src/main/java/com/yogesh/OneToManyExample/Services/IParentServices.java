package com.yogesh.OneToManyExample.Services;

import com.yogesh.OneToManyExample.Model.Parents;

public interface IParentServices {
	public void save(Parents parents);

	public void saveAndFlush(Parents parents);

	public void delete(Parents parents);
}
