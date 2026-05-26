package br.gov.endemias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.endemias.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
