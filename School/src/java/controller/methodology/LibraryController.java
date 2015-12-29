/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import entity.Lektor;
import entity.Podrecznik;
import entity.Wypozyczenie;
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
import session.LektorFacade;
import session.PodrecznikFacade;
import session.WypozyczenieFacade;
import session.persistence.PersistenceManager;
import sorter.FieldSorter;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "TextbookLibraryController",
        loadOnStartup = 1,
        urlPatterns = {"/dodajLektoraDoWypozyczenia",
            "/wyszukajLektoraDoWypozyczenia", // POST
            "/dodajPodrecznikDoWypozyczenia",
            "/wyszukajPodrecznikDoWypozyczenia", // POST
            "/potwierdzWypozyczeniePodrecznika",
            "/wypozyczPodrecznikZBiblioteki",
            "/oddajPodrecznikDoBiblioteki"})
public class LibraryController extends HttpServlet {

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
    private Wypozyczenie wypozyczenie;
    private List wypozyczenieList = new ArrayList();

    @EJB
    private PersistenceManager persistenceManager;

//    lektor
    int intLektorId = 0;
    String lektorId = "";

//    podrecznik
    int intPodrecznikId = 0;
    String podrecznikId = "";

//    general 
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
    //private String searchOption; // entity selector (OPTION) -> we don't need this one in this controller

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
//  ADD LECTOR
            case "/dodajLektoraDoWypozyczenia":

                // it is like in entities list - it's just simpler
                podrecznikId = request.getParameter("podrecznikId"); // it should be set if we are here

                try {
                    pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                } catch (NumberFormatException e) {
                    pageNumber = 1; // (it seems that we do not have page number yet)
                }

                // prepare list
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

                // now we check if we have to sort things (out)
                // (by the way - we sort using auxiliary abstract class)
                switch (sortBy) {
                    case ("id"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            lektorList = FieldSorter.sortIdDesc(lektorList);
                            sortAsc = false;
                        } else {
                            lektorList = FieldSorter.sortId(lektorList);
                            sortAsc = true;
                        }
                        break;
                    case ("nazwa"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            lektorList = FieldSorter.sortNazwaDesc(lektorList);
                            sortAsc = false;
                        } else {
                            lektorList = FieldSorter.sortNazwa(lektorList);
                            sortAsc = true;
                        }
                        break;
                    case ("miasto"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            lektorList = FieldSorter.sortMiastoDesc(lektorList);
                            sortAsc = false;
                        } else {
                            lektorList = FieldSorter.sortMiasto(lektorList);
                            sortAsc = true;
                        }
                        break;
                    case ("telefon"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            lektorList = FieldSorter.sortTelefonDesc(lektorList);
                            sortAsc = false;
                        } else {
                            lektorList = FieldSorter.sortTelefon(lektorList);
                            sortAsc = true;
                        }
                        break;
                    case ("email"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            lektorList = FieldSorter.sortEmailDesc(lektorList);
                            sortAsc = false;
                        } else {
                            lektorList = FieldSorter.sortEmail(lektorList);
                            sortAsc = true;
                        }
                        break;
                }

                // the rest in the following method
                // and this is because it is hard to get lektorId
                // by GET method                
                request = prepareLectorList(request);

                userPath = "/methodology/library/borrow";
                break;

//  ADD TEXTBOOK
            case "/dodajPodrecznikDoWypozyczenia":

                // it is like in entities list - it's just simpler
                lektorId = request.getParameter("lektorId"); // it should be set if we are here

                try {
                    pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                } catch (NumberFormatException e) {
                    pageNumber = 1; // (it seems that we do not have page number yet)
                }

                // prepare list
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

                // now we check if we have to sort things (out)
                // (by the way - we sort using auxiliary abstract class)
                switch (sortBy) {
                    case ("id"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            podrecznikList = FieldSorter.sortIdDesc(podrecznikList);
                            sortAsc = false;
                        } else {
                            lektorList = FieldSorter.sortId(podrecznikList);
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

                // the rest in the following method
                // and this is because it is hard to get lektorId
                // by GET method                
                request = preparePodrecznikList(request);

                userPath = "/methodology/library/borrow";
                break;
                
// CONFIRM
            case "/potwierdzWypozyczeniePodrecznika":

                // check parameters
                lektorId = request.getParameter("lektorId");
                podrecznikId = request.getParameter("podrecznikId");

                // find entities
                try {
                    intLektorId = Integer.parseInt(lektorId);
                    intPodrecznikId = Integer.parseInt(podrecznikId);
                } catch (NumberFormatException e) {
                    userPath = "/methodology/textbook/viewAll";
                    break;
                }
                lektor = lektorFacade.find(intLektorId);
                podrecznik = podrecznikFacade.find(intPodrecznikId);

                // prepare request
                request.setAttribute("lektor", lektor);
                request.setAttribute("podrecznik", podrecznik);
                userPath = "/methodology/library/confirm";

                // forward it to confirmation
                break;

// SAVE
            case "/wypozyczPodrecznikZBiblioteki":
                // it is confirmed so add to database

                // check parameters
                lektorId = request.getParameter("lektorId");
                podrecznikId = request.getParameter("podrecznikId");

                lektor = lektorFacade.find(Integer.parseInt(lektorId)); // we should try/catch it later
                podrecznik = podrecznikFacade.find(Integer.parseInt(podrecznikId)); // we should try/catch it later

                // now persist:
                persistenceManager.saveBorrowingToDatabase(lektor, podrecznik);

                userPath = "/methodology/confirmation"; // and show once more - now with another language
                break;

// REMOVE
            case "/oddajPodrecznikDoBiblioteki":

                // check parameters
                lektorId = request.getParameter("lektorId");
                podrecznikId = request.getParameter("podrecznikId");

                lektor = lektorFacade.find(Integer.parseInt(lektorId)); // we should try/catch it later
                podrecznik = podrecznikFacade.find(Integer.parseInt(podrecznikId)); // we should try/catch it later

                // now persist:
                persistenceManager.deleteBorrowingFromDatabase(lektor, podrecznik);

                userPath = "/methodology/confirmation"; // and show once more - now with another language
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
//  SEARCH FOR LECTOR
            case "/wyszukajLektoraDoWypozyczenia":

                // as this is only for the case when the usser is searching
                // meaning the list will be new
                // we only need search phrase (here) and pagination part (in separate method)
                // it is like in entities list - it's just simpler
                podrecznikId = request.getParameter("podrecznikId"); // it should be set if we are here

                // prepare list
                lektorList = lektorFacade.findAll();
                
                // set page number
                pageNumber = 1; // it is always 1 here, because it is always a new search if we are here
                
                // and check if searching
                searchPhrase = request.getParameter("searchPhrase");
                if (searchPhrase != null && !searchPhrase.equals("")) {
                    lektorList = SchoolFinder.findLector(lektorList, searchPhrase);
                } else {
                    searchPhrase = "";
                }

                // the rest in the following method
                // and this is because it is hard to get lektorId
                // by GET method                
                request = prepareLectorList(request);

                userPath = "/methodology/library/borrow";
                break;

//  SEARCH FOR LECTOR
            case "/wyszukajPodrecznikDoWypozyczenia":

                // as this is only for the case when the usser is searching
                // meaning the list will be new
                // we only need search phrase (here) and pagination part (in separate method)
                // it is like in entities list - it's just simpler
                lektorId = request.getParameter("lektorId"); // it should be set if we are here

                // prepare list
                podrecznikList = podrecznikFacade.findAll();
                
                // set page number
                pageNumber = 1; // it is always 1 here, because it is always a new search if we are here
                
                // and check if searching
                searchPhrase = request.getParameter("searchPhrase");
                if (searchPhrase != null && !searchPhrase.equals("")) {
                    podrecznikList = SchoolFinder.findLector(lektorList, searchPhrase);
                } else {
                    searchPhrase = "";
                }

                // the rest in the following method
                // and this is because it is hard to get lektorId
                // by GET method                
                request = preparePodrecznikList(request);

                userPath = "/methodology/library/borrow";
                break;
                
// FORWARD                
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
    private HttpServletRequest prepareLectorList(HttpServletRequest request) {

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((lektorList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        pageToDisplay = lektorList.subList(fromIndex,
                toIndex > lektorList.size() ? lektorList.size() : toIndex);

        // SEND
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("searchPhrase", searchPhrase);

        request.setAttribute("podrecznikId", podrecznikId); // to display

        request.setAttribute("lektorList", pageToDisplay);

        return request;
    }
    
    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new Entity entity
     */
    private HttpServletRequest preparePodrecznikList(HttpServletRequest request) {

        // PAGINATE
        // and here goes pagination part
        numberOfPages = ((podrecznikList.size()) / pageSize) + 1; // check how many pages

        // pageToDisplay is subList - we check if not get past last index
        int fromIndex = ((pageNumber - 1) * pageSize);
        int toIndex = fromIndex + pageSize;
        pageToDisplay = podrecznikList.subList(fromIndex,
                toIndex > podrecznikList.size() ? podrecznikList.size() : toIndex);

        // SEND
        
        // one more list to prepare
        wypozyczenieList = wypozyczenieFacade.findAll();
        
        // now prepare things for our JSP
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("searchPhrase", searchPhrase);

        request.setAttribute("lektorId", lektorId); // to display

        request.setAttribute("podrecznikList", pageToDisplay);
        request.setAttribute("wypozyczenieList", wypozyczenieList);

        return request;
    }

}
