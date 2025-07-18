package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {}
