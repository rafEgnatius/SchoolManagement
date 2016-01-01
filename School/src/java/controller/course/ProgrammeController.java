package controller.course;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import entity.Program;
import helper.ProgrammeListHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.ProgramFacade;
import session.persistence.PersistenceManager;
import validator.FormValidator;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "ProgrammeController",
        loadOnStartup = 1,
        urlPatterns = {"/programy",
            "/pokazProgram",
            "/dodajProgram",
            "/dodajProgramPotwierdz",
            "/dodajProgramZapisz",
            "/edytujProgram"})
public class ProgrammeController extends HttpServlet {

    @EJB
    private ProgramFacade programFacade;
    private Program program;
    private List programList = new ArrayList();
    ProgrammeListHelper programmeListHelper;

    @EJB
    private PersistenceManager persistenceManager;

//    program
    int intProgramId = 0;
    String programId = "";

//    GENERAL 
    private String userPath; // this one to see what to do
    private Boolean sortAsc = true; // and this one to check how to sort
    private String sortBy; // so we know how to sort
    private Boolean changeSort = false; // this one to know whether to change sorting order

//    pagination
    private static final int pageSize = 10; // number of records on one page
    private int numberOfPages; // auxiliary field for calculating number of pages (based on the list size)
    List<List> listOfPages = new ArrayList<List>(); // list of lists of single page records
    private int pageNumber; // current page number
    private List pageToDisplay; // which one should be displayed to user

//    search and filter
    private String searchPhrase; // entity search (TEXT)
    private String searchOption; // language selector (OPTION)

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
            case "/programy":

                // entity list
                programList = programFacade.findAll();
                
                // use helper to get lektor list prepared in our request
                programmeListHelper = new ProgrammeListHelper(); //  we need a helper
                request = programmeListHelper.prepareEntityList(request, programList);

                userPath = "/course/programme/viewAll";
                break;

// ADD             
            case "/dodajProgram":
                // just ask for a form

                userPath = "/course/programme/form";
                break;

// EDIT             
            case "/edytujProgram":
                // get lektorId from request
                programId = request.getQueryString();

                // cast it to the integer
                try {
                    intProgramId = Integer.parseInt(programId);
                } catch (NumberFormatException e) {
                    intProgramId = 0; // (it seems that we have some kind of a problem)
                }

                if (intProgramId > 0) {
                    // find the lektor entity
                    program = programFacade.find(intProgramId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("id", program.getId());
                    request.setAttribute("referencja", program.getReferencja());
                    request.setAttribute("metodaId", translateMethod(program.getMetoda())); // because we are checking by string representing number
                }
                // then ask for a form 
                userPath = "/course/programme/form";

                break;

// SAVE
            case "/dodajProgramZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String referencja = request.getParameter("referencja");
                String metoda = request.getParameter("metoda"); // we translate it from id representation

                intProgramId = persistenceManager.saveProgrammeToDatabase(id, referencja, metoda);

                request.setAttribute("program", programFacade.find(intProgramId));
                userPath = "/course/programme/viewOne";
                break;

// VIEW ONE
            case "/pokazProgram":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: jezyk, jezykLektora, wypozyczenia etc.

                String programId = request.getQueryString();
                program = programFacade.find(Integer.parseInt(programId));
                request.setAttribute("program", program);
                userPath = "/course/programme/viewOne";
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

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {

// CONFIRM
            case "/dodajProgramPotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String referencja = request.getParameter("referencja");
                String metoda = translateBackMethod(request.getParameter("metodaId")); // we translate it from id representation

                boolean formError = false;

                if (!FormValidator.validateString(referencja)) {
                    request.setAttribute("referencjaError", "*");
                    formError = true;
                }

                if (formError) {
                    request.setAttribute("formError", "formError");

                    request.setAttribute("id", id);
                    request.setAttribute("referencja", referencja);
                    request.setAttribute("metodaId", translateMethod(program.getMetoda())); // because we are checking by string representing number

                    userPath = "/course/programme/form";
                } else {
                    request.setAttribute("id", id);
                    request.setAttribute("metoda", metoda); // for confirmation just a string
                    userPath = "/course/programme/confirm";
                }

                // forward it to confirmation
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
    private HttpServletRequest prepareRequest(HttpServletRequest request, String podrecznikId) {

        // ANYTHING THAT WE WOULD NEED HERE
        return request;
    }

    private String translateMethod(String metoda) {

        switch (metoda) {
            case "audiolingwalna":
                return "1";
            case "komunikatywna":
                return "2";
            case "gramatyczno-tłumaczeniowa":
                return "3";
            case "inna":
                return "0";
        }
        return null;
    }

    private String translateBackMethod(String metodaId) {

        switch (metodaId) {
            case "1":
                return "audiolingwalna";
            case "2":
                return "komunikatywna";
            case "3":
                return "gramatyczno-tłumaczeniowa";
            case "0":
                return "inna";
        }
        return null;
    }

}
