/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Wplata;
import finder.SchoolFinder;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.FirmaFacade;
import session.WplataFacade;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class MoneyInHelper {

    @EJB
    WplataFacade wplataFacade;

    @EJB
    FirmaFacade firmaFacade;

    @Resource(name = "pageSize")
    Integer pageSize;

    /**
     * Handles preparation of the list
     *
     *
     * @param request
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request) {

        /* main lists that we will use */
        List wplataList = wplataFacade.findAll();
        List firmaList = firmaFacade.findAll();

        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number
        String searchPhrase; // what we are looking for

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
            wplataList = SchoolFinder.findWplata(wplataList, firmaList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary abstract class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    wplataList = FieldSorter.sortIdDesc(wplataList);
                    sortAsc = false;
                } else {
                    wplataList = FieldSorter.sortId(wplataList);
                    sortAsc = true;
                }
                break;
            case ("data"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    wplataList = FieldSorter.sortDataDesc(wplataList);
                    sortAsc = false;
                } else {
                    wplataList = FieldSorter.sortData(wplataList);
                    sortAsc = true;
                }
                break;
            case ("kwota"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    wplataList = FieldSorter.sortKwotaDesc(wplataList);
                    sortAsc = false;
                } else {
                    wplataList = FieldSorter.sortKwota(wplataList);
                    sortAsc = true;
                }
                break;
            case ("firma"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    wplataList = EntitySorter.sortFirmaDesc(wplataList);
                    sortAsc = false;
                } else {
                    wplataList = EntitySorter.sortFirma(wplataList);
                    sortAsc = true;
                }
                break;
        }

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((wplataList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = wplataList.subList(fromIndex,
                toIndex > wplataList.size() ? wplataList.size() : toIndex);

        // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);
        //request.setAttribute("searchOption", searchOption);

        request.setAttribute("mainEntityList", resultList);
        request.setAttribute("firmaList", firmaList);

        return request;
    }

    /**
     * This one prepares request to show one lector it is not to multiply code
     * when adding and showing new mainEntity entity
     *
     * @param request
     * @param wplataId
     * @return
     */
    public HttpServletRequest prepareEntityView(HttpServletRequest request, String wplataId) {

        Wplata wplata = wplataFacade.find(Integer.parseInt(wplataId));

        request.setAttribute("mainEntity", wplata);
        request.setAttribute("firma", wplata.getFirma());

        return request;
    }
}
