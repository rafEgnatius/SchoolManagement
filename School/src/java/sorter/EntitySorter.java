/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter;

import entity.AbstractEntity;
import entity.Firma;
import entity.Jezyk;
import entity.Lektor;
import finder.SchoolFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Rafa
 */
public class EntitySorter {

    public static List sortFirma(List<AbstractEntity> entityList) {
        // prepare list to be returned
        List<AbstractEntity> resultList = new ArrayList<>();

        // add in a loop entities to the result list
        while (!entityList.isEmpty()) {

            // find the smallest item in the list
            Firma firma = (Firma) SchoolFinder.findSmallestCustomer(entityList);
            // it should not be null because the list is not empty

            // create a new list that will contain all the firms except the first one
            List<AbstractEntity> auxiliaryList = new ArrayList<>(); // we recreate this  list on every loop

            // we have to make another loop finding only empty firma fields (null)
            if (firma == null) {
                for (AbstractEntity abstractEntity : entityList) {
                    if (abstractEntity.getFirma() == null) {
                        int index = entityList.indexOf(abstractEntity);
                        resultList.add(entityList.get(index)); // 
                    } else {
                        auxiliaryList.add(abstractEntity); // not firma!
                    }
                }
            } else {
                // add in a loop entities to the result list
                for (AbstractEntity abstractEntity : entityList) {
                    if (abstractEntity.getFirma().equals(firma)) {
                        int index = entityList.indexOf(abstractEntity);
                        resultList.add(entityList.get(index)); // 
                    } else {
                        auxiliaryList.add(abstractEntity); // not firma!
                    }
                }
            }

            entityList.clear(); // to make it smaller
            entityList.addAll(auxiliaryList); // by selecting only those that are not equal
        }
        return resultList;
    }

    public static List sortFirmaDesc(List<AbstractEntity> entityList) {

        // prepare list to be returned
        List<AbstractEntity> resultList = EntitySorter.sortFirma(entityList);

        Collections.reverse(resultList); // doing the same but the other way round
        return resultList;
    }

    public static List sortJezyk(List<AbstractEntity> entityList) {
        // prepare list to be returned
        List<AbstractEntity> resultList = new ArrayList<>();

        // add in a loop entities to the result list
        while (!entityList.isEmpty()) {

            // find the smallest item in the list
            Jezyk jezyk = (Jezyk) SchoolFinder.findSmallestLanguage(entityList);
            // it should not be null because the list is not empty

            // create a new list that will contain all the firms except the first one
            List<AbstractEntity> auxiliaryList = new ArrayList<>(); // we recreate this  list on every loop

            // add in a loop entities to the result list
            for (AbstractEntity abstractEntity : entityList) {
                if (abstractEntity.getJezyk().equals(jezyk)) {
                    int index = entityList.indexOf(abstractEntity);
                    resultList.add(entityList.get(index)); // tu jest problem
                } else {
                    auxiliaryList.add(abstractEntity); // not firma!
                }
            }

            entityList.clear(); // to make it smaller
            entityList.addAll(auxiliaryList); // by selecting only those that are not equal
        }
        return resultList;
    }

    public static List sortJezykDesc(List entityList) {
        // prepare list to be returned
        List<AbstractEntity> resultList = EntitySorter.sortJezyk(entityList);

        Collections.reverse(resultList); // doing the same but the other way round
        return resultList;
    }

    public static List sortLektor(List<AbstractEntity> entityList) {
        // prepare list to be returned
        List<AbstractEntity> resultList = new ArrayList<>();

        // add in a loop entities to the result list
        while (!entityList.isEmpty()) {

            // find the smallest item in the list
            Lektor lektor = (Lektor) SchoolFinder.findSmallestLector(entityList);
            // it should not be null because the list is not empty

            // create a new list that will contain all the firms except the first one
            List<AbstractEntity> auxiliaryList = new ArrayList<>(); // we recreate this  list on every loop

            // we have to make a loop finding only empty firma fields (null)
            if (lektor == null) {
                for (AbstractEntity abstractEntity : entityList) {
                    if (abstractEntity.getLektor() == null) {
                        int index = entityList.indexOf(abstractEntity);
                        resultList.add(entityList.get(index)); // 
                    } else {
                        auxiliaryList.add(abstractEntity); //
                    }
                }
            } else {
                // add in a loop entities to the result list
                for (AbstractEntity abstractEntity : entityList) {
                    if (abstractEntity.getLektor().equals(lektor)) {
                        int index = entityList.indexOf(abstractEntity);
                        resultList.add(entityList.get(index)); // tu jest problem
                    } else {
                        auxiliaryList.add(abstractEntity); //
                    }
                }
            }
            entityList.clear(); // to make it smaller
            entityList.addAll(auxiliaryList); // by selecting only those that are not equal
        }
        return resultList;
    }

    public static List sortLektorDesc(List entityList) {
        // prepare list to be returned
        List<AbstractEntity> resultList = EntitySorter.sortLektor(entityList);

        Collections.reverse(resultList); // doing the same but the other way round
        return resultList;
    }

}
