package com.ufcg.psoft.commerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ufcg.psoft.commerce.model.Sabor;
import org.springframework.stereotype.Repository;

@Repository
public interface SaborRepository extends JpaRepository<Sabor, Long> {}
