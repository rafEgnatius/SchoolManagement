/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finder;

import entity.AbstractEntity;
import entity.Faktura;
import entity.Firma;
import entity.Jezyk;
import entity.JezykLektora;
import entity.Kurs;
import entity.Lektor;
import entity.Podrecznik;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
public class SchoolFinder {

    // find by String:
    public static List findCourse(List kursList, String searchPhrase) {
        //        for every field in Lektor entity check whether it contains searchPhrase
        //        use toLower to find everything no matter wheter capital      
        List resultList = new ArrayList<>(); // to send back
        Iterator it = kursList.iterator();
        while (it.hasNext()) {
            Kurs kurs = (Kurs) it.next();
            if (kurs.getSymbol().toLowerCase().contains(searchPhrase.toLowerCase())
                    || kurs.getFirma().getNazwa().toLowerCase().contains(searchPhrase.toLowerCase())
                    || kurs.getLektor().getNazwa().toLowerCase().contains(searchPhrase.toLowerCase())
                    || kurs.getJezyk().getNazwa().toLowerCase().contains(searchPhrase.toLowerCase())) {
                resultList.add(kurs);
            }
        }
        return resultList;
    }

    // find by String:
    public static List<Lektor> findLector(List<Lektor> lektorList, String searchPhrase) {
        List resultList = new ArrayList<>(); // to send back
//        for every field in Lektor entity check whether it contains searchPhrase
//        use toLower to find everything no matter wheter capital      
        for (Lektor lektor : lektorList) {
            if (lektor.getNazwa().toLowerCase().contains(searchPhrase.toLowerCase())
                    || lektor.getEmail().toLowerCase().contains(searchPhrase.toLowerCase())
                    || lektor.getMiasto().toLowerCase().contains(searchPhrase.toLowerCase())
                    || lektor.getTelefon().toLowerCase().contains(searchPhrase.toLowerCase())) {
                resultList.add(lektor);
            }
        }
        return resultList;
    }

    public static List findFaktura(List fakturaList, List firmaList, String searchPhrase) {
        List resultList = new ArrayList<>(); // to send back

//        for every field in entity check whether it contains searchPhrase
//        use toLower to find everything no matter wheter capital      
        Iterator it = fakturaList.iterator();
        while (it.hasNext()) {
            Faktura faktura = (Faktura) it.next();
            if (faktura.getNumer().toLowerCase().contains(searchPhrase.toLowerCase())
                    || faktura.getOpis().toLowerCase().contains(searchPhrase.toLowerCase())
                    || faktura.getFirma().getNazwa().toLowerCase().contains(searchPhrase.toLowerCase())) {
                resultList.add(faktura);
            }
        }

        return resultList;
    }

    // find by String
    public static List findLanguage(List<Jezyk> jezykList, String searchPhrase) {
        List resultList = new ArrayList<>(); // to send back
//        for every field in entity check whether it contains searchPhrase
//        use toLower to find everything no matter wheter capital      
        for (Jezyk jezyk : jezykList) {
            if (jezyk.getNazwa().toLowerCase().contains(searchPhrase.toLowerCase())
                    || jezyk.getSymbol().toLowerCase().contains(searchPhrase.toLowerCase())) {
                resultList.add(jezyk);
            }
        }
        return resultList;
    }

    public static List findLanguageForCourse(List kursList, String searchOption) {

        //        for every entity
        //        find what option is needed
        List resultList = new ArrayList<>(); // to send back

        for (Iterator it = kursList.iterator(); it.hasNext();) {
            Kurs kurs = (Kurs) it.next();
            if (kurs.getJezyk().getId().toString().equals(searchOption)) {
                resultList.add(kurs);
            }
        }
        return resultList;
    }

//    // find by option:
//    public static List findLanguageForEntity(List podrecznikList, String searchOption) {
//        List resultList = new ArrayList<>(); // to send back
////        for every entity
////        check if its language equals with search option that was chosen
//        for (Iterator it = podrecznikList.iterator(); it.hasNext();) {
//            AbstractEntity entity = (AbstractEntity) it.next();
//            Jezyk jezyk = (Jezyk) entity.getJezyk();
//            if (jezyk.getId().toString().equals(searchOption)) {
//                resultList.add(entity);
//            }
//        }
//        return resultList;
//    }
    // find by option:
    public static List<Lektor> findLanguageForLector(List<Lektor> lektorList, List<JezykLektora> jezykLektoraList, String searchOption) {
        List resultList = new ArrayList<>(); // to send back
//        for every entity
//        find what languages he speaks
//        taking it from language lector list (jezykLektora)
//        check if this language is the same as searchLanguage

        for (Lektor lektor : lektorList) {
            for (JezykLektora jezykLektora : jezykLektoraList) {
                if (lektor.equals(jezykLektora.getLektor())) {
                    if (jezykLektora.getJezyk().getId().toString().equals(searchOption)) {
                        resultList.add(lektor);
                    }
                }
            }
        }
        return resultList;
    }

    // find by option:
    public static List findLanguageForTextbook(List podrecznikList, String searchOption) {
        List resultList = new ArrayList<>(); // to send back
//        for every entity
//        check if its language equals with search option that was chosen
        for (Iterator it = podrecznikList.iterator(); it.hasNext();) {
            Podrecznik podrecznik = (Podrecznik) it.next();
            if (podrecznik.getJezyk().getId().toString().equals(searchOption)) {
                resultList.add(podrecznik);
            }
        }
        return resultList;
    }

    // find by String:
    public static List findTextbook(List podrecznikList, String searchPhrase) {
//        for every entity

        List resultList = new ArrayList<>(); // to send back
        for (Iterator it = podrecznikList.iterator(); it.hasNext();) {
            Podrecznik podrecznik = (Podrecznik) it.next();
            if (podrecznik.getNazwa().toLowerCase().contains(searchPhrase.toLowerCase())
                    || podrecznik.getPoziom().toLowerCase().contains(searchPhrase.toLowerCase())) {
                resultList.add(podrecznik);
            }
        }
        return resultList;
    }

    public static Object findSmallestCustomer(List<AbstractEntity> entityList) {
        if (entityList.isEmpty()) {
            return null; // just in case - it will be easier to find any problems
        }
        // LET'S DO IT THIS WAY:
        // create list of entities
        List mainList = new ArrayList();
        for (AbstractEntity abstractEntity : entityList) {

            // and here we check if not null, meaning if set
            // because if not our dorting will produce null exception
            // this is why we return null when we find one
            if (abstractEntity.getFirma() == null) {
                return null;
            }
            // otherwise do as nothing has happened
            mainList.add(abstractEntity.getFirma());
        }
        // sort it
        mainList = FieldSorter.sortNazwa(mainList);
        // and get the first one
        return mainList.get(0);
    }

    public static Object findSmallestLanguage(List<AbstractEntity> entityList) {
        if (entityList.isEmpty()) {
            return null; // just in case - it will be easier to find any problems
        }
        // LET'S DO IT THIS WAY:
        // create list of entities
        List mainList = new ArrayList();
        for (AbstractEntity abstractEntity : entityList) {
            mainList.add(abstractEntity.getJezyk());
        }
        // sort it
        mainList = FieldSorter.sortNazwa(mainList);
        // and get the first one
        return mainList.get(0);
    }

    public static Object findSmallestLector(List<AbstractEntity> entityList) {
        if (entityList.isEmpty()) {
            return null; // just in case - it will be easier to find any problems
        }
        // LET'S DO IT THIS WAY:
        // create list of entities
        List mainList = new ArrayList();
        for (AbstractEntity abstractEntity : entityList) {

            // and here we check if not null, meaning if set
            // because if not our dorting will produce null exception
            // this is why we return null when we find one
            if (abstractEntity.getLektor() == null) {
                return null;
            }
            // otherwise do as nothing has happened
            mainList.add(abstractEntity.getLektor());
        }
        // sort it
        mainList = FieldSorter.sortNazwa(mainList);
        // and get the first one
        return mainList.get(0);
    }

}
