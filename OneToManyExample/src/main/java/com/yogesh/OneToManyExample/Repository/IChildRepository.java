package com.yogesh.OneToManyExample.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yogesh.OneToManyExample.Model.Childs;

@Repository
public interface IChildRepository extends JpaRepository<Childs, String>{

}
