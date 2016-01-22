/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Kursant;
import finder.SchoolFinder;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.FirmaFacade;
import session.JezykFacade;
import session.JezykKursantaFacade;
import session.KursantFacade;
import sorter.EntitySorter;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@Stateless
public class ParticipantHelper {

    @EJB
    KursantFacade kursantFacade;

    @EJB
    JezykFacade jezykFacade;
    
    @EJB
    JezykKursantaFacade jezykKursantaFacade;
    
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
        List kursantList = kursantFacade.findAll();
        return prepareEntityList(request, kursantList);
    }

    public HttpServletRequest prepareEntityList(HttpServletRequest request, List kursantList) {

        List firmaList = firmaFacade.findAll();

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
            kursantList = SchoolFinder.findParticipant(kursantList, searchPhrase);
        } else {
            searchPhrase = "";
        }

        // and what option was chosen
        searchOption = request.getParameter("searchOption");
        if (searchOption != null && !searchOption.equals("")) {
            kursantList = SchoolFinder.findCustomerForParticipant(kursantList, firmaList, searchOption);
        } else {
            searchOption = "";
        }

        // now we check if we have to sort things (out)
        // (by the way - we sort using auxiliary abstract class)
        switch (sortBy) {
            case ("id"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursantList = FieldSorter.sortIdDesc(kursantList);
                    sortAsc = false;
                } else {
                    kursantList = FieldSorter.sortId(kursantList);
                    sortAsc = true;
                }
                break;
            case ("nazwa"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursantList = FieldSorter.sortNazwaDesc(kursantList);
                    sortAsc = false;
                } else {
                    kursantList = FieldSorter.sortNazwa(kursantList);
                    sortAsc = true;
                }
                break;
            case ("telefon"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursantList = FieldSorter.sortTelefonDesc(kursantList);
                    sortAsc = false;
                } else {
                    kursantList = FieldSorter.sortTelefon(kursantList);
                    sortAsc = true;
                }
                break;
            case ("email"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursantList = FieldSorter.sortEmailDesc(kursantList);
                    sortAsc = false;
                } else {
                    kursantList = FieldSorter.sortEmail(kursantList);
                    sortAsc = true;
                }
                break;
            case ("firma"):
                if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                    kursantList = EntitySorter.sortFirmaDesc(kursantList);
                    sortAsc = false;
                } else {
                    kursantList = EntitySorter.sortFirma(kursantList);
                    sortAsc = true;
                }
                break;
        }

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((kursantList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        List resultList = kursantList.subList(fromIndex,
                toIndex > kursantList.size() ? kursantList.size() : toIndex);

        // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortAsc", sortAsc);

        request.setAttribute("searchPhrase", searchPhrase);
        request.setAttribute("searchOption", searchOption);

        request.setAttribute("kursantList", resultList);
        request.setAttribute("firmaList", firmaList);

        return request;
    }

    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new mainEntity entity
     * @param request
     * @param kursantId
     * @return 
     */
    public HttpServletRequest prepareEntityView(HttpServletRequest request, String kursantId) {

        Kursant kursant = kursantFacade.find(Integer.parseInt(kursantId));

        request.setAttribute("kursant", kursant);
        request.setAttribute("jezykKursantaList", jezykKursantaFacade.findAll());
        request.setAttribute("jezykList", jezykFacade.findAll());

        return request;
    }
}
