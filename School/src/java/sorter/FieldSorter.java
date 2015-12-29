/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter;

import entity.AbstractEntity;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to sort any Entity List - sort by all the fields this is
 * why entities inherit AbstractEntity
 *
 * @author Rafa
 */
public class FieldSorter {

    public static List<AbstractEntity> sortData(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getData().compareTo(p2.getData()));
        return entityList;
    }

    public static List<AbstractEntity> sortDataDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getData().compareTo(p1.getData()));
        return entityList;
    }

    public static List<AbstractEntity> sortEmail(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getEmail().compareTo(p2.getEmail()));
        return entityList;
    }

    public static List<AbstractEntity> sortEmailDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getEmail().compareTo(p1.getEmail()));
        return entityList;
    }

    public static List<AbstractEntity> sortId(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getId().compareTo(p2.getId()));
        return entityList;
    }

    public static List<AbstractEntity> sortIdDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getId().compareTo(p1.getId()));
        return entityList;
    }

    public static List<AbstractEntity> sortKwota(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getKwota().compareTo(p2.getKwota()));
        return entityList;
    }

    public static List<AbstractEntity> sortKwotaDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getKwota().compareTo(p1.getKwota()));
        return entityList;
    }

    public static List<AbstractEntity> sortMetoda(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getMetoda().compareTo(p2.getMetoda()));
        return entityList;
    }

    public static List<AbstractEntity> sortMetodaDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getMetoda().compareTo(p1.getMetoda()));
        return entityList;
    }

    public static List<AbstractEntity> sortMiasto(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getMiasto().compareTo(p2.getMiasto()));
        return entityList;
    }

    public static List<AbstractEntity> sortMiastoDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getMiasto().compareTo(p1.getMiasto()));
        return entityList;
    }

    public static List<AbstractEntity> sortNazwa(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getNazwa().compareTo(p2.getNazwa()));
        return entityList;
    }

    public static List<AbstractEntity> sortNazwaDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getNazwa().compareTo(p1.getNazwa()));
        return entityList;
    }

    public static List<AbstractEntity> sortNumer(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getNumer().compareTo(p2.getNumer()));
        return entityList;
    }

    public static List<AbstractEntity> sortNumerDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getNumer().compareTo(p1.getNumer()));
        return entityList;
    }

    public static List<AbstractEntity> sortPoziom(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getPoziom().compareTo(p2.getPoziom()));
        return entityList;
    }

    public static List<AbstractEntity> sortPoziomDesc(List<AbstractEntity> entityList) {
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getPoziom().compareTo(p1.getPoziom()));
        return entityList;
    }

    public static List<AbstractEntity> sortReferencja(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getReferencja().compareTo(p2.getReferencja()));
        return entityList;
    }

    public static List<AbstractEntity> sortReferencjaDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getReferencja().compareTo(p1.getReferencja()));
        return entityList;
    }

    public static List<AbstractEntity> sortSymbol(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getSymbol().compareTo(p2.getSymbol()));
        return entityList;
    }

    public static List<AbstractEntity> sortSymbolDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getSymbol().compareTo(p1.getSymbol()));
        return entityList;
    }

    public static List<AbstractEntity> sortTelefon(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p1.getTelefon().compareTo(p2.getTelefon()));
        return entityList;
    }

    public static List<AbstractEntity> sortTelefonDesc(List<AbstractEntity> entityList) {
        // lambda:
        Collections.sort(entityList, (AbstractEntity p1, AbstractEntity p2) -> p2.getTelefon().compareTo(p1.getTelefon()));
        return entityList;
    }

}
