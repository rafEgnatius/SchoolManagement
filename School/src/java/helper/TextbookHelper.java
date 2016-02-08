/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Podrecznik;
import finder.SchoolFinder;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.JezykFacade;
import session.PodrecznikFacade;
import session.WypozyczenieFacade;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class TextbookHelper {

    @EJB
    PodrecznikFacade podrecznikFacade;

    @EJB
    JezykFacade jezykFacade;

    @EJB
    WypozyczenieFacade wypozyczenieFacade;

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
        List podrecznikList = podrecznikFacade.findAll();
        List jezykList = jezykFacade.findAll();
        List wypozyczenieList = wypozyczenieFacade.findAll();

        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number
        String searchPhrase; // what we are looking for
        String searchOption; // selector (OPTION)

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
            podrecznikList = SchoolFinder.findTextbook(podrecznikList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // and what option was chosen
        searchOption = request.getParameter("searchOption");
        if (searchOption != null && !searchOption.equals("")) {
            podrecznikList = SchoolFinder.findLanguageForTextbook(podrecznikList, searchOption);
        } else {
            searchOption = "";
        }

        // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary abstract class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    podrecznikList = FieldSorter.sortIdDesc(podrecznikList);
                    sortAsc = false;
                } else {
                    podrecznikList = FieldSorter.sortId(podrecznikList);
                    sortAsc = true;
                }
                break;
            case ("nazwa"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    podrecznikList = FieldSorter.sortNazwaDesc(podrecznikList);
                    sortAsc = false;
                } else {
                    podrecznikList = FieldSorter.sortNazwa(podrecznikList);
                    sortAsc = true;
                }
                break;
            case ("poziom"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    podrecznikList = FieldSorter.sortPoziomDesc(podrecznikList);
                    sortAsc = false;
                } else {
                    podrecznikList = FieldSorter.sortPoziom(podrecznikList);
                    sortAsc = true;
                }
                break;
        }

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((podrecznikList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = podrecznikList.subList(fromIndex,
                toIndex > podrecznikList.size() ? podrecznikList.size() : toIndex);


        /* final things */
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);
        request.setAttribute("searchOption", searchOption);

        // set request atributes for necessary lists
        request.setAttribute("podrecznikList", resultList);
        request.setAttribute("jezykList", jezykList);
        request.setAttribute("wypozyczenieList", wypozyczenieList);

        return request;
    }

    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new Entity entity
     * @param request
     * @param podrecznikId
     * @return 
     */
    public HttpServletRequest prepareEntityView(HttpServletRequest request, String podrecznikId) {

        Podrecznik podrecznik = podrecznikFacade.find(Integer.parseInt(podrecznikId));
        List jezykList = jezykFacade.findAll();
        List wypozyczenieList = wypozyczenieFacade.findAll();
        List podrecznikList = podrecznikFacade.findAll();

        request.setAttribute("podrecznik", podrecznik);
        request.setAttribute("jezykList", jezykList);
        request.setAttribute("wypozyczenieList", wypozyczenieList);
        request.setAttribute("podrecznikList", podrecznikList);

        return request;
    }

}
