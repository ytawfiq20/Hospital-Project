package com.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.entity.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

	public Optional<Person> findByUsername(String username);
	
	@Query(value = "select exists(select username from person where "
    		+ "username=:username)"
        , nativeQuery = true)
    public int isUsernameExists(String username);
	
	@Query(value = "select * from person where replace(concat(first_name,last_name),' ','')=:name"
			, nativeQuery = true)
	public List<Person> getByName(String name);
	
	@Query(value = "select * from person "
    		+ "where replace(concat(first_name,last_name),' ','')"
    		+ "LIKE CONCAT('%',:name,'%') group by id", nativeQuery = true)
    public List<Person> getByNameLike(String name);
	
}
