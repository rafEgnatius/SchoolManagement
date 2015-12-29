/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.persistence;

import entity.Firma;
import entity.Jezyk;
import entity.JezykLektora;
import entity.JezykLektoraPK;
import entity.Kurs;
import entity.Lektor;
import entity.Podrecznik;
import entity.Program;
import entity.Wypozyczenie;
import entity.WypozyczeniePK;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.FirmaFacade;
import session.JezykFacade;
import session.KursFacade;
import session.LektorFacade;
import session.PodrecznikFacade;
import session.ProgramFacade;

/**
 * Simple class with methods creating new entities and saving them to database
 *
 * @author Rafa
 */
@Stateless
public class PersistenceManager {

    @PersistenceContext(unitName = "Szkola004PU")
    private EntityManager em;

    @EJB
    private FirmaFacade firmaFacade;
    
    @EJB
    private KursFacade kursFacade;
    
    @EJB
    private LektorFacade lektorFacade;

    @EJB
    private JezykFacade jezykFacade;
    
    @EJB
    private PodrecznikFacade podrecznikFacade;
    
    @EJB
    private ProgramFacade programFacade;
    

//    METHODS
    
    
    
    
    /**
     * Handles removal of the entity from the database
     * @param lektor Lektor
     * @param podrecznik Podrecznik
     */
    public void deleteBorrowingFromDatabase(Lektor lektor, Podrecznik podrecznik) {

        // to remove we probably have to find a proper primary key object
        // then try to remove an object with it
        // will have to check this solution - if we can set the same language for this lector
        // set up primary key object
        WypozyczeniePK wypozyczeniePK = new WypozyczeniePK(lektor.getId(), podrecznik.getId());
        Wypozyczenie wypozyczenie = em.find(Wypozyczenie.class, wypozyczeniePK);

        em.remove(wypozyczenie);

    }

    /**
     *
     *
     *
     */
    public void deleteLectorsLanguageFromDatabase(Lektor lektor, Jezyk jezyk) {

        // to remove we probably have to find a proper primary key object
        // then try to remove an object with it
        // will have to check this solution - if we can set the same language for this lector
        // set up primary key object
        JezykLektoraPK jezykLektoraPK = new JezykLektoraPK(lektor.getId(), jezyk.getId());
        JezykLektora jezykLektora = em.find(JezykLektora.class, jezykLektoraPK);

        em.remove(jezykLektora);
    }

    public void deleteCustomerCourseFromDatabase(String kursId) {
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later
        
        kurs.setFirmaId(null);
        em.flush();
    }
    
    public void deleteLectorFromCourseFromDatabase(String kursId) {
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later
        
        kurs.setLektorId(null);
        em.flush();
    }
    
     public void deleteProgrammeCourseFromDatabase(String kursId) {
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later
        
        kurs.setProgram(null);
        em.flush();
    }

    public void saveAddingCustomerToCourseToDatabase(String firmaId, String kursId) {
        Firma firma = firmaFacade.find(Integer.parseInt(firmaId)); // we should try/catch it later
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later
        
        kurs.setFirmaId(firma);
        em.flush();
    }
    
    public void saveAddingLectorToCourseToDatabase(String lektorId, String kursId) {
        Lektor lektor = lektorFacade.find(Integer.parseInt(lektorId)); // we should try/catch it later
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later
        
        kurs.setLektorId(lektor);
        em.flush();
    }
    
    
    public void saveAddingProgrammeToCourseToDatabase(String programId, String kursId) {
        Program program = programFacade.find(Integer.parseInt(programId)); // we should try/catch it later
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later
        
        kurs.setProgram(program);
        em.flush();
    }
    
    
    /**
     *
     * @param lektor
     * @param podrecznik
     */
    public void saveBorrowingToDatabase(Lektor lektor, Podrecznik podrecznik) {

        // set up primary key object
        WypozyczeniePK wypozyczeniePK = new WypozyczeniePK(podrecznik.getId(), lektor.getId()); // watch the order -> podrecznik first, then lektor

        // create item using PK object
        Wypozyczenie wypozyczenie = new Wypozyczenie(wypozyczeniePK);

        // set
        wypozyczenie.setLektor(lektor);

        // set
        wypozyczenie.setPodrecznik(podrecznik);
        
        // set
        wypozyczenie.setData(LocalDate.now());

        em.persist(wypozyczenie);
    }
    
    
    public int saveCourseToDatabase(String id, String rok, String semestr, Jezyk jezyk, String symbol, String opis, String sala) {
        Kurs kurs; // we have to check whether creating or editing
        if (id.equals("-1")) {
            kurs = new Kurs(); // new one
        } else {
            kurs = kursFacade.find(Integer.parseInt(id)); // existing one
        }

        kurs.setRok(rok);
        kurs.setSemestr(semestr);
        kurs.setJezyk(jezyk);
        kurs.setSymbol(symbol);
        kurs.setOpis(opis);
        kurs.setSala(sala);

        em.persist(kurs);
        em.flush();

        return kurs.getId();
    }
    
    
    
     /**
     * 
     * 
     * @param id
     * @param nip
     * @param nazwa
     * @param symbol
     * @param komorka
     * @param miasto
     * @param osoba
     * @param email
     * @param telefon
     * @param adres
     * @return 
     */
    public int saveCustomerToDatabase(String id, String nip, String nazwa, String symbol, String miasto, String osoba, String telefon, String komorka, String email, String adres) {
        
        Firma firma; // we have to check whether creating or editing
        if (id.equals("-1")) {
            firma = new Firma(); // new one
        } else {
            firma = firmaFacade.find(Integer.parseInt(id)); // existing one
        }

        firma.setNip(nip);
        firma.setNazwa(nazwa);
        firma.setSymbol(symbol);
        firma.setMiasto(miasto);
        firma.setOsoba(osoba);
        firma.setTelefon(telefon);
        firma.setKomorka(komorka);
        firma.setEmail(email);
        firma.setAdres(adres);

        em.persist(firma);
        em.flush();

        return firma.getId();
    }
    

    /**
     * 
     * @param id
     * @param nazwa
     * @param symbol
     * @return 
     */
    public int saveLanguageToDatabase(String id, String nazwa, String symbol) {
        Jezyk jezyk; // we have to check whether creating or editing
        if (id.equals("-1")) {
            jezyk = new Jezyk(); // new one
        } else {
            jezyk = jezykFacade.find(Integer.parseInt(id)); // existing one
        }

        jezyk.setNazwa(nazwa);
        jezyk.setSymbol(symbol);

        em.persist(jezyk);
        em.flush();

        return jezyk.getId();
    }

    /**
     *
     *
     *
     */
    public int saveLectorToDatabase(String id, String nazwa, String miasto, String telefon, String email, String umowa, String nip) {
        Lektor lektor; // we have to check whether creating or editing
        if (id.equals("-1")) {
            lektor = new Lektor(); // new one
        } else {
            lektor = lektorFacade.find(Integer.parseInt(id)); // existing one
        }

        lektor.setNazwa(nazwa);
        lektor.setMiasto(miasto);
        lektor.setTelefon(telefon);
        lektor.setEmail(email);
        lektor.setUmowa(umowa);
        lektor.setNip(nip);

        em.persist(lektor);
        em.flush();

        return lektor.getId();
    }

    /**
     *
     *
     *
     */
    public void saveLectorsLanguageToDatabase(Lektor lektor, Jezyk jezyk, boolean nativeSpeaker) {

        // set up primary key object
        JezykLektoraPK jezykLektoraPK = new JezykLektoraPK(lektor.getId(), jezyk.getId());

        // create item using PK object
        JezykLektora jezykLektora = new JezykLektora(jezykLektoraPK);

        // set
        jezykLektora.setLektor(lektor);

        // set
        jezykLektora.setJezyk(jezyk);

        // set additional field
        jezykLektora.setNatywny(nativeSpeaker);

        em.persist(jezykLektora);
    }
    
    public int saveProgrammeToDatabase(String id, String referencja, String metoda) {
        Program program; // we have to check whether creating or editing
        if (id.equals("-1")) {
            program = new Program(); // new one
        } else {
            program = programFacade.find(Integer.parseInt(id)); // existing one
        }

        program.setReferencja(referencja);
        program.setMetoda(metoda);

        em.persist(program);
        em.flush();

        return program.getId();
    }

    public int saveTextbookToDatabase(String id, String nazwa, String poziom, Jezyk jezyk) {
        
        Podrecznik podrecznik; // we have to check whether creating or editing
        if (id.equals("-1")) {
            podrecznik = new Podrecznik(); // new one
        } else {
            podrecznik = podrecznikFacade.find(Integer.parseInt(id)); // existing one
        }

        podrecznik.setNazwa(nazwa);
        podrecznik.setPoziom(poziom);
        podrecznik.setJezyk(jezyk);

        em.persist(podrecznik);
        em.flush();

        return podrecznik.getId();
    }

   

    

    


}
