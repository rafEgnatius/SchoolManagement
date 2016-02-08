/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.organisation;

import helper.CustomerHelper;
import java.io.IOException;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "CustomerAdditionController",
        loadOnStartup = 1,
        urlPatterns = {"/dodajStawkeNativeSpeakeraDlaFirmy",
            "/dodajStawkeLektoraDlaFirmy",
            "/usunStawkeNativeSpeakeraDlaFirmy",
            "/usunStawkeLektoraDlaFirmy"})
@ServletSecurity(
    @HttpConstraint(rolesAllowed = {"schoolAdmin", "organizator"})
)
public class CustomerAdditionController extends HttpServlet {

    @EJB
    CustomerHelper customerHelper;
    
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

        String firmaId;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {

// REMOVE RATE (Native)
            case "/usunStawkeNativeSpeakeraDlaFirmy":

                // check parameters
                firmaId = request.getQueryString();

                // now persist:
                persistenceManager.deleteCustomerNativeSpeakerRateFromDatabase(Integer.parseInt(firmaId));

                // prepare request for one customer
                request = customerHelper.prepareEntityView(request, firmaId);

                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
                break;
// REMOVE RATE (Lector)
            case "/usunStawkeLektoraDlaFirmy":

                // check parameters
                firmaId = request.getQueryString();

                // now persist:
                persistenceManager.deleteCustomerLectorRateFromDatabase(Integer.parseInt(firmaId));

                // prepare request for one customer
                request = customerHelper.prepareEntityView(request, firmaId);

                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
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

        String firmaId;

        BigDecimal bigDecimalAmount;
        String stawka;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  ADD RATE (native)
            case "/dodajStawkeNativeSpeakeraDlaFirmy":

                // get Parameters
                firmaId = request.getParameter("id"); // it should be set if we are here
                stawka = request.getParameter("stawkaNative");

                // validate
                // if NOT OK go back
                // if OK persist
                if ((bigDecimalAmount = FormValidator.validateMoney(stawka)) == null) {
                    request.setAttribute("stawkaNativeError", "błędne dane");
                } else {
                    persistenceManager.saveCustomerNativeSpeakerRateToDatabase(Integer.parseInt(firmaId), bigDecimalAmount);
                }

                // prepare request for one customer
                request = customerHelper.prepareEntityView(request, firmaId);

                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
                break;

//  ADD RATE (lector)
            case "/dodajStawkeLektoraDlaFirmy":

                // get Parameters
                firmaId = request.getParameter("id"); // it should be set if we are here
                stawka = request.getParameter("stawkaLektor");

                // validate
                // if NOT OK go back
                // if OK persist
                if ((bigDecimalAmount = FormValidator.validateMoney(stawka)) == null) {
                    request.setAttribute("stawkaLektorError", "błędne dane");
                } else {
                    persistenceManager.saveCustomerLectorRateToDatabase(Integer.parseInt(firmaId), bigDecimalAmount);
                }

                // prepare request for one customer
                request = customerHelper.prepareEntityView(request, firmaId);

                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
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
