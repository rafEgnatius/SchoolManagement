/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Kurs;
import entity.KursKursanta;
import entity.Kursant;
import entity.Lekcja;
import entity.Obecnosc;
import finder.SchoolFinder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.KursFacade;
import session.LekcjaFacade;
import session.ObecnoscFacade;
import session.persistence.PersistenceManager;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class LessonHelper {

    @EJB
    LekcjaFacade mainEntityFacade;

    @EJB
    KursFacade kursFacade;

    @EJB
    LekcjaFacade lekcjaFacade;

    @EJB
    ObecnoscFacade obecnoscFacade;

    @EJB
    PersistenceManager persistanceManager;

    @Resource(name = "pageSize")
    Integer pageSize;

    // return only these that have proper lesson
    /**
     *
     * @param obecnoscList
     * @param lekcja
     * @param kurs
     * @return
     */
    private List filterLesson(List obecnoscList, Lekcja lekcja, Kurs kurs) {
        List resultList = new ArrayList();

        Iterator it = obecnoscList.iterator();
        while (it.hasNext()) {
            Obecnosc obecnosc = (Obecnosc) it.next();
            if (obecnosc.getLekcja().equals(lekcja)) {
                resultList.add(obecnosc);
            }
        }

        // there is a problem when there is a kursant that is member of the kurs Collection
        // but not on our resultList
        // now check if anyone is missing
        // we do it by checking elements of the Collection
        it = kurs.getKursKursantaCollection().iterator();

        while (it.hasNext()) {
            KursKursanta kursKursanta = (KursKursanta) it.next();
            Kursant kursant = kursKursanta.getKursant();

            // and now if there is no kursant in our list we just have to acknowledge it
            boolean found = false; // if there are no one on the second list...
            Iterator it2 = resultList.iterator();

            // and check if on the list
            innerloop:
            while (it2.hasNext()) {
                Obecnosc obecnosc = (Obecnosc) it2.next();
                if (obecnosc.getKursant().equals(kursant)) {
                    found = true;
                    break innerloop;
                }
            }
            // and if not found we have to add him
            if (!found) {
                Obecnosc obecnosc = persistanceManager.savePresence(true, lekcja, kursant, false);
                resultList.add(obecnosc);
            }
        }

        return resultList;
    }
    
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
            Lekcja lekcja = (Lekcja) it.next();
            if (lekcja.getKurs().equals(kurs)) {
                resultList.add(lekcja);
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
        List mainEntityList = lekcjaFacade.findAll();
        List kursList = kursFacade.findAll();

        /* specific to this entity */
        // we need only those those lessons that have appropiriate course (kurs)
        // in this case we need to filter the results in case we need specific kurs
        String kursId = request.getParameter("kursId");
        if (kursId != null && !kursId.equals("")) {
            mainEntityList = filterKurs(mainEntityList, kursId);
            request.setAttribute("kursId", kursId);
        }
        
        /* technical */
        boolean sortAsc; // and this one to check how to sort
        String sortBy; // so we know how to sort
        boolean changeSort; // this one to know whether to change sorting order
        int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
        int pageNumber; // current page number
        String searchPhrase; // what we are looking for
        String searchOption;

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
            case ("data"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    mainEntityList = FieldSorter.sortDataDesc(mainEntityList);
                    sortAsc = false;
                } else {
                    mainEntityList = FieldSorter.sortData(mainEntityList);
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
        }

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((mainEntityList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = mainEntityList.subList(fromIndex,
                toIndex > mainEntityList.size() ? mainEntityList.size() : toIndex);

        // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);
        request.setAttribute("searchOption", searchOption);

        request.setAttribute("lekcjaList", resultList);
        request.setAttribute("kursList", kursList);

        return request;
    }

    public HttpServletRequest prepareEntityView(HttpServletRequest request, String lekcjaId) {

        // set attributes for main part
        int i = Integer.parseInt(lekcjaId);
        Lekcja lekcja = mainEntityFacade.find(i); // we should try/catch it later
        request.setAttribute("lekcja", lekcja);
        request.setAttribute("kurs", lekcja.getKurs());

        // and now for obecnosc list
        List obecnoscList = obecnoscFacade.findAll();
        
        // now filter only relevant ones
        obecnoscList = filterLesson(obecnoscList, lekcja, lekcja.getKurs());
        
        
        // TO DO
        
        
        request.setAttribute("obecnoscList", obecnoscList);

        return request;
    }

}
