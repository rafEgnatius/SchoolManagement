/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Kurs;
import entity.Kursant;
import entity.Test;
import finder.SchoolFinder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.KursFacade;
import session.KursantFacade;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class LanguageTestHelper {

    @EJB
    KursFacade kursFacade;
    
    @EJB
    KursantFacade kursantFacade;

    private static final int pageSize = 10; // number of records on one page

    private Boolean sortAsc = true; // and this one to check how to sort
    private String sortBy; // so we know how to sort
    private Boolean changeSort = false; // this one to know whether to change sorting order
    private int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
    List<List> listOfPages = new ArrayList<List>(); // list of lists of single page records
    private int pageNumber; // current page number

    private String searchPhrase;
    private String searchOption;
    
    // METHODS, MAN

    private List filterKurs(List mainEntityList, String kursId) {
        List resultList = new ArrayList();
        Kurs kurs = kursFacade.find(Integer.parseInt(kursId));

        Iterator it = mainEntityList.iterator();
        while (it.hasNext()) {
            Test test = (Test) it.next();
            if (test.getKurs().equals(kurs)) {
                resultList.add(test);
            }
        }
        return resultList;
    }
    
    private List filterKursant(List mainEntityList, String kursantId) {
        List resultList = new ArrayList();
        Kursant kursant = kursantFacade.find(Integer.parseInt(kursantId));

        Iterator it = mainEntityList.iterator();
        while (it.hasNext()) {
            Test test = (Test) it.next();
            if (test.getKursant().equals(kursant)) {
                resultList.add(test);
            }
        }
        return resultList;
    }
    
    /**
     * Handles preparation of the list
     *
     *
     * @param request
     * @param mainEntityList
     * @param firmaList
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request,
            List mainEntityList, List kursList, List kursantList, List firmaList) {

        // in this case we need to filter the results in case we need specific kurs and kursant (participant)
        String kursId = request.getParameter("kursId");
        if (kursId != null && !kursId.equals("")) {
            mainEntityList = filterKurs(mainEntityList, kursId);
        }

        // and kursant
        String kursantId = request.getParameter("kursantId");
        if (kursantId != null && !kursantId.equals("")) {
            mainEntityList = filterKursant(mainEntityList, kursantId);
        }

        List<Kursant> resultList;

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
            mainEntityList = SchoolFinder.findLanguageTest(mainEntityList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // and what option was chosen
        searchOption = request.getParameter("searchOption");
        if (searchOption != null && !searchOption.equals("")) {
            mainEntityList = SchoolFinder.findCustomerForLanguageTest(mainEntityList, kursList, searchOption);
        } else {
            searchOption = "";
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
            case ("rodzaj"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = FieldSorter.sortRodzajDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = FieldSorter.sortRodzaj(mainEntityList);
                    sortAsc = true;
                }
                break;
            case ("ocena"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = FieldSorter.sortOcenaDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = FieldSorter.sortOcena(mainEntityList);
                    sortAsc = true;
                }
                break;
            case ("kurs"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = EntitySorter.sortKursDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = EntitySorter.sortKurs(mainEntityList);
                    sortAsc = true;
                }
                break;
            case ("kursant"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = EntitySorter.sortKursantDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = EntitySorter.sortKursant(mainEntityList);
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
        request.setAttribute("searchOption", searchOption);

        request.setAttribute("testList", resultList);
        request.setAttribute("kursantList", kursantList);
        request.setAttribute("kursList", kursList);
        request.setAttribute("firmaList", firmaList);

        return request;
    }

    

}
