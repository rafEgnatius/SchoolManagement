/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.course;

import entity.Firma;
import entity.Kursant;
import helper.CourseHelper;
import helper.CustomerListHelper;
import helper.LectorListHelper;
import helper.ParticipantHelper;
import helper.ProgrammeListHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
import session.KursantFacade;
import session.LektorFacade;
import session.ProgramFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "CourseAdditionController",
        loadOnStartup = 1,
        urlPatterns = {"/dodajFirmeDoKursu",
            "/dodajKursantaDoKursu",
            "/dodajLektoraDoKursu",
            "/dodajProgramDoKursu",
            "/dodajStawkeLektora",
            "/dodajTermin",
            "/usunFirmeZKursu",
            "/usunKursantaZKursu",
            "/usunLektoraZKursu",
            "/usunProgramZKursu",
            "/usunStawkeLektora",
            "/usunTermin",
            "/zapiszDodanieFirmyDoKursu",
            "/zapiszDodanieKursantaDoKursu",
            "/zapiszDodanieLektoraDoKursu",
            "/zapiszDodanieProgramuDoKursu"})
public class CourseAdditionController extends HttpServlet {

    @EJB
    private FirmaFacade firmaFacade;
    private List firmaList = new ArrayList();

    @EJB
    private JezykFacade jezykFacade;
    private List jezykList = new ArrayList();

    @EJB
    private KursFacade kursFacade;

    @EJB
    private KursantFacade kursantFacade;
    private List kursantList = new ArrayList();

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
    private CourseHelper mainEntityHelper;

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

    String kursantId; // to be moved

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
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE CUSTOMER
            case "/usunFirmeZKursu":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteCustomerFromCourseFromDatabase(mainEntityId);

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
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
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE LECTOR
            case "/usunLektoraZKursu":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteLectorFromCourseFromDatabase(mainEntityId);

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

//  ADD PARTICIPANT
            case "/dodajKursantaDoKursu":

                // check the id
                mainEntityId = request.getParameter("kursId"); // it should be set if we are here

                // we will need this one at least twice
                Firma firma = kursFacade.find(Integer.parseInt(mainEntityId)).getFirma();

                // entity list
                kursantList = new ArrayList();

                // filter only those with proper participants - meaning only those with matching customer (firma)
                Iterator myIterator = kursantFacade.findAll().iterator();
                while (myIterator.hasNext()) {
                    Kursant kursant = (Kursant) myIterator.next();
                    if (kursant.getFirma().equals(firma)) {
                        kursantList.add(kursant);
                    }
                }

                // use helper to get lektor list prepared in our request
                ParticipantHelper participantListHelper = new ParticipantHelper(); //  we need a helper
                List onlyOneFirma = new ArrayList();
                onlyOneFirma.add(firma); // get the firma we need
                request = participantListHelper.prepareEntityList(request, kursantList, onlyOneFirma); // only one customer (firma)

                request.setAttribute("kursId", mainEntityId);

                userPath = "/course/course/addParticipant";
                break;

// SAVE PARTICIPANT
            case "/zapiszDodanieKursantaDoKursu":
                // it is confirmed so add to database
                // check parameters
                kursantId = request.getParameter("kursantId");
                mainEntityId = request.getParameter("kursId");

                // check if not already have this participant
                if (!mainEntityHelper.alreadyThere(Integer.parseInt(mainEntityId), Integer.parseInt(kursantId))) {
                    // persist if not:
                    persistenceManager.saveAddingParticipantToCourseToDatabase(kursFacade.find(Integer.parseInt(mainEntityId)), kursantFacade.find(Integer.parseInt(kursantId)));
                }

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE PARTICIPANT
            case "/usunKursantaZKursu":

                // check parameters
                mainEntityId = request.getParameter("kursId");
                kursantId = request.getParameter("kursantId");

                // now persist:
                persistenceManager.deleteParticipantFromDatabase(mainEntityId, kursantId);

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
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
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE PROGRAMME
            case "/usunProgramZKursu":

                // check parameters
                mainEntityId = request.getQueryString();

                // now persist:
                persistenceManager.deleteProgrammeCourseFromDatabase(mainEntityId);

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE RATE (Lector)
            case "/usunStawkeLektora":

                // check parameters
                mainEntityId = request.getParameter("id");
                lektorId = request.getParameter("lektorId");

                // now persist:
                persistenceManager.deleteLectorRateFromDatabase(Integer.parseInt(mainEntityId), Integer.parseInt(lektorId));

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

// REMOVE DAY AND TIME
            case "/usunTermin":

                // check parameters
                String terminId = request.getQueryString();

                // now persist:
                persistenceManager.deleteDayAndTimeFromCourseFromDatabase(terminId);

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
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

// ADD DAY AND TIME
            case "/dodajTermin":
                // it is confirmed so add to database

                // check parameters
                mainEntityId = request.getParameter("id");
                String stringGodzinaStart = request.getParameter("godzinaStart");
                String stringGodzinaStop = request.getParameter("godzinaStop");

                // validate
                boolean formError = false;
                Calendar godzinaStart;
                Calendar godzinaStop;

                if ((godzinaStart = FormValidator.validateTime(stringGodzinaStart)) == null) {
                    request.setAttribute("godzinaStartError", "błędne dane");
                    formError = true;
                } else {
                    request.setAttribute("godzinaStart", stringGodzinaStart);
                }
                if ((godzinaStop = FormValidator.validateTime(stringGodzinaStop)) == null) {
                    request.setAttribute("godzinaStopError", "błędne dane");
                    formError = true;
                } else {
                    request.setAttribute("godzinaStop", stringGodzinaStop);
                }

                if (!formError) {
                    // now persist:
                    persistenceManager.saveAddingDayAndTimeToCourseToDatabase(mainEntityId, request.getParameter("dzien"), godzinaStart, godzinaStop);
                    request.removeAttribute("godzinaStart");
                    request.removeAttribute("godzinaStop");
                }

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
                userPath = "/course/course/viewOne";
                break;

//  ADD RATE (lector)
            case "/dodajStawkeLektora":

                // get Parameters
                mainEntityId = request.getParameter("id"); // it should be set if we are here
                lektorId = request.getParameter("lektorId");
                stawka = request.getParameter("stawka");

                if ((bigDecimalAmount = FormValidator.validateMoney(stawka)) == null) {
                    request.setAttribute("stawkaError", "błędne dane");
                } else {
                    persistenceManager.saveLectorRateToDatabase(Integer.parseInt(mainEntityId), Integer.parseInt(lektorId), bigDecimalAmount);
                }

                // use helper to get lektor list prepared in our request
                request = mainEntityHelper.prepareEntityView(request, mainEntityId);

                // prepare redirect
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
