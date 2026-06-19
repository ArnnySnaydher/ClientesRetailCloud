package com.aquispe.techretail.infrastructure.adapter.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
