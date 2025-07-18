package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
}
