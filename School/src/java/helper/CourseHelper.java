/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Firma;
import entity.Kurs;
import entity.KursKursanta;
import entity.Kursant;
import entity.Termin;
import finder.SchoolFinder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.FirmaFacade;
import session.JezykFacade;
import session.KursFacade;
import session.KursKursantaFacade;
import session.KursantFacade;
import session.LektorFacade;
import session.StawkaFirmyFacade;
import session.StawkaLektoraFacade;
import session.TerminFacade;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class CourseHelper {

    @EJB
    FirmaFacade firmaFacade;
    
    @EJB
    JezykFacade jezykFacade;
    
    @EJB
    KursFacade kursFacade;

    @EJB
    KursantFacade kursantFacade;

    @EJB
    KursKursantaFacade kursKursantaFacade;
    
    @EJB
    LektorFacade lektorFacade;

    @EJB
    StawkaFirmyFacade stawkaFirmyFacade;

    @EJB
    StawkaLektoraFacade stawkaLektoraFacade;

    @EJB
    TerminFacade terminFacade;
    
    @Resource(name="pageSize")
    int pageSize;

    public boolean alreadyThere(int kursId, int kursantId) {

        List kursKursantaList = kursKursantaFacade.findAll();
        Kurs kurs = kursFacade.find(kursId);
        Kursant kursant = kursantFacade.find(kursantId);

        Iterator it = kursKursantaList.iterator();
        while (it.hasNext()) {
            KursKursanta kursKursanta = (KursKursanta) it.next();
            if (kursKursanta.getKurs().equals(kurs) && kursKursanta.getKursant().equals(kursant)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param mainEntityList
     * @param kursId
     * @return 
     */
    private List filterKursForTerminList(List mainEntityList, String kursId) {
        List resultList = new ArrayList();
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId));

        Iterator it = mainEntityList.iterator();
        while (it.hasNext()) {
            Termin termin = (Termin) it.next();
            if (termin.getKurs().equals(kurs)) {
                resultList.add(termin);
            }
        }
        return resultList;
    }

    /**
     * 
     * @param request
     * @return 
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request) {

        /* main lists that we will use */
        List kursList = kursFacade.findAll();
        List lektorList = lektorFacade.findAll();
        List firmaList = firmaFacade.findAll();
        List jezykList = jezykFacade.findAll();

        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number
        String searchPhrase; // what we are looking for
        String searchOption; // option that is chosen by user when searchin
        
        /* and now process */
        // SORT & SEARCH
        
        // get pageNumber from request
        try {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        } catch (NumberFormatException e) {
            pageNumber = 1; // (it seems that we do not have page number yet)
        }
        
        // check whether to change sorting direction
        changeSort = Boolean.parseBoolean(request.getParameter("changeSort"));
        String stringSortAsc = request.getParameter("sortAsc");

        try {
            sortAsc = Boolean.parseBoolean(stringSortAsc);
        } catch (Exception e) {
            sortAsc = true;
        }

        // check if sorting...
        sortBy = request.getParameter("sortBy");
        // if not sorting let's sort by id
        if (sortBy == null) {
            sortBy = "id";
            sortAsc = true; // to start from the beginning
            changeSort = false;
        }

        // and check if searching
        searchPhrase = request.getParameter("searchPhrase");
        if (searchPhrase != null && !searchPhrase.equals("")) {
            kursList = SchoolFinder.findCourse(kursList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // and what option was chosen
        searchOption = request.getParameter("searchOption");
        if (searchOption != null && !searchOption.equals("")) {
            kursList = SchoolFinder.findLanguageForCourse(kursList, searchOption);
        } else {
            searchOption = "";
        }

                // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursList = FieldSorter.sortIdDesc(kursList);
                    sortAsc = false;
                } else {
                    kursList = FieldSorter.sortId(kursList);
                    sortAsc = true;
                }
                break;
            case ("symbol"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursList = FieldSorter.sortSymbolDesc(kursList);
                    sortAsc = false;
                } else {
                    kursList = FieldSorter.sortSymbol(kursList);
                    sortAsc = true;
                }
                break;
            case ("firma"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursList = EntitySorter.sortFirmaDesc(kursList);
                    sortAsc = false;
                } else {
                    kursList = EntitySorter.sortFirma(kursList);
                    sortAsc = true;
                }
                break;
            case ("lektor"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursList = EntitySorter.sortLektorDesc(kursList);
                    sortAsc = false;
                } else {
                    kursList = EntitySorter.sortLektor(kursList);
                    sortAsc = true;
                }
                break;
            case ("jezyk"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursList = EntitySorter.sortJezykDesc(kursList);
                    sortAsc = false;
                } else {
                    kursList = EntitySorter.sortJezyk(kursList);
                    sortAsc = true;
                }
                break;
        }

                // PAGINATE
        // and here goes pagination part
        numberOfPages = ((kursList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = kursList.subList(fromIndex,
                toIndex > kursList.size() ? kursList.size() : toIndex);

                // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);
        request.setAttribute("searchOption", searchOption);

        // set main list
        request.setAttribute("kursList", resultList);

        // set additional lists
        request.setAttribute("firmaList", firmaList);
        request.setAttribute("jezykList", jezykList);
        request.setAttribute("lektorList", lektorList);

        return request;
    }

    /**
     * 
     * @param request
     * @param mainEntityId
     * @return 
     */
    public HttpServletRequest prepareEntityView(HttpServletRequest request, String mainEntityId) {

        // set attributes
        int i = Integer.parseInt(mainEntityId);
        Kurs kurs = kursFacade.find(i); // we should try/catch it later
        request.setAttribute("kurs", kurs);

        Firma firma = kurs.getFirma();
        request.setAttribute("firma", firma);

        // if firma is null there is no point...
        if (firma != null) {
            // first find kursKursantaList with only those records that match specific course
            List kursantList = new ArrayList();
            kursKursantaFacade.findAll().stream().filter((kursKursanta) -> (kursKursanta.getKurs().equals(kurs))).forEach((kursKursanta) -> {
                // than add participants that are on this short list
                kursantList.add(kursKursanta.getKursant());
            });
            // and set the final list as an attribute
            request.setAttribute("kursantList", kursantList);
        }

        // and set another attributes
        List stawkaFirmyList = stawkaFirmyFacade.findAll();
        request.setAttribute("stawkaFirmyList", stawkaFirmyList);

        List stawkaLektoraList = stawkaLektoraFacade.findAll();
        request.setAttribute("stawkaLektoraList", stawkaLektoraList);

        List terminList = terminFacade.findAll(); // and filter this list
        terminList = filterKursForTerminList(terminList, mainEntityId);
        request.setAttribute("terminList", terminList);

        return request;
    }

}
