/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Ankieta;
import finder.SchoolFinder;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.AnkietaFacade;
import session.KursantFacade;
import session.LektorFacade;
import session.persistence.PersistenceManager;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class QuestionnaireHelper {

    @EJB
    AnkietaFacade mainEntityFacade;

    @EJB
    KursantFacade kursantFacade;

    @EJB
    LektorFacade lektorFacade;

    @EJB
    PersistenceManager persistanceManager;
    
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
        List testList = mainEntityFacade.findAll();
        List kursantList = kursantFacade.findAll();
        List lektorList = lektorFacade.findAll();

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
            testList = SchoolFinder.findQuestionnaire(testList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        /* no option part here */
        // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary abstract class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    testList = FieldSorter.sortIdDesc(testList);
                    sortAsc = false;
                } else {
                    testList = FieldSorter.sortId(testList);
                    sortAsc = true;
                }
                break;
            case ("data"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    testList = FieldSorter.sortDataDesc(testList);
                    sortAsc = false;
                } else {
                    testList = FieldSorter.sortData(testList);
                    sortAsc = true;
                }
                break;
            case ("lektor"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    testList = EntitySorter.sortLektorDesc(testList);
                    sortAsc = false;
                } else {
                    testList = EntitySorter.sortLektor(testList);
                    sortAsc = true;
                }
                break;
            case ("kursant"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    testList = EntitySorter.sortKursantDesc(testList);
                    sortAsc = false;
                } else {
                    testList = EntitySorter.sortKursant(testList);
                    sortAsc = true;
                }
                break;
        }

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((testList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = testList.subList(fromIndex,
                toIndex > testList.size() ? testList.size() : toIndex);

        // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);

        request.setAttribute("ankietaList", resultList);
        request.setAttribute("kursantList", kursantList);
        request.setAttribute("lektorList", lektorList);

        return request;
    }

    public HttpServletRequest prepareEntityView(HttpServletRequest request, String mainEntityId) {
        // set attributes for main part
        int i = Integer.parseInt(mainEntityId);
        Ankieta ankieta = mainEntityFacade.find(i);
        
        request.setAttribute("ankieta", ankieta);
        request.setAttribute("kursant", ankieta.getKursant());
        request.setAttribute("lektora", ankieta.getLektor());

        return request;
    }

}
