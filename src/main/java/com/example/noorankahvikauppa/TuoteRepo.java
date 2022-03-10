package com.example.noorankahvikauppa;

import java.util.Collection;
import java.util.List;

import com.example.noorankahvikauppa.Tuote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuoteRepo extends JpaRepository<Tuote, Long> {

    List<Tuote> findByOsasto_idIn(Collection<Long> tuotteet);

    Page<Tuote> findByOsasto_IdIn(Collection<Long> tuotteet, Pageable pageable);
}