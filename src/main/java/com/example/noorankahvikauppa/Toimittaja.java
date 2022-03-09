package com.example.noorankahvikauppa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Toimittaja extends AbstractPersistable<Long> {

    private String nimi;
    private String yhteyshenkilo;
    private String yhteyshenkiloemail;

    // YHDELLÄ TOIMITTAJALLA VOI OLLA YKSI TAI USEAMPI TUOTE
    @OneToMany(mappedBy = "toimittaja")
    private List<Tuote> tuotteet = new ArrayList<>();

}