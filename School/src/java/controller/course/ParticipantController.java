/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.course;

import entity.Firma;
import entity.Jezyk;
import entity.Kursant;
import helper.ParticipantHelper;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.FirmaFacade;
import session.JezykFacade;
import session.KursantFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "ParticipantController",
        loadOnStartup = 1,
        urlPatterns = {"/kursanci",
            "/dodajJezykKursanta",
            "/pokazKursanta",
            "/dodajKursanta",
            "/dodajKursantaPotwierdz", // POST
            "/dodajKursantaZapisz",
            "/edytujKursanta",
            "/usunJezykKursanta"})
public class ParticipantController extends HttpServlet {

    // kursant meaning entity of this controller
    @EJB
    KursantFacade kursantFacade;

    @EJB
    FirmaFacade firmaFacade;

    @EJB
    JezykFacade jezykFacade;

    @EJB
    ParticipantHelper participantHelper;

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

        Kursant kursant;
        Firma firma;

        int intMainEntityId;
        String kursantId;

        String jezykId;
        Jezyk jezyk;
        String poziom;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/kursanci":

                request = participantHelper.prepareEntityList(request);

                // and tell the container where to redirect
                userPath = "/course/participant/viewAll";
                break;
// ADD             
            case "/dodajKursanta":

                // just set attribute
                request.setAttribute("firmaList", firmaFacade.findAll());

                // and ask for a form
                userPath = "/course/participant/form";
                break;
// EDIT             
            case "/edytujKursanta":
                // get kursantId from request
                kursantId = request.getQueryString();

                // cast it to the integer
                try {
                    intMainEntityId = Integer.parseInt(kursantId);
                } catch (NumberFormatException e) {
                    intMainEntityId = 0; // (it seems that we have some kind of a problem)
                }

                if (intMainEntityId > 0) {
                    // find the main entity
                    kursant = kursantFacade.find(intMainEntityId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("id", kursant.getId());
                    request.setAttribute("nazwa", kursant.getNazwa());
                    request.setAttribute("telefon", kursant.getTelefon());
                    request.setAttribute("email", kursant.getEmail());
                    request.setAttribute("firma", kursant.getFirma());
                }
                // then ask for a form
                request.setAttribute("firmaList", firmaFacade.findAll());
                userPath = "/course/participant/form";

                break;
// SAVE
            case "/dodajKursantaZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String nazwa = request.getParameter("nazwa");
                String telefon = request.getParameter("telefon");
                String email = request.getParameter("email");
                firma = firmaFacade.find(Integer.parseInt(request.getParameter("firmaId")));

                intMainEntityId = persistenceManager.saveParticipantToDatabase(id, nazwa, telefon, email, firma);

                kursantId = intMainEntityId + "";
                request = participantHelper.prepareEntityView(request, kursantId); // set all the attributes that request needs

                // finally show the newly created entity (so it can be further processed)
                userPath = "/course/participant/viewOne";
                break;
// VIEW ONE
            case "/pokazKursanta":
                // first get entity id from request
                // then prepare another lists that we will need

                request = participantHelper.prepareEntityView(request, request.getQueryString()); // set all the attributes that request needs

                userPath = "/course/participant/viewOne";
                break;
// ADD PARTICIPANT'S LANGUAGE
            case "/dodajJezykKursanta":
                // first: get three values from the form
                kursantId = request.getParameter("kursantId");
                jezykId = request.getParameter("jezykId");
                poziom = request.getParameter("poziom"); // we are looking for "ON" value

                // in case we ran out of languages and do not want user to see big fat 500
                if (jezykId != null) {
                    kursant = kursantFacade.find(Integer.parseInt(kursantId)); // we should try/catch it later
                    jezyk = jezykFacade.find(Integer.parseInt(jezykId)); // we should try/catch it later
                    // now persist:
                    persistenceManager.saveParticipantsLanguageToDatabase(kursant, jezyk, poziom);
                }

                request = participantHelper.prepareEntityView(request, kursantId); // set all the attributes that request needs

                userPath = "/course/participant/viewOne"; // and show once more - now with another language
                break;
// REMOVE PARTICIPANT'S LANGUAGE
            case "/usunJezykKursanta":
                kursantId = request.getParameter("kursantId");
                jezykId = request.getParameter("jezykId");

                kursant = kursantFacade.find(Integer.parseInt(kursantId)); // we should try/catch it later
                jezyk = jezykFacade.find(Integer.parseInt(jezykId)); // we should try/catch it later

                // now persist:
                persistenceManager.deleteParticipantsLanguageFromDatabase(kursant, jezyk);

                request = participantHelper.prepareEntityView(request, kursantId); // set all the attributes that request needs
                userPath = "/course/participant/viewOne"; // and show once more - now with another language
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
            case "/dodajKursantaPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String nazwa = request.getParameter("nazwa");
                String telefon = request.getParameter("telefon");
                String email = request.getParameter("email");
                String firmaId = request.getParameter("firmaId");

                boolean formError = false;

                if (!FormValidator.validateString(nazwa)) {
                    request.setAttribute("nazwaError", "*");
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

                if (formError) {
                    request.setAttribute("formError", "formError");

                    userPath = "/course/participant/form";
                } else {
                    request.setAttribute("id", id);
                    userPath = "/course/participant/confirm";
                }

                request.setAttribute("id", id);
                request.setAttribute("nazwa", nazwa);
                request.setAttribute("telefon", telefon);
                request.setAttribute("email", email);
                request.setAttribute("firma", firmaFacade.find(Integer.parseInt(firmaId)));
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
