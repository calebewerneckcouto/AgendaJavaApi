package com.agenda.br.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agenda.br.model.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    
    Page<Contato> findAll(Pageable pageable);
    
    @Query("SELECT c FROM Contato c WHERE " +
           "LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(c.telefone) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Contato> buscarPorTermo(@Param("termo") String termo, Pageable pageable);
    
    boolean existsByEmail(String email);
    
    boolean existsByEmailAndIdNot(String email, Long id);
}