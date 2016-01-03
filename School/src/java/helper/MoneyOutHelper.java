/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Wyplata;
import finder.SchoolFinder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
public class MoneyOutHelper {

    private static final int pageSize = 10; // number of records on one page

    private Boolean sortAsc = true; // and this one to check how to sort
    private String sortBy; // so we know how to sort
    private Boolean changeSort = false; // this one to know whether to change sorting order
    private int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
    List<List> listOfPages = new ArrayList<>(); // list of lists of single page records
    private int pageNumber; // current page number

    private String searchPhrase;
    // private String searchOption;

    /**
     * Handles preparation of the rachunek list
     *
     * 
     * @param request
     * @param mainEntityList
     * @param lektorList
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request,
            List mainEntityList, List lektorList) {

        List<Wyplata> resultList;

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
            mainEntityList = SchoolFinder.findRachunek(mainEntityList, lektorList, searchPhrase);
        } else {
            searchPhrase = "";
        }


        // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary abstract class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = FieldSorter.sortIdDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = FieldSorter.sortId(mainEntityList);
                    sortAsc = true;
                }
                break;
            case ("data"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = FieldSorter.sortDataDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = FieldSorter.sortData(mainEntityList);
                    sortAsc = true;
                }
                break;
            case ("kwota"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = FieldSorter.sortKwotaDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = FieldSorter.sortKwota(mainEntityList);
                    sortAsc = true;
                }
                break;
            case ("lektor"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = EntitySorter.sortLektorDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = EntitySorter.sortLektor(mainEntityList);
                    sortAsc = true;
                }
                break;
        }

                // PAGINATE
        // and here goes pagination part
        numberOfPages = ((mainEntityList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        resultList = mainEntityList.subList(fromIndex,
                toIndex > mainEntityList.size() ? mainEntityList.size() : toIndex);

                // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);
        
        request.setAttribute("searchPhrase", searchPhrase);
        //request.setAttribute("searchOption", searchOption);

        request.setAttribute("mainEntityList", resultList);
        request.setAttribute("lektorList", lektorList);

        return request;
    }

}
