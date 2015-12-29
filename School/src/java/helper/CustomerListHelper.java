/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Lektor;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
public class CustomerListHelper {

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
     * @param request
     * @param firmaList
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request, List firmaList) {

        List<Lektor> resultList;

        try {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        } catch (NumberFormatException e) {
            pageNumber = 1; // (it seems that we do not have page number yet)
        }

        // SORT only (no search)
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

                // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    firmaList = FieldSorter.sortIdDesc(firmaList);
                    sortAsc = false;
                } else {
                    firmaList = FieldSorter.sortId(firmaList);
                    sortAsc = true;
                }
                break;
            case ("nazwa"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    firmaList = FieldSorter.sortNazwaDesc(firmaList);
                    sortAsc = false;
                } else {
                    firmaList = FieldSorter.sortNazwa(firmaList);
                    sortAsc = true;
                }
                break;
            case ("symbol"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    firmaList = FieldSorter.sortSymbolDesc(firmaList);
                    sortAsc = false;
                } else {
                    firmaList = FieldSorter.sortSymbol(firmaList);
                    sortAsc = true;
                }
                break;
            case ("miasto"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    firmaList = FieldSorter.sortMiastoDesc(firmaList);
                    sortAsc = false;
                } else {
                    firmaList = FieldSorter.sortMiasto(firmaList);
                    sortAsc = true;
                }
                break;
        }

                // PAGINATE
        // and here goes pagination part
        numberOfPages = ((firmaList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        resultList = firmaList.subList(fromIndex,
                toIndex > firmaList.size() ? firmaList.size() : toIndex);

                // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("firmaList", resultList);

        return request;
    }

}
