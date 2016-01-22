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
import session.JezykFacade;
import session.LektorFacade;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class LanguageHelper {

    @EJB
    JezykFacade jezykFacade;

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
        List jezykList = jezykFacade.findAll();

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
                    jezykList = FieldSorter.sortIdDesc(jezykList);
                    sortAsc = false;
                } else {
                    jezykList = FieldSorter.sortId(jezykList);
                    sortAsc = true;
                }
                break;
            case ("nazwa"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    jezykList = FieldSorter.sortNazwaDesc(jezykList);
                    sortAsc = false;
                } else {
                    jezykList = FieldSorter.sortNazwa(jezykList);
                    sortAsc = true;
                }
                break;
            case ("symbol"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    jezykList = FieldSorter.sortSymbolDesc(jezykList);
                    sortAsc = false;
                } else {
                    jezykList = FieldSorter.sortSymbol(jezykList);
                    sortAsc = true;
                }
                break;
        }

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((jezykList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = jezykList.subList(fromIndex,
                toIndex > jezykList.size() ? jezykList.size() : toIndex);

        /* final things */
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("jezykList", resultList);

        return request;
    }

}
