package com.main.catchy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.catchy.model.Role;
import com.main.catchy.repository.RoleRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Component
@AllArgsConstructor
public class RoleServicesImp  {
	@Autowired
    RoleRepository roledao;

	
	public List<Role> getall() {
		// TODO Auto-generated method stub
		return roledao.findAll();
	}

	
	public Role save(Role Role) {
		// TODO Auto-generated method stub
		return roledao.save(Role);
	}

	
	public void delete(int id) {
		roledao.deleteById((int) id);

	}

	
	public Role findById(int pkRoleID) {
		// TODO Auto-generated method stub
		return roledao.getRoleById(pkRoleID);
	}

}
