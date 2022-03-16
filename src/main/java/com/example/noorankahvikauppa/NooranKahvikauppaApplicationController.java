package com.example.noorankahvikauppa;

import java.io.IOException;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.noorankahvikauppa.Osasto;
import com.example.noorankahvikauppa.Toimittaja;
import com.example.noorankahvikauppa.Tuote;
import com.example.noorankahvikauppa.Valmistaja;
import com.example.noorankahvikauppa.OsastoService;
import com.example.noorankahvikauppa.ToimittajaService;
import com.example.noorankahvikauppa.TuoteService;
import com.example.noorankahvikauppa.ValmistajaService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NooranKahvikauppaApplicationController {

    @Autowired
    private OsastoService osastoService;

    @Autowired
    private TuoteService tuoteService;

    @Autowired
    private ValmistajaService valmistajaService;

    @Autowired
    private ToimittajaService toimittajaService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // TUOTTEET

    @GetMapping("/tuotteet")
    public String naytaTuotteet(Model model) {
        model.addAttribute("tuotteet", tuoteService.listaaTuotteet());
        model.addAttribute("valmistajat", valmistajaService.listaaValmistajat());
        model.addAttribute("osastot", osastoService.listaaOsastot());
        model.addAttribute("toimittajat", toimittajaService.listaaToimittajat());
        return "tuotteet";
    }

    @PostMapping("/tuotteet")
    public String lisaaTuote(@RequestParam String nimi, @RequestParam BigDecimal hinta,
            @RequestParam String kuvaus, @RequestParam Long osastoID,
            @RequestParam Long toimittajaID,
            @RequestParam Long valmistajaID, @RequestParam("kuva") MultipartFile kuva)
            throws IOException {
        Tuote uusiTuote = new Tuote();
        uusiTuote.setNimi(nimi);
        uusiTuote.setHinta(hinta);
        uusiTuote.setKuva(kuva.getBytes());
        uusiTuote.setKuvaus(kuvaus);
        Osasto osasto = osastoService.getOsastoById(osastoID);
        uusiTuote.setOsasto(osasto);
        Valmistaja valmistaja = valmistajaService.getValmistajaById(valmistajaID);
        uusiTuote.setValmistaja(valmistaja);
        Toimittaja toimittaja = toimittajaService.getToimittajaById(toimittajaID);
        uusiTuote.setToimittaja(toimittaja);
        tuoteService.lisaaTuote(uusiTuote);
        return "redirect:/tuotteet";
    }

    @GetMapping(path = "/tuotteet/edittuote/{id}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return tuoteService.getTuoteById(id).getKuva();
    }

    @GetMapping("/tuotteet/edittuote/{id}")
    public String naytaMuokkaaTuote(@PathVariable("id") long id, Model model) {
        Tuote tuote = tuoteService.getTuoteById(id);
        model.addAttribute("tuote", tuote);
        model.addAttribute("valmistajat", valmistajaService.listaaValmistajat());
        model.addAttribute("osastot", osastoService.listaaOsastot());
        model.addAttribute("toimittajat", toimittajaService.listaaToimittajat());
        return "edittuote";
    }

    @PostMapping("/tuotteet/edittuote/{id}")
    public String muokkaaTuote(@PathVariable("id") Long id, @RequestParam String nimi,
            @RequestParam BigDecimal hinta,
            @RequestParam String kuvaus, @RequestParam Long osastoID,
            @RequestParam Long toimittajaID,
            @RequestParam Long valmistajaID) {
        Tuote tuote = tuoteService.getTuoteById(id);
        tuote.setNimi(nimi);
        tuote.setHinta(hinta);
        tuote.setKuvaus(kuvaus);
        Osasto osasto = osastoService.getOsastoById(osastoID);
        tuote.setOsasto(osasto);
        Valmistaja valmistaja = valmistajaService.getValmistajaById(valmistajaID);
        tuote.setValmistaja(valmistaja);
        Toimittaja toimittaja = toimittajaService.getToimittajaById(toimittajaID);
        tuote.setToimittaja(toimittaja);
        tuoteService.muokkaaTuote(tuote);
        return "redirect:/tuotteet/edittuote/" + id;
    }

    @RequestMapping(value = "tuotteet/poista", method = RequestMethod.GET)
    public String poistaTuote(@RequestParam(name = "tuoteId") Long id) {
        tuoteService.poistaTuote(id);
        return "redirect:/tuotteet";
    }


    // VALMISTAJAT

    @GetMapping("/valmistajat")
    public String naytaValmistajat(Model model) {
        model.addAttribute("valmistajat", valmistajaService.listaaValmistajat());
        return "valmistajat";
    }

    @PostMapping("/valmistajat")
    public String lisaaValmistaja(Model model, @ModelAttribute Valmistaja valmistaja) {
        valmistajaService.uusiValmistaja(valmistaja);
        return "redirect:/valmistajat";
    }

    @GetMapping("/valmistajat/muokkaa/{id}")
    public String naytaMuokkaaValmistaja(@PathVariable("id") long id, Model model) {
        Valmistaja valmistaja = valmistajaService.getValmistajaById(id);
        model.addAttribute("valmistaja", valmistaja);
        return "muokkaa";
    }

    @PostMapping("/valmistajat/muokkaa/{id}")
    public String muokkaaValmistajaa(@PathVariable("id") Long id, @ModelAttribute Valmistaja valmistaja) {
        Valmistaja muokkaaValmistaja = valmistajaService.getValmistajaById(id);
        muokkaaValmistaja.setNimi(valmistaja.getNimi());
        muokkaaValmistaja.setUrl(valmistaja.getUrl());
        valmistajaService.muokkaaValmistajaa(muokkaaValmistaja);
        return "redirect:/valmistajat/muokkaa/" + id;
    }

    @RequestMapping(value = "valmistajat/poista", method = RequestMethod.GET)
    public String poistaValmistaja(@RequestParam(name = "valmistajaId") Long id) {
        valmistajaService.poistaValmistaja(id);
        return "redirect:/valmistajat";
    }


    // OSASTOT

    @GetMapping("/osastot")
    public String naytaOsastot(Model model) {
        model.addAttribute("osastot", osastoService.listaaOsastot());
        return "osastot";
    }

    @PostMapping("/osastot")
    public String lisaaOsasto(Model model, @ModelAttribute Osasto osasto) {
        osastoService.lisaaOsasto(osasto);
        return "redirect:/osastot";
    }

    @GetMapping("/osastot/editosasto/{id}")
    public String naytaMuokkaaOsasto(@PathVariable("id") long id, Model model) {
        Osasto osasto = osastoService.getOsastoById(id);
        model.addAttribute("osasto", osasto);
        return "editosasto";
    }

    @PostMapping("/osastot/editosasto/{id}")
    public String muokkaaOsastoa(@PathVariable("id") Long id, @ModelAttribute Osasto osasto) {
        Osasto muokkaaOsastoa = osastoService.getOsastoById(id);
        muokkaaOsastoa.setNimi(osasto.getNimi());
        muokkaaOsastoa.setOsastoIDP(osasto.getOsastoIDP());
        osastoService.muokkaaOsastoa(muokkaaOsastoa);
        return "redirect:/osastot/editosasto/" + id;
    }

    @RequestMapping(value = "osastot/poista", method = RequestMethod.GET)
    public String poistaOsasto(@RequestParam(name = "osastoId") Long id) {
        osastoService.poistaOsasto(id);
        return "redirect:/osastot";
    }


    // TOIMITTAJAT

    @GetMapping("/toimittajat")
    public String naytaToimittajat(Model model) {
        model.addAttribute("toimittajat", toimittajaService.listaaToimittajat());
        return "toimittajat";
    }

    @PostMapping("/toimittajat")
    public String lisaaToimittaja(Model model, @ModelAttribute Toimittaja toimittaja) {
        toimittajaService.lisaaToimittaja(toimittaja);
        return "redirect:/toimittajat";
    }

    @GetMapping("/toimittajat/edit/{id}")
    public String naytaMuokkaaToimittaja(@PathVariable("id") long id, Model model) {
        Toimittaja toimittaja = toimittajaService.getToimittajaById(id);
        model.addAttribute("toimittaja", toimittaja);
        return "edit";
    }

    @PostMapping("/toimittajat/edit/{id}")
    public String muokkaaToimittajaa(@PathVariable("id") Long id, @ModelAttribute Toimittaja toimittaja) {
        Toimittaja muokkaaToimittaja = toimittajaService.getToimittajaById(id);
        muokkaaToimittaja.setNimi(toimittaja.getNimi());
        muokkaaToimittaja.setYhteyshenkilo(toimittaja.getYhteyshenkilo());
        muokkaaToimittaja.setYhteyshenkiloemail(toimittaja.getYhteyshenkiloemail());
        toimittajaService.muokkaaToimittaja(muokkaaToimittaja);
        return "redirect:/toimittajat/edit/" + id;
    }

    @RequestMapping(value = "toimittajat/poista", method = RequestMethod.GET)
    public String poistaToimittaja(@RequestParam(name = "toimittajaId") Long id) {
        toimittajaService.poistaToimittaja(id);
        return "redirect:/toimittajat";
    }


// KAHVILAITTEET

@RequestMapping("/kahvilaitteet")
public String naytaEkaSivu(Model model) {
    return naytaKahvilaitteet(model, 1);
}

@GetMapping("/kahvilaitteet/{sivunum}")
public String naytaKahvilaitteet(Model model, @PathVariable(name = "sivunum") Integer sivunum) {
    List<Tuote> tuotteet = new ArrayList<Tuote>();
    List<Long> kahvilaitteet = Arrays.asList(3L, 4L, 5L);
    Pageable pageable = PageRequest.of(sivunum - 1, 6);
    Page<Tuote> tuotesivut = tuoteService.listaaHalututTuotteet(kahvilaitteet, pageable);
    tuotteet = tuotesivut.getContent();
    Integer totalSivut = tuotesivut.getTotalPages();
    if (sivunum >= 1 && sivunum < totalSivut) {
        model.addAttribute("seuraava", sivunum + 1);
    }

    if (sivunum > 1 && sivunum <= totalSivut) {
        model.addAttribute("edellinen", sivunum - 1);
    }

    if (sivunum >= 1 && sivunum <= totalSivut) {
        model.addAttribute("nykyinen", sivunum);
    }
    model.addAttribute("totalPages", totalSivut);
    model.addAttribute("totalItems", tuotesivut.getTotalElements());
    model.addAttribute("kahvilaitteet", tuotteet);
    return "kahvilaitteet";
}

@GetMapping(path = "/kahvilaitteet/{id}/content", produces = "image/jpg")
@ResponseBody
public byte[] getKuvat(@PathVariable Long id) {
    return tuoteService.getTuoteById(id).getKuva();
}

@GetMapping("/kahvilaitteet/tuotetiedot/{id}")
public String naytaKahvilaite(@PathVariable("id") long id, Model model) {
    Tuote tuote = tuoteService.getTuoteById(id);
    model.addAttribute("tuote", tuote);
    model.addAttribute("valmistajat", valmistajaService.listaaValmistajat());
    model.addAttribute("osastot", osastoService.listaaOsastot());
    model.addAttribute("toimittajat", toimittajaService.listaaToimittajat());
    return "tuotetiedot";
}


// KULUTUSTUOTTEET

@RequestMapping("/kulutustuotteet")
public String naytaKulutusEkaSivu(Model model) {
    return naytaKulutustuotteet(model, 1);
}

@GetMapping("/kulutustuotteet/{sivunum}")
public String naytaKulutustuotteet(Model model, @PathVariable(name = "sivunum") Integer sivunum) {
    List<Tuote> tuotteet = new ArrayList<Tuote>();
    List<Long> kulutustuotteet = Arrays.asList(6L, 8L, 9L);
    Pageable pageable = PageRequest.of(sivunum - 1, 12);
    Page<Tuote> tuotesivut = tuoteService.listaaHalututTuotteet(kulutustuotteet, pageable);
    tuotteet = tuotesivut.getContent();
    Integer totalSivut = tuotesivut.getTotalPages();
    if (sivunum >= 1 && sivunum < totalSivut) {
        model.addAttribute("seuraava", sivunum + 1);
    }

    if (sivunum > 1 && sivunum <= totalSivut) {
        model.addAttribute("edellinen", sivunum - 1);
    }

    if (sivunum >= 1 && sivunum <= totalSivut) {
        model.addAttribute("nykyinen", sivunum);
    }
    model.addAttribute("totalPages", totalSivut);
    model.addAttribute("totalItems", tuotesivut.getTotalElements());
    model.addAttribute("kulutustuotteet", tuotteet);
    return "kulutustuotteet";
}

@GetMapping(path = "/kulutustuotteet/{id}/content", produces = "image/jpg")
@ResponseBody
public byte[] getKuvatKulutustuotteet(@PathVariable Long id) {
    return tuoteService.getTuoteById(id).getKuva();
}

@GetMapping("/kulutustuotteet/tuotetiedot/{id}")
public String naytaKulutustuote(@PathVariable("id") long id, Model model) {
    Tuote tuote = tuoteService.getTuoteById(id);
    model.addAttribute("tuote", tuote);
    model.addAttribute("valmistajat", valmistajaService.listaaValmistajat());
    model.addAttribute("osastot", osastoService.listaaOsastot());
    model.addAttribute("toimittajat", toimittajaService.listaaToimittajat());
    return "tuotetiedot";
}

@GetMapping(path = "/tuotetiedot/{id}/content", produces = "image/jpg")
@ResponseBody
public byte[] getKuvaKulutustuote(@PathVariable Long id) {
    return tuoteService.getTuoteById(id).getKuva();
}

}