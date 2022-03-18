package com.example.noorankahvikauppa;

import java.util.List;

// import com.example.noorankahvikauppa.Toimittaja;
// import com.example.noorankahvikauppa.ToimittajaRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToimittajaService {

    @Autowired
    private ToimittajaRepo toimittajaRepo;

    public List<Toimittaja> listaaToimittajat() {
        return toimittajaRepo.findAll();
    }

    public void lisaaToimittaja(Toimittaja toimittaja) {
        toimittajaRepo.save(toimittaja);
    }

    public Toimittaja muokkaaToimittaja(Toimittaja toimittaja) {
        return toimittajaRepo.save(toimittaja);
    }

    public Toimittaja getToimittajaById(long id) {
        return toimittajaRepo.getById(id);
    }

    public void poistaToimittaja(long id) {
        toimittajaRepo.deleteById(id);
    }

}
