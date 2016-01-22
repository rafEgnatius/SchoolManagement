/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Lektor;
import finder.SchoolFinder;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.JezykFacade;
import session.JezykLektoraFacade;
import session.LektorFacade;
import session.PodrecznikFacade;
import session.WypozyczenieFacade;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class LectorHelper {

    @EJB
    private LektorFacade lektorFacade;
    
    @EJB
    private JezykFacade jezykFacade;
    
    @EJB
    PodrecznikFacade podrecznikFacade;

    @EJB
    WypozyczenieFacade wypozyczenieFacade;

    @EJB
    private JezykLektoraFacade jezykLektoraFacade;
    
    @Resource(name = "pageSize")
    Integer pageSize;

    /**
     * Handles preparation of the lector list
     *
     * @param request
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request) {

        /* main lists that we will use */
        List lektorList = lektorFacade.findAll();
        List jezykList = jezykFacade.findAll();
        List jezykLektoraList = jezykLektoraFacade.findAll();

        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number
        String searchPhrase; // what we are looking for
        String searchOption;

        // sorting and pagination works this way:
        // if there is no sortBy it means we are here for the first time
        // so we check if we have pageNumber
        // if not it means that we are really for the first time here
        // let's get initial data...
        // ... like page number
        
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
            lektorList = SchoolFinder.findLector(lektorList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // and what option was chosen
        searchOption = request.getParameter("searchOption");
        if (searchOption != null && !searchOption.equals("")) {
            lektorList = SchoolFinder.findLanguageForLector(lektorList, jezykLektoraList, searchOption);
        } else {
            searchOption = "";
        }

        // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary abstract class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    lektorList = FieldSorter.sortIdDesc(lektorList);
                    sortAsc = false;
                } else {
                    lektorList = FieldSorter.sortId(lektorList);
                    sortAsc = true;
                }
                break;
            case ("nazwa"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    lektorList = FieldSorter.sortNazwaDesc(lektorList);
                    sortAsc = false;
                } else {
                    lektorList = FieldSorter.sortNazwa(lektorList);
                    sortAsc = true;
                }
                break;
            case ("miasto"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    lektorList = FieldSorter.sortMiastoDesc(lektorList);
                    sortAsc = false;
                } else {
                    lektorList = FieldSorter.sortMiasto(lektorList);
                    sortAsc = true;
                }
                break;
            case ("telefon"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    lektorList = FieldSorter.sortTelefonDesc(lektorList);
                    sortAsc = false;
                } else {
                    lektorList = FieldSorter.sortTelefon(lektorList);
                    sortAsc = true;
                }
                break;
            case ("email"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    lektorList = FieldSorter.sortEmailDesc(lektorList);
                    sortAsc = false;
                } else {
                    lektorList = FieldSorter.sortEmail(lektorList);
                    sortAsc = true;
                }
                break;
        }

                // PAGINATE
        // and here goes pagination part
        numberOfPages = ((lektorList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = lektorList.subList(fromIndex,
                toIndex > lektorList.size() ? lektorList.size() : toIndex);

                // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);
        
        request.setAttribute("searchPhrase", searchPhrase);
        request.setAttribute("searchOption", searchOption);

        request.setAttribute("lektorList", resultList);
        request.setAttribute("jezykLektoraList", jezykLektoraList); // we got it a couple of line earlier
        request.setAttribute("jezykList", jezykList);

        return request;
    }
    
    /**
     * This one prepares request to show one lector it is not to multiply code
     * when adding and showing new mainEntity entity
     * @param request
     * @param lektorId
     * @return 
     */
    public HttpServletRequest prepareEntityView(HttpServletRequest request, String lektorId) {

        Lektor lektor;
        List jezykList;
        List jezykLektoraList;
        List podrecznikList;
        List wypozyczenieList;

        lektor = lektorFacade.find(Integer.parseInt(lektorId));
        jezykList = jezykFacade.findAll();
        jezykLektoraList = jezykLektoraFacade.findAll();
        wypozyczenieList = wypozyczenieFacade.findAll();
        podrecznikList = podrecznikFacade.findAll();

        request.setAttribute("lektor", lektor);
        request.setAttribute("jezykList", jezykList);
        request.setAttribute("jezykLektoraList", jezykLektoraList);
        request.setAttribute("wypozyczenieList", wypozyczenieList);
        request.setAttribute("podrecznikList", podrecznikList);

        return request;
    }

}
