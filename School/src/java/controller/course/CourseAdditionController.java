/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.course;

import entity.Kurs;
import helper.CourseHelper;
import helper.CustomerListHelper;
import helper.LectorListHelper;
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
import session.JezykFacade;
import session.JezykLektoraFacade;
import session.KursFacade;
import session.LektorFacade;
import session.ProgramFacade;
import session.StawkaFirmyFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "CourseAdditionController",
        loadOnStartup = 1,
        urlPatterns = {"/dodajFirmeDoKursu",
            "/dodajLektoraDoKursu",
            "/dodajProgramDoKursu",
            "/dodajStawkeLektora",
            "/usunFirmeZKursu",
            "/usunLektoraZKursu",
            "/usunProgramZKursu",
            "/usunStawkeLektora",
            "/zapiszDodanieFirmyDoKursu",
            "/zapiszDodanieLektoraDoKursu",
            "/zapiszDodanieProgramuDoKursu"})
public class CourseAdditionController extends HttpServlet {

    @EJB
    private KursFacade kursFacade;
    private Kurs kurs;

    @EJB
    private FirmaFacade firmaFacade;
    private List firmaList = new ArrayList();

    @EJB
    private JezykFacade jezykFacade;
    private List jezykList = new ArrayList();

    @EJB
    private JezykLektoraFacade jezykLektoraFacade;
    private List jezykLektoraList = new ArrayList();

    @EJB
    private LektorFacade lektorFacade;
    private List lektorList = new ArrayList();
    
    @EJB
    private ProgramFacade programFacade;
    private List programList = new ArrayList();

    @EJB
    private StawkaFirmyFacade stawkaFirmyFacade;
    
    @EJB
    private PersistenceManager persistenceManager;

//    main
    int intMainEntityId = 0;
    String mainEntityId = "";

// additional
    int intFirmaId = 0;
    String firmaId = "";
    CustomerListHelper customerListHelper;

// additional
    int intLektorId = 0;
    String lektorId = "";
    LectorListHelper lectorListHelper;

// additional
    int intProgramId = 0;
    String programId = "";
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

        CourseHelper courseHelper = new CourseHelper();
        
        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  ADD CUSTOMER
            case "/dodajFirmeDoKursu":

                mainEntityId = request.getParameter("kursId"); // it should be set if we are here

                customerListHelper = new CustomerListHelper(); //  we need a helper

                // get the necessary lists for the request
                firmaList = firmaFacade.findAll();

                // use helper to get entity list prepared in our request
                request = customerListHelper.prepareEntityList(request, firmaList);

                // tell the kurs id
                request.setAttribute("kursId", mainEntityId);

                // and tell the container where to redirect
                userPath = "/course/course/addCustomer";
                break;

// SAVE CUSTOMER
            case "/zapiszDodanieFirmyDoKursu":
                // it is confirmed so add to database

                // check parameters
                firmaId = request.getParameter("firmaId");
                mainEntityId = request.getParameter("kursId");

                // now persist:
                persistenceManager.saveAddingCustomerToCourseToDatabase(firmaId, mainEntityId);

                // use helper to get lektor list prepared in our request
                request = courseHelper.prepareEntityView(request, mainEntityId, kursFacade, stawkaFirmyFacade);
                
                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE CUSTOMER
            case "/usunFirmeZKursu":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteCustomerCourseFromDatabase(mainEntityId);

                kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
                request.setAttribute("kurs", kurs);
                userPath = "/course/course/viewOne";
                break;
                
//  ADD LECTOR
            case "/dodajLektoraDoKursu":

                mainEntityId = request.getParameter("kursId"); // it should be set if we are here

                lectorListHelper = new LectorListHelper(); //  we need a helper

                // get the necessary lists for the request
                lektorList = lektorFacade.findAll();
                jezykList = jezykFacade.findAll();
                jezykLektoraList = jezykLektoraFacade.findAll();

                // use helper to get lektor list prepared in our request
                request = lectorListHelper.prepareEntityList(request, lektorList, jezykList, jezykLektoraList);

                // tell the kurs id
                request.setAttribute("kursId", mainEntityId);

                // and tell the container where to redirect
                userPath = "/course/course/addLector";
                break;

// SAVE LECTOR
            case "/zapiszDodanieLektoraDoKursu":
                // it is confirmed so add to database

                // check parameters
                lektorId = request.getParameter("lektorId");
                mainEntityId = request.getParameter("kursId");

                // now persist:
                persistenceManager.saveAddingLectorToCourseToDatabase(lektorId, mainEntityId);

                // use helper to get lektor list prepared in our request
                request = courseHelper.prepareEntityView(request, mainEntityId, kursFacade, stawkaFirmyFacade);
                
                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE LECTOR
            case "/usunLektoraZKursu":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteLectorFromCourseFromDatabase(mainEntityId);

                kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
                request.setAttribute("kurs", kurs);
                userPath = "/course/course/viewOne";
                break;

//  ADD PROGRAMME
            case "/dodajProgramDoKursu":
                
                 // entity list
                programList = programFacade.findAll();
                
                // use helper to get lektor list prepared in our request
                programmeListHelper = new ProgrammeListHelper(); //  we need a helper
                request = programmeListHelper.prepareEntityList(request, programList);

                // tell the kurs id
                mainEntityId = request.getParameter("kursId"); // it should be set if we are here
                request.setAttribute("kursId", mainEntityId);
                
                userPath = "/course/course/addProgramme";
                break;

// SAVE PROGRAMME
            case "/zapiszDodanieProgramuDoKursu":
                // it is confirmed so add to database

                // check parameters
                programId = request.getParameter("programId");
                mainEntityId = request.getParameter("kursId");

                // now persist:
                persistenceManager.saveAddingProgrammeToCourseToDatabase(programId, mainEntityId);

                // use helper to get lektor list prepared in our request
                request = courseHelper.prepareEntityView(request, mainEntityId, kursFacade, stawkaFirmyFacade);
                
                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE PROGRAMME
            case "/usunProgramZKursu":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteProgrammeCourseFromDatabase(mainEntityId);

                kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
                request.setAttribute("kurs", kurs);
                userPath = "/course/course/viewOne";
                break;
                
// REMOVE RATE (Lector)
            case "/usunStawkeLektora":

                // check parameters
                mainEntityId = request.getParameter("id");
                lektorId = request.getParameter("lektorId");

                // now persist:
                persistenceManager.deleteLectorRateFromDatabase(Integer.parseInt(mainEntityId), Integer.parseInt(lektorId));

                // set attributes
                kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
                request.setAttribute("kurs", kurs);
                
                // and tell the container where to redirect
                userPath = "/course/course/viewOne";
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
            
//  ADD RATE (lector)
            case "/dodajStawkeLektora":

                // get Parameters
                mainEntityId = request.getParameter("id"); // it should be set if we are here
                lektorId = request.getParameter("lektorId");
                stawka = request.getParameter("stawka");
                
                if ((bigDecimalAmount = FormValidator.validateMoney(stawka)) == null) {
                    request.setAttribute("stawkaNativeError", "błędne dane");
                } else {
                    persistenceManager.saveLectorRateToDatabase(Integer.parseInt(mainEntityId), Integer.parseInt(lektorId), bigDecimalAmount);
                }
                
                // set attributes
                kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
                request.setAttribute("kurs", kurs);
                
                // and tell the container where to redirect
                userPath = "/course/course/viewOne";
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
}
