/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import entity.Jezyk;
import entity.Lektor;
import helper.LectorHelper;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.JezykFacade;
import session.JezykLektoraFacade;
import session.LektorFacade;
import session.PodrecznikFacade;
import session.WypozyczenieFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "LectorController",
        loadOnStartup = 1,
        urlPatterns = {"/lektorzy",
            "/pokazLektora",
            "/dodajLektora",
            "/dodajLektoraPotwierdz", // POST
            "/dodajLektoraZapisz",
            "/edytujLektora",
            "/dodajJezykLektora",
            "/usunJezykLektora"})
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"schoolAdmin", "metodyk"})
)
public class LectorController extends HttpServlet {

    // mainEntity meaning entity of this controller
    @EJB
    LektorFacade mainEntityFacade;

    @EJB
    JezykFacade jezykFacade;

    @EJB
    JezykLektoraFacade jezykLektoraFacade;

    @EJB
    PodrecznikFacade podrecznikFacade;

    @EJB
    WypozyczenieFacade wypozyczenieFacade;

    @EJB
    LectorHelper lectorHelper;

    @EJB
    PersistenceManager persistenceManager;

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

        Lektor lektor;
        int intLektorId;
        String lektorId;
        
        Jezyk jezyk;
        String jezykId;
        
        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/lektorzy":

                request = lectorHelper.prepareEntityList(request);

                // and tell the container where to redirect
                userPath = "/methodology/lector/viewAll";
                break;
// ADD             
            case "/dodajLektora":
                // just ask for a form

                userPath = "/methodology/lector/form";
                break;
// EDIT             
            case "/edytujLektora":
                // get lektorId from request
                lektorId = request.getQueryString();

                // cast it to the integer
                try {
                    intLektorId = Integer.parseInt(lektorId);
                } catch (NumberFormatException e) {
                    intLektorId = 0; // (it seems that we have some kind of a problem)
                }

                if (intLektorId > 0) {
                    // find the lektor intLektorId
                    lektor = mainEntityFacade.find(intLektorId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("id", lektor.getId());
                    request.setAttribute("nazwa", lektor.getNazwa());
                    request.setAttribute("miasto", lektor.getMiasto());
                    request.setAttribute("telefon", lektor.getTelefon());
                    request.setAttribute("email", lektor.getEmail());
                    request.setAttribute("umowa", lektor.getUmowa());
                    request.setAttribute("nip", lektor.getNip());
                }
                // then ask for a form 
                userPath = "/methodology/lector/form";

                break;
// SAVE
            case "/dodajLektoraZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String nazwa = request.getParameter("nazwa");
                String miasto = request.getParameter("miasto");
                String telefon = request.getParameter("telefon");
                String email = request.getParameter("email");
                String umowa = request.getParameter("umowa");
                String nip = request.getParameter("nip");

                intLektorId = persistenceManager.saveLectorToDatabase(id, nazwa, miasto, telefon, email, umowa, nip);

                lektorId = intLektorId + "";
                request = lectorHelper.prepareEntityView(request, lektorId); // set all the attributes that request needs

                // finally show the newly created lector (so it can be further processed)
                userPath = "/methodology/lector/viewOne";
                break;
// VIEW ONE
            case "/pokazLektora":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                request = lectorHelper.prepareEntityView(request, request.getQueryString()); // set all the attributes that request needs

                userPath = "/methodology/lector/viewOne";
                break;

// ADD LECTOR'S LANGUAGE
            case "/dodajJezykLektora":
                // first: get three values from the form
                lektorId = request.getParameter("lektorId");
                jezykId = request.getParameter("jezykId");
                String stringNativeSpeaker = request.getParameter("nativeSpeaker"); // we are looking for "ON" value
                boolean nativeSpeaker; //  lets sort out a boolean
                if (stringNativeSpeaker != null) {
                    nativeSpeaker = stringNativeSpeaker.equals("ON");
                } else {
                    nativeSpeaker = false;
                }

                lektor = mainEntityFacade.find(Integer.parseInt(lektorId)); // we should try/catch it later
                jezyk = jezykFacade.find(Integer.parseInt(jezykId)); // we should try/catch it later

                // now persist:
                persistenceManager.saveLectorsLanguageToDatabase(lektor, jezyk, nativeSpeaker);

                request = lectorHelper.prepareEntityView(request, lektorId); // set all the attributes that request needs

                userPath = "/methodology/lector/viewOne"; // and show once more - now with another language
                break;
// REMOVE LECTOR'S LANGUAGE
            case "/usunJezykLektora":
                lektorId = request.getParameter("lektorId");
                jezykId = request.getParameter("jezykId");

                lektor = mainEntityFacade.find(Integer.parseInt(lektorId)); // we should try/catch it later
                jezyk = jezykFacade.find(Integer.parseInt(jezykId)); // we should try/catch it later

                // now persist:
                persistenceManager.deleteLectorsLanguageFromDatabase(lektor, jezyk);

                request = lectorHelper.prepareEntityView(request, lektorId); // set all the attributes that request needs
                userPath = "/methodology/lector/viewOne"; // and show once more - now with another language
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
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
// CONFIRM
            case "/dodajLektoraPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String nazwa = request.getParameter("nazwa");
                String miasto = request.getParameter("miasto");
                String telefon = request.getParameter("telefon");
                String email = request.getParameter("email");
                String umowa = request.getParameter("umowa");
                String nip = request.getParameter("nip");

                boolean formError = false;

                if (!FormValidator.validateString(nazwa)) {
                    request.setAttribute("nazwaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(miasto)) {
                    request.setAttribute("miastoError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(telefon)) {
                    request.setAttribute("telefonError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(email)) {
                    request.setAttribute("emailError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(umowa)) {
                    request.setAttribute("umowaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(nip)) {
                    request.setAttribute("nipError", "*");
                    formError = true;
                }

                if (formError) {
                    request.setAttribute("formError", "formError");

                    request.setAttribute("id", id);
                    request.setAttribute("nazwa", nazwa);
                    request.setAttribute("miasto", miasto);
                    request.setAttribute("telefon", telefon);
                    request.setAttribute("email", email);
                    request.setAttribute("umowa", umowa);
                    request.setAttribute("nip", nip);

                    userPath = "/methodology/lector/form";
                } else {
                    request.setAttribute("id", id);
                    userPath = "/methodology/lector/confirm";
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
