/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.organisation;

import entity.Wplata;
import helper.MoneyInHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.FirmaFacade;
import session.WplataFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "MoneyInController",
        loadOnStartup = 1,
        urlPatterns = {"/wplaty",
            "/pokazWplate",
            "/dodajWplate",
            "/dodajWplatePotwierdz",
            "/dodajWplateZapisz",
            "/edytujWplate"})
public class MoneyInController extends HttpServlet {

    @EJB
    WplataFacade mainEntityFacade;

    @EJB
    FirmaFacade firmaFacade;

    @EJB
    MoneyInHelper moneyInHelper;

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

        Wplata wplata;
        int intWplataId;
        String wplataId;
        List firmaList;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  VIEW ALL
            case "/wplaty":

                // use helper to get list prepared in our request
                request = moneyInHelper.prepareEntityList(request);

                // and tell the container where to redirect
                userPath = "/organisation/moneyIn/viewAll";
                break;
// ADD             
            case "/dodajWplate":
                // just ask for a form

                // we need this list for this form
                firmaList = firmaFacade.findAll();
                request.setAttribute("firmaList", firmaList);

                userPath = "/organisation/moneyIn/form";
                break;
// EDIT             
            case "/edytujWplate":
                // get lektorId from request
                wplataId = request.getQueryString();

                // cast it to the integer
                try {
                    intWplataId = Integer.parseInt(wplataId);
                } catch (NumberFormatException e) {
                    intWplataId = 0; // (it seems that we have some kind of a problem)
                }

                if (intWplataId > 0) {
                    // find the lektor entity
                    wplata = mainEntityFacade.find(intWplataId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("id", wplata.getId());
                    request.setAttribute("data", wplata.getData());
                    request.setAttribute("kwota", wplata.getKwota());
                    request.setAttribute("opis", wplata.getOpis());
                    request.setAttribute("firma", wplata.getFirma());
                    request.setAttribute("firmaList", firmaFacade.findAll());
                }
                // then ask for a form 
                userPath = "/organisation/moneyIn/form";

                break;
// SAVE
            case "/dodajWplateZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String data = request.getParameter("data");
                String kwota = request.getParameter("kwota");
                String opis = request.getParameter("opis");
                String firmaId = request.getParameter("firmaId");

                intWplataId = persistenceManager.saveMoneyInToDatabase(id, data, kwota, opis, firmaFacade.find(Integer.parseInt(firmaId)));

                wplataId = intWplataId + "";
                request = prepareEntityView(request, wplataId); // set all the attributes that request needs

                // finally show the newly created lector (so it can be further processed)
                userPath = "/organisation/moneyIn/viewOne";
                break;
// VIEW ONE
            case "/pokazWplate":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                request = prepareEntityView(request, request.getQueryString()); // set all the attributes that request needs

                userPath = "/organisation/moneyIn/viewOne";
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
            case "/dodajWplatePotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String data = request.getParameter("data");
                String kwota = request.getParameter("kwota");
                String opis = request.getParameter("opis");
                String firmaId = request.getParameter("firmaId");

                boolean formError = false;

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
                    userPath = "/organisation/moneyIn/form";
                } else {
                    userPath = "/organisation/moneyIn/confirm";
                }

                request.setAttribute("id", id);
                request.setAttribute("data", localDateData);
                request.setAttribute("kwota", bigDecimalKwota);
                request.setAttribute("opis", opis);
                request.setAttribute("firma", firmaFacade.find(Integer.parseInt(firmaId)));
                request.setAttribute("firmaList", firmaFacade.findAll());

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
    private HttpServletRequest prepareEntityView(HttpServletRequest request, String wplataId) {

        Wplata wplata = mainEntityFacade.find(Integer.parseInt(wplataId));

        request.setAttribute("mainEntity", wplata);
        request.setAttribute("firma", wplata.getFirma());

        return request;
    }

}
