/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.organisation;

import entity.Rachunek;
import helper.BillHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.LektorFacade;
import session.RachunekFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "BillController",
        loadOnStartup = 1,
        urlPatterns = {"/rachunki",
            "/pokazRachunek",
            "/dodajRachunek",
            "/dodajRachunekPotwierdz",
            "/dodajRachunekZapisz",
            "/edytujRachunek"})
public class BillController extends HttpServlet {

    // mainEntity meaning entity of this controller
    @EJB
    private RachunekFacade mainEntityFacade;
    private Rachunek mainEntity;
    private List mainEntityList = new ArrayList();

    @EJB
    LektorFacade lektorFacade;
    private List lektorList = new ArrayList();

    @EJB
    private PersistenceManager persistenceManager;

//    main
//    GENERAL 
    private String userPath; // this one to see what to do

//    pagination
    List<List> listOfPages = new ArrayList<List>(); // list of lists of single page records

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

        int intMainEntityId;
        String mainEntityId;
        BillHelper mainEntityListHelper;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/rachunki":

                // get the necessary lists for the request
                mainEntityList = mainEntityFacade.findAll();
                lektorList = lektorFacade.findAll();

                // use helper to get lektor list prepared in our request
                mainEntityListHelper = new BillHelper(); //  we need a helper
                request = mainEntityListHelper.prepareEntityList(request, mainEntityList, lektorList);

                // and tell the container where to redirect
                userPath = "/organisation/bill/viewAll";
                break;
// ADD             
            case "/dodajRachunek":
                // just ask for a form

                // we need this list for this form
                lektorList = lektorFacade.findAll();
                request.setAttribute("lektorList", lektorList);

                userPath = "/organisation/bill/form";
                break;
// EDIT             
            case "/edytujRachunek":
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
                    mainEntity = mainEntityFacade.find(intMainEntityId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("id", mainEntity.getId());
                    request.setAttribute("numer", mainEntity.getNumer());
                    request.setAttribute("data", mainEntity.getData());
                    request.setAttribute("kwota", mainEntity.getKwota());
                    request.setAttribute("opis", mainEntity.getOpis());
                    request.setAttribute("lektor", mainEntity.getLektor());
                    request.setAttribute("lektorList", lektorFacade.findAll());
                }
                // then ask for a form 
                userPath = "/organisation/bill/form";

                break;
// SAVE
            case "/dodajRachunekZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String numer = request.getParameter("numer");
                String data = request.getParameter("data");
                String kwota = request.getParameter("kwota");
                String opis = request.getParameter("opis");
                String lektorId = request.getParameter("lektorId");

                intMainEntityId = persistenceManager.saveBillToDatabase(id, numer, data, kwota, opis, lektorFacade.find(Integer.parseInt(lektorId)));

                mainEntityId = intMainEntityId + "";
                request = prepareRequest(request, mainEntityId); // set all the attributes that request to view one needs

                // finally show the newly created lector (so it can be further processed)
                userPath = "/organisation/bill/viewOne";
                break;
// VIEW ONE
            case "/pokazRachunek":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                request = prepareRequest(request, request.getQueryString()); // set all the attributes that request needs

                userPath = "/organisation/bill/viewOne";
                break;

// FORWARD
        } // close main swith
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException ex) {
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
            case "/dodajRachunekPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String numer = request.getParameter("numer");
                String data = request.getParameter("data");
                String kwota = request.getParameter("kwota");
                String opis = request.getParameter("opis");
                String lektorId = request.getParameter("lektorId");

                boolean formError = false;

                if (!FormValidator.validateString(numer)) {
                    request.setAttribute("numerError", "*");
                    formError = true;
                }
                LocalDate localDateData;
                if ((localDateData = FormValidator.validateDate(data)) == null) {
                    request.setAttribute("dataError", "*");
                    formError = true;
                }
                BigDecimal bigDecimalKwota;
                if ((bigDecimalKwota = FormValidator.validateMoney(kwota)) == null) {
                    request.setAttribute("kwotaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(opis)) {
                    request.setAttribute("opisError", "*");
                    formError = true;
                }

                if (formError) {
                    request.setAttribute("formError", "formError");
                    userPath = "/organisation/bill/form";
                } else {
                    userPath = "/organisation/bill/confirm";
                }

                request.setAttribute("id", id);
                request.setAttribute("numer", numer);
                request.setAttribute("data", localDateData);
                request.setAttribute("kwota", bigDecimalKwota);
                request.setAttribute("opis", opis);
                request.setAttribute("lektor", lektorFacade.find(Integer.parseInt(lektorId)));
                request.setAttribute("lektorList", lektorFacade.findAll());

                // forward it to confirmation
                break;

// FORWARD
        } // close main swith
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException ex) {
        }
    }

    /**
     * This one prepares request to show one lector it is not to multiply code
     * when adding and showing new mainEntity entity
     */
    private HttpServletRequest prepareRequest(HttpServletRequest request, String mainEntityId) {

        mainEntity = mainEntityFacade.find(Integer.parseInt(mainEntityId));

        request.setAttribute("mainEntity", mainEntity);
        request.setAttribute("lektor", mainEntity.getLektor());

        return request;
    }

}
