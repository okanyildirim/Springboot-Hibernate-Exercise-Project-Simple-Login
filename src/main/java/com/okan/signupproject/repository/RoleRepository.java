package com.okan.signupproject.repository;

import com.okan.signupproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRole(String role);

}