package com.main.catchy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.catchy.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query("select role from Role role where role.id  like :id")
	public Role getRoleById(@Param("id") long id);

	@Query("select role from Role role where role.name  like :name")
	public Role getRoleByName(@Param("name") String name);

}
