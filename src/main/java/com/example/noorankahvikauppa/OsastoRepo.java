package com.example.noorankahvikauppa;

import com.example.noorankahvikauppa.Osasto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OsastoRepo extends JpaRepository<Osasto, Long> {

    Osasto findByNimi(String nimi);
}
