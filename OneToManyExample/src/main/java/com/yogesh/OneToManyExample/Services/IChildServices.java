package com.yogesh.OneToManyExample.Services;

import com.yogesh.OneToManyExample.Model.Childs;

public interface IChildServices {
	public void save(Childs child);

	public Childs saveAndFlush(Childs child);

	public void delete(Childs child);
}
