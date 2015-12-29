/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Lektor;
import finder.SchoolFinder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
public class LectorListHelper {

    private static final int pageSize = 10; // number of records on one page

    private Boolean sortAsc = true; // and this one to check how to sort
    private String sortBy; // so we know how to sort
    private Boolean changeSort = false; // this one to know whether to change sorting order
    private int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
    List<List> listOfPages = new ArrayList<List>(); // list of lists of single page records
    private int pageNumber; // current page number

    private String searchPhrase;
    private String searchOption;

    /**
     * Handles preparation of the lector list
     *
     * @param lektorList
     * @param jezykLektoraList
     * @param jezykList
     * @param request
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request,
            List lektorList, List jezykList, List jezykLektoraList) {

        List<Lektor> resultList;

        try {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        } catch (NumberFormatException e) {
            pageNumber = 1; // (it seems that we do not have page number yet)
        }

        // SORT & SEARCH
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
        resultList = lektorList.subList(fromIndex,
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

}
