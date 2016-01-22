/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import helper.QuestionnaireHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.persistence.PersistenceManager;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "QuestionnaireController",
        loadOnStartup = 1,
        urlPatterns = {"/ankiety",
            "/pokazAnkiete",
            "/dodajAnkiete",
            "/dodajAnkietePotwierdz", // POST
            "/dodajAnkieteZapisz",
            "/edytujAnkiete"})
public class QuestionnaireController extends HttpServlet {

    @EJB
    QuestionnaireHelper questionnaireHelper;

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

        String ankietaId;
        ServletContext ankietaContext;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/ankiety":

                // use helper to get entity list prepared in our request
                request = questionnaireHelper.prepareEntityList(request);

                // and tell the container where to redirect
                userPath = "/methodology/questionnaire/viewAll";
                break;

// VIEW ONE
            case "/pokazAnkiete":

                ankietaId = request.getQueryString();

                // use helper to get lektor list prepared in our request
                request = questionnaireHelper.prepareEntityView(request, ankietaId);

                // prepare redirect
                userPath = "/methodology/questionnaire/viewOne";
                break;

// ADD             
            case "/dodajAnkiete":

                // get ServletContext because we will need it
                ankietaContext = (ServletContext) request.getSession().getServletContext();
                // set attributes
                String kursantId = request.getParameter("kursantId");
                String lektorId = request.getParameter("lektorId");
                request.setAttribute("kursantId", kursantId);
                request.setAttribute("lektorId", lektorId);
                request.setAttribute("listaPytan", ankietaContext.getAttribute("listOfQuestions"));
                request.setAttribute("pytanieOpisowe", ankietaContext.getAttribute("finalQuestion"));

                // and ask for a form
                userPath = "/methodology/questionnaireForm";
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

// VALIDATE
            case "/dodajAnkietePotwierdz":

                // first we validate answers (selected or not)
                List answerList = new ArrayList(); // with answers
                String formError = ""; // indicates error
                List errorList = new ArrayList(); // list of errors paralel to pytanieList
                for (int i = 0; i < 22; i++) {
                    String answer = request.getParameter("pytanie" + i);
                    if (answer == null || answer.equals("")) {
                        answerList.add("");
                        errorList.add("*");
                        formError = "formError";

                    } else {
                        answerList.add(answer);
                        errorList.add("");
                    }
                }
                String answer23 = request.getParameter("pytanie23");
                if (answer23 == null) {
                    answer23 = ""; // no need to have an answer to this one
                }

                // set common attributes
                String kursantId = request.getParameter("kursantId");
                String lektorId = request.getParameter("lektorId");

                // decide what to do forward
                if (formError.equals("formError")) {
                    // get ServletContext because we will need it
                    ServletContext ankietaContext = (ServletContext) request.getSession().getServletContext();

                    // set attributes specific to this path
                    request.setAttribute("listaPytan", ankietaContext.getAttribute("listOfQuestions"));
                    request.setAttribute("pytanieOpisowe", ankietaContext.getAttribute("finalQuestion"));
                    request.setAttribute("kursantId", kursantId);
                    request.setAttribute("lektorId", lektorId);

                    // set answers to request
                    request.setAttribute("answerList", answerList);
                    request.setAttribute("answer23", answer23);
                    // errors
                    request.setAttribute("formError", formError);
                    request.setAttribute("errorList", errorList);

                    userPath = "/methodology/questionnaireForm";
                } else {
                    // persist
                    boolean isAlreadyThere = persistenceManager.saveQuestionnaire(kursantId, lektorId, answerList, answer23);
                    request.setAttribute("isAlreadyThere", isAlreadyThere);
                    userPath = "/methodology/questionnaireConfirmation";
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

    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new mainEntity entity
     */
    private HttpServletRequest prepareRequest(HttpServletRequest request, String ankietaId) {

        return request;
    }

}
