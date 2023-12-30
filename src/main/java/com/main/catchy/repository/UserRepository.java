package com.main.catchy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.main.catchy.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value= "SELECT * FROM catchy.user where username=:username ", nativeQuery = true)
	User findUserByUserName(@Param("username") String username);
	@Query("select user from User user where user.id like :id")
	User findUserByID(@Param("id")long id);
	@Modifying
	@Transactional()
	@Query("DELETE  FROM User user where user.id like :id ")
	void deleteUser(@Param("id")long id);
	
	@Transactional
	@Modifying
	@Query(value = "delete  from  catchy.user_role  where user_id like :userID and role_id like :roleId",nativeQuery = true)
	void deleteRole(@Param("roleId") long roleId,@Param("userID") long userID);
	@Query("select user from User user where user.email like :email")
	User findUserByEmail(String email);
}
