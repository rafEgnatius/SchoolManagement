/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import entity.Jezyk;
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
import session.persistence.PersistenceManager;
import sorter.FieldSorter;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "LanguageController",
        loadOnStartup = 1,
        urlPatterns = {"/jezyki",
            "/pokazJezyk",
            "/dodajJezyk",
            "/dodajJezykPotwierdz", // POST
            "/dodajJezykZapisz",
            "/edytujJezyk"})
public class LanguageController extends HttpServlet {

    @EJB
    private JezykFacade jezykFacade;
    private Jezyk jezyk;
    private List jezykList = new ArrayList();

    @EJB
    private PersistenceManager persistenceManager;

//    Jezyk
    int intJezykId = 0;
    String jezykId = "";

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

        request.setCharacterEncoding("UTF-8"); // for Polish characters

        userPath = request.getServletPath();

        switch (userPath) {
// VIEW ALL
            case "/jezyki":
                // we need here two lists - a <lektor> list and <jezykLektora> list
                // and we need to combine them somehow
                // and additionally <jezyk> list

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

                // jezykList
                jezykList = jezykFacade.findAll();

                
                // SORT only (no search)
                
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
                // (by the way - we sort using auxiliary class)
                switch (sortBy) {
                    case ("id"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            jezykList = FieldSorter.sortIdDesc(jezykList);
                            sortAsc = false;
                        } else {
                            jezykList = FieldSorter.sortId(jezykList);
                            sortAsc = true;
                        }
                        break;
                    case ("nazwa"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            jezykList = FieldSorter.sortNazwaDesc(jezykList);
                            sortAsc = false;
                        } else {
                            jezykList = FieldSorter.sortNazwa(jezykList);
                            sortAsc = true;
                        }
                        break;
                    case ("symbol"):
                        if ((sortAsc && changeSort) || (!sortAsc && !changeSort)) {
                            jezykList = FieldSorter.sortSymbolDesc(jezykList);
                            sortAsc = false;
                        } else {
                            jezykList = FieldSorter.sortSymbol(jezykList);
                            sortAsc = true;
                        }
                        break;
                }

                
                // PAGINATE
                
                // and here goes pagination part
                numberOfPages = ((jezykList.size()) / pageSize) + 1; // check how many pages

                // pageToDisplay is subList - we check if not get past last index
                int fromIndex = ((pageNumber - 1) * pageSize);
                int toIndex = fromIndex + pageSize;
                pageToDisplay = jezykList.subList(fromIndex,
                        toIndex > jezykList.size() ? jezykList.size() : toIndex);

                
                // SEND
                
                // now prepare things for our JSP
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("pageNumber", pageNumber);
                request.setAttribute("sortBy", sortBy);

                request.setAttribute("jezykList", pageToDisplay);
                                
                userPath = "/methodology/language/viewAll";
                break;
// ADD             
            case "/dodajJezyk":
                // just ask for a form

                userPath = "/methodology/language/form";
                break;

// EDIT             
            case "/edytujJezyk":
                // get lektorId from request
                jezykId = request.getQueryString();

                // cast it to the integer
                try {
                    intJezykId = Integer.parseInt(jezykId);
                } catch (NumberFormatException e) {
                    intJezykId = 0; // (it seems that we have some kind of a problem)
                }

                if (intJezykId > 0) {
                    // find the lektor entity
                    jezyk = jezykFacade.find(intJezykId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("id", jezyk.getId());
                    request.setAttribute("nazwa", jezyk.getNazwa());
                    request.setAttribute("symbol", jezyk.getSymbol());
                }
                // then ask for a form 
                userPath = "/methodology/language/form";

                break;

// SAVE
            case "/dodajJezykZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String nazwa = request.getParameter("nazwa");
                String symbol = request.getParameter("symbol");

                intJezykId = persistenceManager.saveLanguageToDatabase(id, nazwa, symbol);

                request.setAttribute("jezyk", jezykFacade.find(intJezykId));
                userPath = "/methodology/language/viewOne";
                break;
                
// VIEW ONE
            case "/pokazJezyk":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                String jezykId = request.getQueryString();
                jezyk = jezykFacade.find(Integer.parseInt(jezykId));
                request.setAttribute("jezyk", jezyk);
                userPath = "/methodology/language/viewOne";
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

        request.setCharacterEncoding("UTF-8"); // for Polish characters

        userPath = request.getServletPath();

        switch (userPath) {
// CONFIRM
            case "/dodajJezykPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String nazwa = request.getParameter("nazwa");
                String symbol = request.getParameter("symbol");

                boolean formError = false;

                if (!FormValidator.validateString(nazwa)) {
                    request.setAttribute("nazwaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(symbol)) {
                    request.setAttribute("symbolError", "*");
                    formError = true;
                }

                if (formError) {
                    request.setAttribute("formError", "formError");

                    request.setAttribute("id", id);
                    request.setAttribute("nazwa", nazwa);
                    request.setAttribute("symbol", symbol);

                    userPath = "/methodology/language/form";
                } else {
                    request.setAttribute("id", id);
                    userPath = "/methodology/language/confirm";
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
}
