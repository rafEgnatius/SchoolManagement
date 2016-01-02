/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.course;

import entity.Jezyk;
import entity.Kurs;
import finder.SchoolFinder;
import helper.CourseHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.FirmaFacade;
import session.JezykFacade;
import session.KursFacade;
import session.LektorFacade;
import session.StawkaFirmyFacade;
import session.StawkaLektoraFacade;
import session.persistence.PersistenceManager;
import sorter.FieldSorter;
import sorter.EntitySorter;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "CourseController",
        loadOnStartup = 1,
        urlPatterns = {"/kursy",
            "/pokazKurs",
            "/dodajKurs",
            "/dodajKursPotwierdz",
            "/dodajKursZapisz",
            "/edytujKurs"})
public class CourseController extends HttpServlet {

    @EJB
    private KursFacade kursFacade;
    private Kurs kurs;
    private List kursList = new ArrayList();

    @EJB
    private FirmaFacade firmaFacade;
    private List firmaList = new ArrayList();

    @EJB
    private JezykFacade jezykFacade;
    private Jezyk jezyk;
    private List jezykList = new ArrayList();

    @EJB
    private LektorFacade lektorFacade;
    private List lektorList = new ArrayList();

    @EJB
    private StawkaFirmyFacade stawkaFirmyFacade;

    @EJB
    private StawkaLektoraFacade stawkaLektoraFacade;

    @EJB
    private PersistenceManager persistenceManager;

//    main
    int intMainEntityId = 0;
    String mainEntityId = "";

//    other
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

        CourseHelper courseHelper = new CourseHelper();

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
// VIEW ALL
            case "/kursy":

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

                // entityList
                kursList = kursFacade.findAll();

                // additional lists
                firmaList = firmaFacade.findAll();
                jezykList = jezykFacade.findAll();
                lektorList = lektorFacade.findAll();

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
                    kursList = SchoolFinder.findCourse(kursList, searchPhrase);
                } else {
                    searchPhrase = "";
                }

                // and what option was chosen
                searchOption = request.getParameter("searchOption");
                if (searchOption != null && !searchOption.equals("")) {
                    kursList = SchoolFinder.findLanguageForCourse(kursList, searchOption);
                } else {
                    searchOption = "";
                }

                // now we check if we have to sort things (out)
                // (by the way - we sort using auxiliary class)
                switch (sortBy) {
                    case ("id"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            kursList = FieldSorter.sortIdDesc(kursList);
                            sortAsc = false;
                        } else {
                            kursList = FieldSorter.sortId(kursList);
                            sortAsc = true;
                        }
                        break;
                    case ("symbol"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            kursList = FieldSorter.sortSymbolDesc(kursList);
                            sortAsc = false;
                        } else {
                            kursList = FieldSorter.sortSymbol(kursList);
                            sortAsc = true;
                        }
                        break;
                    case ("firma"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            kursList = EntitySorter.sortFirmaDesc(kursList);
                            sortAsc = false;
                        } else {
                            kursList = EntitySorter.sortFirma(kursList);
                            sortAsc = true;
                        }
                        break;
                    case ("lektor"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            kursList = EntitySorter.sortLektorDesc(kursList);
                            sortAsc = false;
                        } else {
                            kursList = EntitySorter.sortLektor(kursList);
                            sortAsc = true;
                        }
                        break;
                    case ("jezyk"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            kursList = EntitySorter.sortJezykDesc(kursList);
                            sortAsc = false;
                        } else {
                            kursList = EntitySorter.sortJezyk(kursList);
                            sortAsc = true;
                        }
                        break;
                }

                // PAGINATE
                // and here goes pagination part
                numberOfPages = ((kursList.size()) / pageSize) + 1; // check how many pages

                // pageToDisplay is subList - we check if not get past last index
                int fromIndex = ((pageNumber - 1) * pageSize);
                int toIndex = fromIndex + pageSize;
                pageToDisplay = kursList.subList(fromIndex,
                        toIndex > kursList.size() ? kursList.size() : toIndex);

                // SEND
                // now prepare things for our JSP
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("sortAsc", sortAsc);

                request.setAttribute("searchPhrase", searchPhrase);
                request.setAttribute("searchOption", searchOption);

                // set main list
                request.setAttribute("kursList", pageToDisplay);

                // set additional lists
                request.setAttribute("firmaList", firmaList);
                request.setAttribute("jezykList", jezykList);
                request.setAttribute("lektorList", lektorList);

                userPath = "/course/course/viewAll";
                break;
// ADD             
            case "/dodajKurs":
                // just ask for a form

                // set additional list
                jezykList = jezykFacade.findAll();
                request.setAttribute("jezykList", jezykList);

                userPath = "/course/course/form";
                break;

// EDIT             
            case "/edytujKurs":
                // get id from request
                mainEntityId = request.getQueryString();

                // cast it to the integer
                try {
                    intMainEntityId = Integer.parseInt(mainEntityId);
                } catch (NumberFormatException e) {
                    intMainEntityId = 0; // (it seems that we have some kind of a problem)
                }

                if (intMainEntityId > 0) {
                    // find the lektor entity
                    kurs = kursFacade.find(intMainEntityId);
                    //request.setAttribute("kurs", kurs);

                    request.setAttribute("id", mainEntityId);
                    request.setAttribute("rok", kurs.getRok());
                    request.setAttribute("semestr", kurs.getSemestr());
                    request.setAttribute("symbol", kurs.getSymbol());
                    request.setAttribute("opis", kurs.getOpis());
                    request.setAttribute("sala", kurs.getSala());

                    request.setAttribute("jezyk", kurs.getJezyk());

                    // set additional list
                    jezykList = jezykFacade.findAll();
                    request.setAttribute("jezykList", jezykList);

                }
                // then ask for a form 
                userPath = "/course/course/form";

                break;

// SAVE
            case "/dodajKursZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String rok = request.getParameter("rok");
                String semestr = request.getParameter("semestr");
                String jezykId = request.getParameter("jezykId");
                String symbol = request.getParameter("symbol");
                String opis = request.getParameter("opis");
                String sala = request.getParameter("sala");

                int intJezykId = Integer.parseInt(jezykId);
                jezyk = jezykFacade.find(intJezykId);

                // use helper to get lektor list prepared in our request
                request = courseHelper.prepareEntityView(request, mainEntityId, kursFacade, stawkaFirmyFacade, stawkaLektoraFacade);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// VIEW ONE
            case "/pokazKurs":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: entity etc.

                mainEntityId = request.getQueryString();

                // use helper to get lektor list prepared in our request
                request = courseHelper.prepareEntityView(request, mainEntityId, kursFacade, stawkaFirmyFacade, stawkaLektoraFacade);

                // prepare redirect
                userPath = "/course/course/viewOne";
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
            case "/dodajKursPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String rok = request.getParameter("rok");
                String semestr = request.getParameter("semestr");
                String jezykId = request.getParameter("jezykId");
                String symbol = request.getParameter("symbol");
                String opis = request.getParameter("opis");
                String sala = request.getParameter("sala");

                boolean formError = false;

                if (!FormValidator.validateString(rok)) {
                    request.setAttribute("rokError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(semestr)) {
                    request.setAttribute("semestrError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(symbol)) {
                    request.setAttribute("symbolError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(opis)) {
                    request.setAttribute("opisError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(sala)) {
                    request.setAttribute("salaError", "*");
                    formError = true;
                }

                if (formError) {
                    request.setAttribute("formError", "formError");

                    request.setAttribute("id", id);
                    request.setAttribute("rok", rok);
                    request.setAttribute("semestr", semestr);
                    request.setAttribute("symbol", symbol);
                    request.setAttribute("opis", opis);
                    request.setAttribute("sala", sala);

                    jezykList = jezykFacade.findAll();
                    request.setAttribute("jezykList", jezykList);

                    jezyk = jezykFacade.find(Integer.parseInt(jezykId));
                    request.setAttribute("jezyk", jezyk);

                    userPath = "/course/course/form";
                } else {
                    request.setAttribute("id", id);
                    userPath = "/course/course/confirm";
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

        // ANYTHING THAT WE WOULD NEED HERE
        return request;
    }

}
