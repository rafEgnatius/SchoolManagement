/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.course;

import entity.Jezyk;
import entity.Kurs;
import helper.CourseHelper;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.JezykFacade;
import session.KursFacade;
import session.persistence.PersistenceManager;
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

    @EJB
    private JezykFacade jezykFacade;

    @EJB
    private PersistenceManager persistenceManager;

    @EJB
    private CourseHelper kursHelper;
    
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

        Kurs kurs;
        Jezyk jezyk;
        List jezykList;
        
        int intKursId;
        String kursId;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
// VIEW ALL
            case "/kursy":

                // use helper to get entity list prepared in our request
                request = kursHelper.prepareEntityList(request);

                // and tell the container where to redirect
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
                kursId = request.getQueryString();

                // cast it to the integer
                try {
                    intKursId = Integer.parseInt(kursId);
                } catch (NumberFormatException e) {
                    intKursId = 0; // (it seems that we have some kind of a problem)
                }

                if (intKursId > 0) {
                    // find the lektor entity
                    kurs = kursFacade.find(intKursId);
                    //request.setAttribute("kurs", kurs);

                    request.setAttribute("id", kursId);
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

                intKursId = persistenceManager.saveCourseToDatabase(id, rok, semestr, jezyk, symbol, opis, sala);
                kursId = intKursId + ""; // de facto cast it to String

                // use helper to get lektor list prepared in our request
                request = kursHelper.prepareEntityView(request, kursId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// VIEW ONE
            case "/pokazKurs":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: entity etc.

                kursId = request.getQueryString();

                System.out.println(kursFacade);

                // use helper to get lektor list prepared in our request
                request = kursHelper.prepareEntityView(request, kursId);

                // prepare redirect
                userPath = "/course/course/viewOne";
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

        Jezyk jezyk;
        List jezykList;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

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
        } catch (ServletException | IOException ex) {
        }
    }
}
