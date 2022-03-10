package com.example.noorankahvikauppa;

import java.util.List;

import com.example.noorankahvikauppa.Osasto;
import com.example.noorankahvikauppa.OsastoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OsastoService {

    @Autowired
    private OsastoRepo osastoRepo;

    public List<Osasto> listaaOsastot() {
        return osastoRepo.findAll();
    }

    public Osasto getOsastoById(long id) {
        return osastoRepo.getById(id);
    }

    public void lisaaOsasto(Osasto osasto) {
        osastoRepo.save(osasto);
    }

    public Osasto muokkaaOsastoa(Osasto osasto) {
        return osastoRepo.save(osasto);
    }

    public void poistaOsasto(long id) {
        osastoRepo.deleteById(id);
    }

}