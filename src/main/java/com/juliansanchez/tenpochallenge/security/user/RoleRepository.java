package com.juliansanchez.tenpochallenge.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(ERole eRole);
}
