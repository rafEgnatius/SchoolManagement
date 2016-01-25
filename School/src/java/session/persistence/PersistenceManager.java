/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.persistence;

import entity.Ankieta;
import entity.Faktura;
import entity.Firma;
import entity.Jezyk;
import entity.JezykKursanta;
import entity.JezykKursantaPK;
import entity.JezykLektora;
import entity.JezykLektoraPK;
import entity.Kurs;
import entity.KursKursanta;
import entity.KursKursantaPK;
import entity.Kursant;
import entity.Lekcja;
import entity.Lektor;
import entity.Obecnosc;
import entity.ObecnoscPK;
import entity.Podrecznik;
import entity.Program;
import entity.Rachunek;
import entity.StawkaFirmy;
import entity.StawkaFirmyPK;
import entity.StawkaLektora;
import entity.StawkaLektoraPK;
import entity.Termin;
import entity.Test;
import entity.Wplata;
import entity.Wyplata;
import entity.Wypozyczenie;
import entity.WypozyczeniePK;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.AnkietaFacade;
import session.FakturaFacade;
import session.FirmaFacade;
import session.JezykFacade;
import session.KursFacade;
import session.KursKursantaFacade;
import session.KursantFacade;
import session.LekcjaFacade;
import session.LektorFacade;
import session.PodrecznikFacade;
import session.ProgramFacade;
import session.RachunekFacade;
import session.TerminFacade;
import session.TestFacade;
import session.WplataFacade;
import session.WyplataFacade;

/**
 * Simple class with methods creating new entities and saving them to database
 *
 * @author Rafa
 */
@Stateless
public class PersistenceManager {

    @PersistenceContext(unitName = "SchoolPU")
    private EntityManager em;

    @EJB
    private AnkietaFacade ankietaFacade;
    
    @EJB
    private FakturaFacade fakturaFacade;

    @EJB
    private FirmaFacade firmaFacade;

    @EJB
    private KursFacade kursFacade;

    @EJB
    private KursKursantaFacade kursKursantaFacade;

    @EJB
    private LektorFacade lektorFacade;

    @EJB
    private JezykFacade jezykFacade;

    @EJB
    private KursantFacade kursantFacade;

    @EJB
    private LekcjaFacade lekcjaFacade;

    @EJB
    private PodrecznikFacade podrecznikFacade;

    @EJB
    private ProgramFacade programFacade;

    @EJB
    private RachunekFacade rachunekFacade;

    @EJB
    private TerminFacade terminFacade;

    @EJB
    private WplataFacade wplataFacade;

    @EJB
    private WyplataFacade wyplataFacade;

    @EJB
    private TestFacade testFacade;

//    METHODS
    /**
     * Handles removal of the entity from the database
     *
     * @param intLektorId
     * @param intPodrecznikId
     */
    public void deleteBorrowingFromDatabase(int intLektorId, int intPodrecznikId) {

        // to remove we probably have to find a proper primary key object
        // then try to remove an object with it
        // will have to check this solution - if we can set the same language for this lector
        // set up primary key object
        WypozyczeniePK wypozyczeniePK = new WypozyczeniePK(intPodrecznikId, intLektorId);
        Wypozyczenie wypozyczenie = em.find(Wypozyczenie.class, wypozyczeniePK);

        em.remove(wypozyczenie);

    }

    public void deleteLectorRateFromDatabase(int intKursId, int intLectorId) {

        StawkaLektoraPK stawkaLektoraPK = new StawkaLektoraPK(intKursId, intLectorId); // false because not native
        StawkaLektora stawkaLektora = em.find(StawkaLektora.class, stawkaLektoraPK);

        em.remove(stawkaLektora);
    }

    public void deleteCustomerLectorRateFromDatabase(int mainEntityId) {
        StawkaFirmyPK stawkaFirmyPK = new StawkaFirmyPK(mainEntityId, false); // false because not native
        StawkaFirmy stawkaFirmy = em.find(StawkaFirmy.class, stawkaFirmyPK);

        em.remove(stawkaFirmy);
    }

    public void deleteCustomerNativeSpeakerRateFromDatabase(int mainEntityId) {

        StawkaFirmyPK stawkaFirmyPK = new StawkaFirmyPK(mainEntityId, true); // true because native
        StawkaFirmy stawkaFirmy = em.find(StawkaFirmy.class, stawkaFirmyPK);

        em.remove(stawkaFirmy);
    }

    public void deleteCustomerFromCourseFromDatabase(String mainEntityId) {
        Kurs kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later

        kurs.setFirmaId(null);
        em.flush();

        // we should also delete all participants
        List<KursKursanta> kursKursantaList = kursKursantaFacade.findAll();
        kursKursantaList.stream().filter((kursKursanta) -> (kursKursanta.getKurs().equals(kurs))).forEach((kursKursanta) -> {
            em.remove(kursKursanta);
        });

        // and Day and Hour (Termin) for this course
        List<Termin> terminList = terminFacade.findAll();
        terminList.stream().filter((termin) -> (termin.getKurs().equals(kurs))).forEach((termin) -> {
            em.remove(termin);
        });

    }

    public void deleteDayAndTimeFromCourseFromDatabase(String terminId) {
        Termin termin = terminFacade.find(Integer.parseInt(terminId));
        em.remove(termin);
    }

    public void deleteLectorFromCourseFromDatabase(String kursId) {
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later

        kurs.setLektorId(null);
        em.flush();
    }

    /**
     *
     *
     *
     * @param lektor
     * @param jezyk
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

    public void deleteParticipantFromDatabase(String mainEntityId, String kursantId) {

        Kursant kursant = kursantFacade.find(Integer.parseInt(kursantId));
        Kurs kurs = kursFacade.find(Integer.parseInt(mainEntityId));

        List<KursKursanta> kursKursantaList = kursKursantaFacade.findAll();
        kursKursantaList.stream().filter((kursKursanta) -> (kursKursanta.getKurs().equals(kurs) && kursKursanta.getKursant().equals(kursant))).forEach((kursKursanta) -> {
            em.remove(kursKursanta);
        });
    }

    public void deleteParticipantsLanguageFromDatabase(Kursant mainEntity, Jezyk jezyk) {
        JezykKursantaPK jezykKursantaPK = new JezykKursantaPK(mainEntity.getId(), jezyk.getId());
        JezykKursanta jezykLektora = em.find(JezykKursanta.class, jezykKursantaPK);

        em.remove(jezykLektora);
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

    public void saveAddingDayAndTimeToCourseToDatabase(String mainEntityId, String dzien, Calendar godzinaStart, Calendar godzinaStop) {
        Kurs kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
        Termin termin = new Termin();

        termin.setDzien(dzien);
        termin.setGodzinaStart(godzinaStart);
        termin.setGodzinaStop(godzinaStop);
        termin.setKurs(kursFacade.find(Integer.parseInt(mainEntityId)));

        em.persist(termin);
        em.flush();
    }

    public void saveAddingLectorToCourseToDatabase(String lektorId, String kursId) {
        Lektor lektor = lektorFacade.find(Integer.parseInt(lektorId)); // we should try/catch it later
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId)); // we should try/catch it later

        kurs.setLektorId(lektor);
        em.flush();
    }

    public void saveAddingParticipantToCourseToDatabase(Kurs kurs, Kursant kursant) {

        // set up primary key object
        KursKursantaPK kursKursantaPK = new KursKursantaPK(kurs.getId(), kursant.getId()); // watch the order -> kurs first, then kursant

        // create item using PK object
        KursKursanta kursKursanta = new KursKursanta(kursKursantaPK);

        // set
        kursKursanta.setKurs(kurs);

        // set
        kursKursanta.setKursant(kursant);

        // set
        kursKursanta.setOpis("brak");

        em.persist(kursKursanta);
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
     * @param id
     * @param numer
     * @param data
     * @param opis
     * @param lektor
     * @param kwota
     * @return
     */
    public int saveBillToDatabase(String id, String numer, String data, String kwota, String opis, Lektor lektor) {
        Rachunek rachunek; // we have to check whether creating or editing
        if (id.equals("-1")) {
            rachunek = new Rachunek(); // new one
        } else {
            rachunek = rachunekFacade.find(Integer.parseInt(id)); // existing one
        }

        rachunek.setNumer(numer);
        rachunek.setData(LocalDate.parse(data)); // it should be ok at this point
        rachunek.setKwota(new BigDecimal(kwota).setScale(2, RoundingMode.HALF_DOWN));
        rachunek.setOpis(opis);
        rachunek.setLektor(lektor);

        em.persist(rachunek);
        em.flush();

        return rachunek.getId();
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

    public void saveCustomerLectorRateToDatabase(int mainEntityId, BigDecimal bigDecimalKwota) {

        StawkaFirmyPK stawkaFirmyPK = new StawkaFirmyPK(mainEntityId, false); // false because not native
        StawkaFirmy stawkaFirmy = new StawkaFirmy(stawkaFirmyPK);

        stawkaFirmy.setFirma(firmaFacade.find(mainEntityId));
        stawkaFirmy.setStawka(bigDecimalKwota);
        em.persist(stawkaFirmy);
    }

    public void saveCustomerNativeSpeakerRateToDatabase(int mainEntityId, BigDecimal bigDecimalKwota) {

        StawkaFirmyPK stawkaFirmyPK = new StawkaFirmyPK(mainEntityId, true); // true becausee native
        StawkaFirmy stawkaFirmy = new StawkaFirmy(stawkaFirmyPK);

        stawkaFirmy.setFirma(firmaFacade.find(mainEntityId));
        stawkaFirmy.setStawka(bigDecimalKwota);
        em.persist(stawkaFirmy);
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

    public int saveInvoiceToDatabase(String id, String numer, String data, String kwota, String opis, Firma firma) {
        Faktura faktura; // we have to check whether creating or editing
        if (id.equals("-1")) {
            faktura = new Faktura(); // new one
        } else {
            faktura = fakturaFacade.find(Integer.parseInt(id)); // existing one
        }

        faktura.setNumer(numer);
        faktura.setData(LocalDate.parse(data)); // it should be ok at this point
        faktura.setKwota(new BigDecimal(kwota).setScale(2, RoundingMode.HALF_DOWN));
        faktura.setOpis(opis);
        faktura.setFirma(firma);

        em.persist(faktura);
        em.flush();

        return faktura.getId();
    }

    public int saveLanguageTestToDatabase(String id, String rodzaj, Integer ocena, Kurs kurs, Kursant kursant) {
        Test test; // we have to check whether creating or editing
        if (id.equals("-1")) {
            test = new Test(); // new one
        } else {
            test = testFacade.find(Integer.parseInt(id)); // existing one
        }

        test.setRodzaj(rodzaj);
        test.setOcena(ocena);
        test.setKurs(kurs);
        test.setKursant(kursant);

        em.persist(test);
        em.flush();

        return test.getId();
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

    public void saveLectorRateToDatabase(int intKursId, int intLectorId, BigDecimal bigDecimalAmount) {
        StawkaLektoraPK stawkaLektoraPK = new StawkaLektoraPK(intKursId, intLectorId); // false because not native
        StawkaLektora stawkaLektora = new StawkaLektora(stawkaLektoraPK);

        stawkaLektora.setKurs(kursFacade.find(intKursId));
        stawkaLektora.setLektor(lektorFacade.find(intLectorId));
        stawkaLektora.setStawka(bigDecimalAmount);
        em.persist(stawkaLektora);
    }

    /**
     * @param id
     * @param nazwa
     * @param miasto
     * @param telefon
     * @param email
     * @param umowa
     * @param nip
     * @return
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
     * @param lektor
     * @param jezyk
     * @param nativeSpeaker
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

    public int saveLessonToDatabase(String id, String data, boolean parseBoolean, Kurs find) {

        Lekcja lekcja; // we have to check whether creating or editing
        if (id.equals("-1")) {
            lekcja = new Lekcja(); // new one
        } else {
            lekcja = lekcjaFacade.find(Integer.parseInt(id)); // existing one
        }

        lekcja.setData(LocalDate.parse(data)); // it should be ok at this point
        lekcja.setOdwolana(parseBoolean);
        lekcja.setKurs(find);

        em.persist(lekcja);
        em.flush();
        
        return lekcja.getId();
    }

    public int saveMoneyInToDatabase(String id, String data, String kwota, String opis, Firma firma) {
        Wplata wplata; // we have to check whether creating or editing
        if (id.equals("-1")) {
            wplata = new Wplata(); // new one
        } else {
            wplata = wplataFacade.find(Integer.parseInt(id)); // existing one
        }

        wplata.setData(LocalDate.parse(data)); // it should be ok at this point
        wplata.setKwota(new BigDecimal(kwota).setScale(2, RoundingMode.HALF_DOWN));
        wplata.setOpis(opis);
        wplata.setFirma(firma);

        em.persist(wplata);
        em.flush();

        return wplata.getId();
    }

    public int saveMoneyOutToDatabase(String id, String data, String kwota, String opis, Lektor lektor) {
        Wyplata wyplata; // we have to check whether creating or editing
        if (id.equals("-1")) {
            wyplata = new Wyplata(); // new one
        } else {
            wyplata = wyplataFacade.find(Integer.parseInt(id)); // existing one
        }

        wyplata.setData(LocalDate.parse(data)); // it should be ok at this point
        wyplata.setKwota(new BigDecimal(kwota).setScale(2, RoundingMode.HALF_DOWN));
        wyplata.setOpis(opis);
        wyplata.setLektor(lektor);

        em.persist(wyplata);
        em.flush();

        return wyplata.getId();
    }

    public int saveParticipantToDatabase(String id, String nazwa, String telefon, String email, Firma firma) {

        Kursant kursant; // we have to check whether creating or editing
        if (id.equals("-1")) {
            kursant = new Kursant(); // new one
        } else {
            kursant = kursantFacade.find(Integer.parseInt(id)); // existing one
        }

        kursant.setNazwa(nazwa);
        kursant.setTelefon(telefon);
        kursant.setEmail(email);
        kursant.setFirma(firma);

        em.persist(kursant);
        em.flush();

        return kursant.getId();

    }

    public void saveParticipantsLanguageToDatabase(Kursant mainEntity, Jezyk jezyk, String poziom) {

        // set up primary key object
        JezykKursantaPK jezykKursantaPK = new JezykKursantaPK(mainEntity.getId(), jezyk.getId());

        // create item using PK object
        JezykKursanta jezykKursanta = new JezykKursanta(jezykKursantaPK);

        // set
        jezykKursanta.setKursant(mainEntity);

        // set
        jezykKursanta.setJezyk(jezyk);

        // set additional field
        jezykKursanta.setPoziom(poziom);

        em.persist(jezykKursanta);

    }

    public Obecnosc savePresence(boolean isNew, Lekcja lekcja, Kursant kursant, boolean obecny) {

        ObecnoscPK obecnoscPK = new ObecnoscPK(lekcja.getId(), kursant.getId());
        Obecnosc obecnosc;

        // if new we create, else we find
        if (isNew) {
            obecnosc = new Obecnosc(obecnoscPK);
            obecnosc.setLekcja(lekcja);
            obecnosc.setKursant(kursant);
        } else {
            obecnosc = em.find(Obecnosc.class, obecnoscPK);
        }
        
        // set if is present
        obecnosc.setObecny(obecny);
        
        em.persist(obecnosc);
        em.flush();
        
        // and return it
        return obecnosc;
    }

    public boolean saveQuestionnaire(String kursantId, String lektorId, List answerList, String answer23) {
        boolean isAlreadyThere = false; // this one we will return
        
        // create new Ankieta entity
        Ankieta ankieta = new Ankieta();
        
        // set all the fields necessary to check if questionnaire was already filled
        ankieta.setKursant(kursantFacade.find(Integer.parseInt(kursantId)));
        ankieta.setLektor(lektorFacade.find(Integer.parseInt(lektorId)));
        ankieta.setData(LocalDate.now());
        
        for (Ankieta next : ankietaFacade.findAll()) {
            if (next.equals(ankieta))
                isAlreadyThere = true;
        }
        if (!isAlreadyThere) {
            // if no, we can set the other fields and persist
            if (!ankieta.setAllBooleans(answerList)) {
                return true;
            }
            ankieta.setPytanieOpisowe23(answer23);
            
            em.persist(ankieta);
        }
            
        return isAlreadyThere;
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
