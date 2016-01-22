/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.course;

import entity.Lekcja;
import entity.Obecnosc;
import helper.LessonHelper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.KursFacade;
import session.LekcjaFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "LessonController",
        loadOnStartup = 1,
        urlPatterns = {"/lekcje",
            "/edytujLekcje",
            "/pokazLekcje",
            "/dodajLekcje",
            "/dodajLekcjePotwierdz", // POST
            "/dodajLekcjeZapisz",
            "/dodajObecnosc",
            "/edytujLekcje"})
public class LessonController extends HttpServlet {

    // mainEntity meaning entity of this controller
    private Lekcja mainEntity;

    @EJB
    private KursFacade kursFacade;

    @EJB
    private LekcjaFacade mainEntityFacade;

    @EJB
    LessonHelper mainEntityHelper;

    @EJB
    private PersistenceManager persistenceManager;

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

        int intLekcjaId;
        String lekcjaId;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/lekcje":

                // use helper to get entity list prepared in our request
                request = mainEntityHelper.prepareEntityList(request);

                // and tell the container where to redirect
                userPath = "/course/lesson/viewAll";
                break;

// ADD             
            case "/dodajLekcje":

                // all we need is in parameters
                request.setAttribute("kursId", request.getQueryString());

                // and ask for a form
                userPath = "/course/lesson/form";
                break;
// EDIT             
            case "/edytujLekcje":
                // get mainEntityId from request
                lekcjaId = request.getQueryString();

                // cast it to the integer
                try {
                    intLekcjaId = Integer.parseInt(lekcjaId);
                } catch (NumberFormatException e) {
                    intLekcjaId = 0; // (it seems that we have some kind of a problem)
                }

                if (intLekcjaId > 0) {

                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    mainEntity = mainEntityFacade.find(intLekcjaId);

                    request.setAttribute("id", mainEntity.getId());
                    request.setAttribute("data", mainEntity.getData());
                    request.setAttribute("odwolana", mainEntity.getOdwolana());
                    request.setAttribute("kurs", mainEntity.getKurs());
                }
                // then ask for a form
                userPath = "/course/lesson/form";

                break;
// SAVE
            case "/dodajLekcjeZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String data = request.getParameter("data");
                String odwolana = request.getParameter("odwolana");
                String kursId = request.getParameter("kursId");

                intLekcjaId = persistenceManager.saveLessonToDatabase(id, data, Boolean.parseBoolean(odwolana),
                        kursFacade.find(Integer.parseInt(kursId)));

                lekcjaId = intLekcjaId + "";

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, lekcjaId);

                // prepare redirect
                userPath = "/course/lesson/viewOne";
                break;

// VIEW ONE
            case "/pokazLekcje":

                lekcjaId = request.getQueryString();

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, lekcjaId);

                // prepare redirect
                userPath = "/course/lesson/viewOne";
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

        String lekcjaId;
        
        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
// CONFIRM
            case "/dodajLekcjePotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String data = request.getParameter("data");
                Boolean odwolana = Boolean.parseBoolean(request.getParameter("ocena"));

                boolean formError = false;

                LocalDate localDateData;
                if ((localDateData = FormValidator.validateDate(data)) == null) {
                    request.setAttribute("dataError", "*");
                    formError = true;
                }

                if (formError) {
                    userPath = "/course/lesson/form";
                } else {
                    userPath = "/course/lesson/confirm";
                }

                // we should need these
                request.setAttribute("id", id);
                request.setAttribute("odwolana", odwolana);
                request.setAttribute("kurs", kursFacade.find(Integer.parseInt(request.getParameter("kursId"))));

                // forward it to confirmation
                break;

// ADD PRESENCE
            case "/dodajObecnosc":

                // prepare main Entity
                lekcjaId = request.getParameter("lekcjaId");
                mainEntity = mainEntityFacade.find(Integer.parseInt(lekcjaId));

                // get list of obecnosc for this lekcja
                List obecnoscList = (List) mainEntity.getObecnoscCollection();

                // in just one loop check if obecny or not obecny
                Iterator it = obecnoscList.iterator();
                while (it.hasNext()) {
                    Obecnosc obecnosc = (Obecnosc) it.next();

                    // check what user has chosen:
                    Boolean obecny = Boolean.parseBoolean(request.getParameter(obecnosc.getKursant().getId().toString()));

                    // then check if value is different
                    if (!obecnosc.getObecny().equals(obecny)) {

                        // change the value in Entity and persis
                        persistenceManager.savePresence(false, mainEntity, obecnosc.getKursant(), obecny);

                    }
                }

                // use helper to get lektor list prepared in our request
                request.setAttribute("lekcjaId", lekcjaId);

                // prepare redirect
                userPath = "/course/lesson/confirmation";
                break;

// FORWARD
// FORWARD
        } // close main swith
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException | IOException ex) {
        }
    }

    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new mainEntity entity
     */
    private HttpServletRequest prepareRequest(HttpServletRequest request, String mainEntityId) {

        return request;
    }

}
