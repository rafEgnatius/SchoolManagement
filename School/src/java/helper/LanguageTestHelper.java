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
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.FirmaFacade;
import session.KursFacade;
import session.KursantFacade;
import session.TestFacade;
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

    @EJB
    TestFacade testFacade;

    @EJB
    FirmaFacade firmaFacade;

    @Resource(name = "pageSize")
    int pageSize; // number of records on one page

    /**
     *
     * @param mainEntityList
     * @param kursId
     * @return
     */
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

    /**
     *
     * @param mainEntityList
     * @param kursantId
     * @return
     */
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
     * @return HttpServletRequest
     */
    public HttpServletRequest prepareEntityList(HttpServletRequest request) {

        /* main lists that we will use */
        List testList = testFacade.findAll();
        List kursList = kursFacade.findAll();

        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number
        String searchPhrase; // what we are looking for
        String searchOption; // option chosen by user

        /* and now process */
        // in this case we need to filter the results in case we need specific kurs and kursant (participant)
        String kursId = request.getParameter("kursId");
        if (kursId != null && !kursId.equals("")) {
            testList = filterKurs(testList, kursId);
            request.setAttribute("kursId", kursId);
        }

        // and kursant
        String kursantId = request.getParameter("kursantId");
        if (kursantId != null && !kursantId.equals("")) {
            testList = filterKursant(testList, kursantId);
            request.setAttribute("kursantId", kursantId);
        }

        List resultList;

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
            testList = SchoolFinder.findLanguageTest(testList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // and what option was chosen
        searchOption = request.getParameter("searchOption");
        if (searchOption != null && !searchOption.equals("")) {
            testList = SchoolFinder.findCustomerForLanguageTest(testList, kursList, searchOption);
        } else {
            searchOption = "";
        }

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
            case ("rodzaj"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    testList = FieldSorter.sortRodzajDesc(testList);
                    sortAsc = false;
                } else {
                    testList = FieldSorter.sortRodzaj(testList);
                    sortAsc = true;
                }
                break;
            case ("ocena"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    testList = FieldSorter.sortOcenaDesc(testList);
                    sortAsc = false;
                } else {
                    testList = FieldSorter.sortOcena(testList);
                    sortAsc = true;
                }
                break;
            case ("kurs"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    testList = EntitySorter.sortKursDesc(testList);
                    sortAsc = false;
                } else {
                    testList = EntitySorter.sortKurs(testList);
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
        resultList = testList.subList(fromIndex,
                toIndex > testList.size() ? testList.size() : toIndex);

        // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);
        request.setAttribute("searchOption", searchOption);

        request.setAttribute("testList", resultList);
        request.setAttribute("kursantList", kursantFacade.findAll());
        request.setAttribute("kursList", kursList);
        request.setAttribute("firmaList", firmaFacade.findAll());

        return request;
    }

    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new mainEntity entity
     * @param request
     * @param testId
     * @return 
     */
    public HttpServletRequest prepareEntityView(HttpServletRequest request, int testId) {
        request.setAttribute("test", testFacade.find(testId));
        return request;
    }
}
