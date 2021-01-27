package com.yogesh.OneToManyExample.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yogesh.OneToManyExample.Model.Parents;

@Repository
public interface IParentRepository extends JpaRepository<Parents, String> {

}
