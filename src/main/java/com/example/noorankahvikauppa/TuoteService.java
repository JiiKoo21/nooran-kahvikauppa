package com.example.noorankahvikauppa;

import java.util.List;

import com.example.noorankahvikauppa.Tuote;
import com.example.noorankahvikauppa.TuoteRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TuoteService {

    @Autowired
    private TuoteRepo tuoteRepo;

    public List<Tuote> listaaTuotteet() {
        return tuoteRepo.findAll();
    }

    // public Page<Tuote> listaaRajatutSivut(Integer sivu, Integer sivumaara) {
    // Pageable pageable = PageRequest.of(sivu - 1, sivumaara);
    // return tuoteRepo.findAll(pageable);
    // }

    public void lisaaTuote(Tuote tuote) {
        tuoteRepo.save(tuote);
    }

    public Tuote muokkaaTuote(Tuote tuote) {
        return tuoteRepo.save(tuote);
    }

    public Tuote getTuoteById(long id) {
        return tuoteRepo.getById(id);
    }

    public void poistaTuote(long id) {
        tuoteRepo.deleteById(id);
    }

    // public List<Tuote> listaaHalututTuotteet(List<Long> halututTuotteetId) {
    // return tuoteRepo.findByOsasto_idIn(halututTuotteetId);
    // }

    public Page<Tuote> listaaHalututTuotteet(List<Long> halututTuotteetId, Pageable pageable) {
        return tuoteRepo.findByOsasto_IdIn(halututTuotteetId, pageable);
    }

}