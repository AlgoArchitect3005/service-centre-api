package com.servicecentre.service_centre_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicecentre.service_centre_api.model.User;
import java.util.Optional;  

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
