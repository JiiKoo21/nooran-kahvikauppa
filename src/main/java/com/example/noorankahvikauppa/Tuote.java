package com.example.noorankahvikauppa;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

//import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuote extends AbstractPersistable<Long> {

    private String nimi;
    private String kuvaus;
    private BigDecimal hinta;
    
    // MUISTA STRING KUVA
    @Lob
    //@Type(type = "org.hibernate.type.BinaryType")
    @Basic(fetch = FetchType.EAGER)
    private byte[] kuva;
    
    // TUOTE LIITTYY YHTEEN OSASTOON, VALMISTAJAAN JA TOIMITTAJAAN
    @ManyToOne
    private Osasto osasto;
    
    @ManyToOne
    private Valmistaja valmistaja;
    
    @ManyToOne
    private Toimittaja toimittaja;

}