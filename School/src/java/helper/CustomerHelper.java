/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.FirmaFacade;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class CustomerHelper {

    @EJB
    FirmaFacade firmaFacade;

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
        List firmaList = firmaFacade.findAll();

        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number

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
        List resultList = firmaList.subList(fromIndex,
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
