/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.organisation;

import entity.Firma;
import helper.CustomerHelper;
import helper.LectorHelper;
import helper.ProgrammeListHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.FirmaFacade;
import session.StawkaFirmyFacade;
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
public class CustomerAdditionController extends HttpServlet {

    @EJB
    private FirmaFacade mainEntityFacade;
    private Firma mainEntity;

    @EJB
    private StawkaFirmyFacade stawkaFirmyFacade;
    private List stawkaFirmyList = new ArrayList();

    @EJB
    private PersistenceManager persistenceManager;

//    main
    int intKursId = 0;
    String kursId = "";

// additional
    int intFirmaId = 0;
    String firmaId = "";
    CustomerHelper customerListHelper;

// additional
    int intLektorId = 0;
    String lektorId = "";
    LectorHelper lectorListHelper;

// additional
    int intMainEntityId = 0;
    String mainEntityId = "";
    ProgrammeListHelper programmeListHelper;

//    GENERAL 
    private String userPath; // this one to see what to do

//    pagination
    List<List> listOfPages = new ArrayList<List>(); // list of lists of single page records

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
        
        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {

// REMOVE RATE (Native)
            case "/usunStawkeNativeSpeakeraDlaFirmy":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteCustomerNativeSpeakerRateFromDatabase(Integer.parseInt(mainEntityId));

                // prepare request for one customer
                request = prepareRequest(request, mainEntityId);
                
                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
                break;
// REMOVE RATE (Lector)
            case "/usunStawkeLektoraDlaFirmy":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteCustomerLectorRateFromDatabase(Integer.parseInt(mainEntityId));

                // prepare request for one customer
                request = prepareRequest(request, mainEntityId);
                
                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
                break;
                
// FORWARD
        } // close main swith
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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

        BigDecimal bigDecimalAmount;
        String stawka;
        
        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  ADD RATE (native)
            case "/dodajStawkeNativeSpeakeraDlaFirmy":

                // get Parameters
                mainEntityId = request.getParameter("id"); // it should be set if we are here
                stawka = request.getParameter("stawkaNative");

                // validate
                // if NOT OK go back
                // if OK persist
                if ((bigDecimalAmount = FormValidator.validateMoney(stawka)) == null) {
                    request.setAttribute("stawkaNativeError", "błędne dane");
                } else {
                    persistenceManager.saveCustomerNativeSpeakerRateToDatabase(Integer.parseInt(mainEntityId), bigDecimalAmount);
                }

                // prepare request for one customer
                request = prepareRequest(request, mainEntityId);
                
                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
                break;
                
//  ADD RATE (lector)
            case "/dodajStawkeLektoraDlaFirmy":

                // get Parameters
                mainEntityId = request.getParameter("id"); // it should be set if we are here
                stawka = request.getParameter("stawkaLektor");

                // validate
                // if NOT OK go back
                // if OK persist
                if ((bigDecimalAmount = FormValidator.validateMoney(stawka)) == null) {
                    request.setAttribute("stawkaLektorError", "błędne dane");
                } else {
                    persistenceManager.saveCustomerLectorRateToDatabase(Integer.parseInt(mainEntityId), bigDecimalAmount);
                }

                // prepare request for one customer
                request = prepareRequest(request, mainEntityId);
                
                // and tell the container where to redirect
                userPath = "/organisation/customer/viewOne";
                break;
                
// FORWARD
        } // close main swith
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This one prepares request to show one entity it is not to multiply code
     * when adding and showing new Entity entity
     */
    private HttpServletRequest prepareRequest(HttpServletRequest request, String firmaId) {

        mainEntity = mainEntityFacade.find(Integer.parseInt(firmaId));
        request.setAttribute("firma", mainEntity);

        stawkaFirmyList = stawkaFirmyFacade.findAll();
        request.setAttribute("stawkaFirmyList", stawkaFirmyList);

        return request;
    }

}
