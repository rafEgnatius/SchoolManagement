/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import finder.SchoolFinder;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.LektorFacade;
import session.RachunekFacade;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class BillHelper {

    @EJB
    RachunekFacade rachunekFacade;

    @EJB
    LektorFacade lektorFacade;

    @Resource(name = "pageSize")
    Integer pageSize;

    /**
     * Handles preparation of the rachunek list
     *
     *
     * @param request
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request) {

        /* main lists that we will use */
        List rachunekList = rachunekFacade.findAll();
        List lektorList = lektorFacade.findAll();

        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number
        String searchPhrase; // what we are looking for

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
        if (searchPhrase
                != null && !searchPhrase.equals(
                        "")) {
            rachunekList = SchoolFinder.findRachunek(rachunekList, lektorList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary abstract class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    rachunekList = FieldSorter.sortIdDesc(rachunekList);
                    sortAsc = false;
                } else {
                    rachunekList = FieldSorter.sortId(rachunekList);
                    sortAsc = true;
                }
                break;
            case ("numer"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    rachunekList = FieldSorter.sortNumerDesc(rachunekList);
                    sortAsc = false;
                } else {
                    rachunekList = FieldSorter.sortNumer(rachunekList);
                    sortAsc = true;
                }
                break;
            case ("data"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    rachunekList = FieldSorter.sortDataDesc(rachunekList);
                    sortAsc = false;
                } else {
                    rachunekList = FieldSorter.sortData(rachunekList);
                    sortAsc = true;
                }
                break;
            case ("kwota"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    rachunekList = FieldSorter.sortKwotaDesc(rachunekList);
                    sortAsc = false;
                } else {
                    rachunekList = FieldSorter.sortKwota(rachunekList);
                    sortAsc = true;
                }
                break;
            case ("lektor"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    rachunekList = EntitySorter.sortLektorDesc(rachunekList);
                    sortAsc = false;
                } else {
                    rachunekList = EntitySorter.sortLektor(rachunekList);
                    sortAsc = true;
                }
                break;
        }

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((rachunekList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = rachunekList.subList(fromIndex,
                toIndex > rachunekList.size() ? rachunekList.size() : toIndex);


        /* final things */
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);
        //request.setAttribute("searchOption", searchOption);

        request.setAttribute("rachunekList", resultList);
        request.setAttribute("lektorList", lektorList);

        return request;
    }

}
