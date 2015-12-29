/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import entity.Jezyk;
import entity.Lektor;
import entity.Podrecznik;
import finder.SchoolFinder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.JezykFacade;
import session.LektorFacade;
import session.PodrecznikFacade;
import session.WypozyczenieFacade;
import session.persistence.PersistenceManager;
import sorter.FieldSorter;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "TextbookController",
        loadOnStartup = 1,
        urlPatterns = {"/podreczniki",
            "/pokazPodrecznik",
            "/dodajPodrecznik",
            "/dodajPodrecznikPotwierdz", // POST
            "/dodajPodrecznikZapisz",
            "/edytujPodrecznik"})
public class TextbookController extends HttpServlet {

    @EJB
    private JezykFacade jezykFacade;
    private Jezyk jezyk;
    private List jezykList = new ArrayList();

    @EJB
    private LektorFacade lektorFacade;
    private Lektor lektor;
    private List lektorList = new ArrayList();

    @EJB
    private PodrecznikFacade podrecznikFacade;
    private Podrecznik podrecznik;
    private List podrecznikList = new ArrayList();

    @EJB
    private WypozyczenieFacade wypozyczenieFacade;
    private List wypozyczenieList = new ArrayList();

    @EJB
    private PersistenceManager persistenceManager;

//    jezyk
    int intJezykId = 0;
    String jezykId = "";

//    lektor
    int intLektorId = 0;
    String lektorId = "";

//    podrecznik
    int intPodrecznikId = 0;
    String podrecznikId = "";

//    GENERAL 
    private String userPath; // this one to see what to do
    private Boolean sortAsc = true; // and this one to check how to sort
    private String sortBy; // so we know how to sort
    private Boolean changeSort = false; // this one to know whether to change sorting order

//    pagination
    private static final int pageSize = 10; // number of records on one page
    private int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
    List<List> listOfPages = new ArrayList<List>(); // list of lists of single page records
    private int pageNumber; // current page number
    private List pageToDisplay; // which one should be displayed to user

//    search and filter
    private String searchPhrase; // entity search (TEXT)
    private String searchOption; // language selector (OPTION)

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/podreczniki":
                // we need here different lists - similar to lektor
                // and we need to combine them

                // sorting and pagination works this way:
                // if there is no sortBy it means we are here for the first time
                // so we check if we have pageNumber
                // if not it means that we are really for the first time here
                // let's get initial data...
                // ... like page number
                try {
                    pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                } catch (NumberFormatException e) {
                    pageNumber = 1; // (it seems that we do not have page number yet)
                }

                // prepare lists other than main
                jezykList = jezykFacade.findAll();
                wypozyczenieList = wypozyczenieFacade.findAll();

                // mainList
                podrecznikList = podrecznikFacade.findAll();

                // SORT & SEARCH
                // check whether to change sorting direction
                changeSort = Boolean.parseBoolean(request.getParameter("changeSort"));

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

                // and what language was chosen
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

                // PAGINATE                     -> pageToDisplay
                numberOfPages = ((podrecznikList.size()) / pageSize) + 1; // check how many pages

                // pageToDisplay is subList - we check if not get past last index
                int fromIndex = ((pageNumber - 1) * pageSize);
                int toIndex = fromIndex + pageSize;
                pageToDisplay = podrecznikList.subList(fromIndex,
                        toIndex > podrecznikList.size() ? podrecznikList.size() : toIndex);

                // SEND
                // now prepare things for our JSP
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("searchPhrase", searchPhrase);
                request.setAttribute("searchOption", searchOption);

                // set request atributes for necessary lists
                request.setAttribute("podrecznikList", pageToDisplay);
                request.setAttribute("jezykList", jezykList);
                request.setAttribute("wypozyczenieList", wypozyczenieList);

                userPath = "/methodology/textbook/viewAll";
                break;
// ADD             
            case "/dodajPodrecznik":
                // just ask for a form

                // we need this list for this form
                jezykList = jezykFacade.findAll();
                request.setAttribute("jezykList", jezykList);

                userPath = "/methodology/textbook/form";
                break;
// EDIT             
            case "/edytujPodrecznik":
                // get id from request
                podrecznikId = request.getQueryString();

                // cast it to the integer
                try {
                    intPodrecznikId = Integer.parseInt(podrecznikId);
                } catch (NumberFormatException e) {
                    intPodrecznikId = 0; // (it seems that we have some kind of a problem)
                }

                if (intPodrecznikId > 0) {
                    // find the lektor entity
                    podrecznik = podrecznikFacade.find(intPodrecznikId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("podrecznik", podrecznik);
                    request.setAttribute("id", podrecznik.getId());
                    request.setAttribute("nazwa", podrecznik.getNazwa());
                    request.setAttribute("poziom", podrecznik.getPoziom());
                    request.setAttribute("jezyk", podrecznik.getJezyk());

                    jezykList = jezykFacade.findAll();
                    request.setAttribute("jezykList", jezykList);
                }
                // then ask for a form 
                userPath = "/methodology/textbook/form";

                break;
// SAVE
            case "/dodajPodrecznikZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String nazwa = request.getParameter("nazwa");
                String poziom = request.getParameter("poziom");
                jezykId = request.getParameter("jezykId");

                // we need language, not its id, or a string representing it
                intJezykId = Integer.parseInt(jezykId); // I better be sure about that one not to try it
                jezyk = jezykFacade.find(intJezykId);
                                
                intPodrecznikId = persistenceManager.saveTextbookToDatabase(id, nazwa, poziom, jezyk);

                podrecznikId = intPodrecznikId + "";
                request = prepareRequest(request, podrecznikId); // set all the attributes that request needs

                // finally show the newly created lector (so it can be further processed)
                userPath = "/methodology/textbook/viewOne";
                break;
// VIEW ONE
            case "/pokazPodrecznik":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                request = prepareRequest(request, request.getQueryString()); // set all the attributes that request needs

                userPath = "/methodology/textbook/viewOne";
                break;

// FORWARD
        } // close main swith
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
// CONFIRM
            case "/dodajPodrecznikPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String nazwa = request.getParameter("nazwa");
                String poziom = request.getParameter("poziom");
                jezykId = request.getParameter("jezykId");
                
                intJezykId = Integer.parseInt(jezykId);
                jezyk = jezykFacade.find(intJezykId);

                boolean formError = false;

                if (!FormValidator.validateString(nazwa)) {
                    request.setAttribute("nazwaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(poziom)) {
                    request.setAttribute("poziomError", "*");
                    formError = true;
                }

                if (formError) {
                    request.setAttribute("formError", "formError");

                    request.setAttribute("id", id);
                    request.setAttribute("nazwa", nazwa);
                    request.setAttribute("poziom", poziom);
                    request.setAttribute("jezyk", jezyk);

                    userPath = "/methodology/textbook/form";
                } else {
                    request.setAttribute("id", id);
                    request.setAttribute("jezyk", jezyk); // the rest is in params
                    userPath = "/methodology/textbook/confirm";
                }

                // forward it to confirmation
                break;

// FORWARD
        } // close main swith
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new Entity entity
     */
    private HttpServletRequest prepareRequest(HttpServletRequest request, String podrecznikId) {

        podrecznik = podrecznikFacade.find(Integer.parseInt(podrecznikId));
        jezykList = jezykFacade.findAll();
        wypozyczenieList = wypozyczenieFacade.findAll();
        podrecznikList = podrecznikFacade.findAll();

        request.setAttribute("podrecznik", podrecznik);
        request.setAttribute("jezykList", jezykList);
        request.setAttribute("wypozyczenieList", wypozyczenieList);
        request.setAttribute("podrecznikList", podrecznikList);

        return request;
    }

}
