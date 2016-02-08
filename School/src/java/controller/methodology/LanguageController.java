/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import entity.Jezyk;
import helper.LanguageHelper;
import java.io.IOException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.JezykFacade;
import session.persistence.PersistenceManager;
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
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"schoolAdmin", "metodyk"})
)
public class LanguageController extends HttpServlet {

    @EJB
    JezykFacade jezykFacade;

    @EJB
    PersistenceManager persistenceManager;
    
    @EJB
    LanguageHelper languageHelper;

    @Resource(name = "pageSize")
    Integer pageSize;

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

        Jezyk jezyk;
        int intJezykId;
        String jezykId;
        
        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath();

        switch (userPath) {
// VIEW ALL
            case "/jezyki":
                // we need here two lists - a <lektor> list and <jezykLektora> list
                // and we need to combine them somehow
                // and additionally <jezyk> list

                request = languageHelper.prepareEntityList(request);

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

                jezykId = request.getQueryString();
                jezyk = jezykFacade.find(Integer.parseInt(jezykId));
                request.setAttribute("jezyk", jezyk);
                userPath = "/methodology/language/viewOne";
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

        request.setCharacterEncoding("UTF-8"); // for Polish characters

        String userPath = request.getServletPath();

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
        } catch (ServletException | IOException ex) {
        }
    }
}
