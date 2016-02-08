/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import entity.Jezyk;
import entity.Podrecznik;
import helper.TextbookHelper;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.JezykFacade;
import session.PodrecznikFacade;
import session.WypozyczenieFacade;
import session.persistence.PersistenceManager;
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
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"schoolAdmin", "metodyk"})
)
public class TextbookController extends HttpServlet {

    @EJB
    JezykFacade jezykFacade;
    
    @EJB
    PodrecznikFacade podrecznikFacade;

    @EJB
    WypozyczenieFacade wypozyczenieFacade;

    @EJB
    PersistenceManager persistenceManager;
    
    @EJB
    TextbookHelper textbookHelper;

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
        List jezykList;
        Podrecznik podrecznik;
        
//    jezyk
        int intJezykId;
        String jezykId;

//    podrecznik
        int intPodrecznikId;
        String podrecznikId;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/podreczniki":
                // use helper to get list prepared in our request
                request = textbookHelper.prepareEntityList(request);

                // and tell the container where to redirect
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
                request = textbookHelper.prepareEntityView(request, podrecznikId); // set all the attributes that request needs

                // finally show the newly created lector (so it can be further processed)
                userPath = "/methodology/textbook/viewOne";
                break;
// VIEW ONE
            case "/pokazPodrecznik":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                request = textbookHelper.prepareEntityView(request, request.getQueryString()); // set all the attributes that request needs

                userPath = "/methodology/textbook/viewOne";
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
        
        //    jezyk
        Jezyk jezyk;
        int intJezykId;
        String jezykId;        

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

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
        } catch (ServletException | IOException ex) {
        }

    }

   

}
