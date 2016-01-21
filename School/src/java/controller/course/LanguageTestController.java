/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.course;

import entity.Kurs;
import entity.Kursant;
import entity.Test;
import helper.LanguageTestHelper;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.FirmaFacade;
import session.KursFacade;
import session.KursantFacade;
import session.TestFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "LanguageTestController",
        loadOnStartup = 1,
        urlPatterns = {"/testy",
            "/pokazTest",
            "/dodajTest",
            "/dodajTestPotwierdz", // POST
            "/dodajTestZapisz",
            "/pokazTest",
            "/edytujTest"})
public class LanguageTestController extends HttpServlet {

    @EJB
    FirmaFacade firmaFacade;

    @EJB
    KursFacade kursFacade;

    @EJB
    KursantFacade kursantFacade;

    @EJB
    LanguageTestHelper testHelper;

    @EJB
    TestFacade testFacade;

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

        Test test;
        int intTestId;
        String kursId;
        
        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/testy":

                // use helper to get entity list prepared in our request
                request = testHelper.prepareEntityList(request);

                // and tell the container where to redirect
                userPath = "/course/languageTest/viewAll";
                break;

// VIEW ONE
            case "/pokazTest":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                request = testHelper.prepareEntityView(request, Integer.parseInt(request.getParameter("testId"))); // set all the attributes that request needs

                userPath = "/course/languageTest/viewOne";
                break;

// ADD             
            case "/dodajTest":

                request.setAttribute("kursId", request.getParameter("kursId"));
                request.setAttribute("kursantId", request.getParameter("kursantId"));
                
                // and ask for a form
                userPath = "/course/languageTest/form";
                break;
// EDIT             
            case "/edytujTest":
                // get mainEntityId from request
                kursId = request.getQueryString();

                // cast it to the integer
                try {
                    intTestId = Integer.parseInt(kursId);
                } catch (NumberFormatException e) {
                    intTestId = 0; // (it seems that we have some kind of a problem)
                }

                if (intTestId > 0) {

                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    test = testFacade.find(Integer.parseInt(kursId));
                    
                    request.setAttribute("id", test.getId());
                    request.setAttribute("rodzaj", test.getRodzaj());
                    request.setAttribute("ocena", test.getOcena());
                    Kurs kurs = (Kurs) test.getKurs();
                    request.setAttribute("kursId", kurs.getId());
                    Kursant kursant = (Kursant) test.getKursant();
                    request.setAttribute("kursantId", kursant.getId());
                }
                // then ask for a form
                request.setAttribute("firmaList", firmaFacade.findAll());
                userPath = "/course/languageTest/form";

                break;
// SAVE
            case "/dodajTestZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String rodzaj = request.getParameter("rodzaj");
                String ocena = request.getParameter("ocena");
                kursId = request.getParameter("kursId");
                String kursantId = request.getParameter("kursantId");

                intTestId = persistenceManager.saveLanguageTestToDatabase(id, rodzaj, Integer.parseInt(ocena),
                        kursFacade.find(Integer.parseInt(kursId)), kursantFacade.find(Integer.parseInt(kursantId)));

                // finally show the newly created entity (so it can be further processed)
                request.setAttribute("kursId", kursId);
                request.setAttribute("kursantId", kursantId);

                // use helper to get entity list prepared in our request
                request = testHelper.prepareEntityView(request, intTestId);

                userPath = "/course/languageTest/viewOne";
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
            case "/dodajTestPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String rodzaj = request.getParameter("rodzaj");
                String ocena = request.getParameter("ocena");

                boolean formError = false;

                if (!FormValidator.validateString(rodzaj)) {
                    request.setAttribute("rodzajError", "*");
                    formError = true;
                }
                if (FormValidator.validateInteger(ocena) == null) {
                    request.setAttribute("ocenaError", "*");
                    formError = true;
                }

                if (formError) {
                    userPath = "/course/languageTest/form";
                } else {
                    userPath = "/course/languageTest/confirm";
                }

                // we should need these
                request.setAttribute("id", id);
                request.setAttribute("rodzaj", rodzaj);
                request.setAttribute("ocena", ocena);

                // no need for kurs and kursant Ids because they are in parameters
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
