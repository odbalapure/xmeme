package com.xmeme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.xmeme.entity.MemeEntity;

public interface MemeRepository extends MongoRepository<MemeEntity, String> {

	// single select operation
	@Query("{'memeId': '?0'}")
	Optional<MemeEntity> findMemeById(String memeId);

	// multi select operations
	@Query("{'owner': {$regex: '.*?0.*', $options: 'i'}}")
	Optional<List<MemeEntity>> findMemeByOwner(String owner);

	@Query("{'name': {$regex: '.*?0.*', $options: 'i'}}")
	Optional<List<MemeEntity>> findMemeByExactOwner(String owner);

	@Query("{'caption': {$regex: '.*?0.*', $options: 'i'}}")
	Optional<List<MemeEntity>> findMemeByCaption(String name);
	
	// delete operation
	@Query(value = "{'memeId' : ?0}", delete = true)
	public void deleteById(String id);

}
