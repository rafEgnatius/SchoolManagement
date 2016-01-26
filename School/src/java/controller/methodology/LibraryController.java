/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.methodology;

import entity.Lektor;
import entity.Podrecznik;
import helper.LectorHelper;
import helper.TextbookHelper;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import session.LektorFacade;
import session.PodrecznikFacade;
import session.persistence.PersistenceManager;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "TextbookLibraryController",
        loadOnStartup = 1,
        urlPatterns = {"/dodajLektoraDoWypozyczenia",
            "/wyszukajLektoraDoWypozyczenia", // POST
            "/dodajPodrecznikDoWypozyczenia",
            "/wyszukajPodrecznikDoWypozyczenia", // POST
            "/potwierdzWypozyczeniePodrecznika",
            "/wypozyczPodrecznikZBiblioteki",
            "/oddajPodrecznikDoBiblioteki"})
public class LibraryController extends HttpServlet {

    @EJB
    private LektorFacade lektorFacade;

    @EJB
    private PodrecznikFacade podrecznikFacade;

    @EJB
    LectorHelper lectorHelper;

    @EJB
    TextbookHelper textbookHelper;

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

        Lektor lektor;
        Podrecznik podrecznik;
        int intLektorId;
        String lektorId;
        int intPodrecznikId;
        String podrecznikId;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
//  ADD LECTOR
            case "/dodajLektoraDoWypozyczenia":

                // it is like in entities list - it's just simpler
                request = lectorHelper.prepareEntityList(request);

                request.setAttribute("podrecznikId", request.getParameter("podrecznikId")); // to display
                userPath = "/methodology/library/borrow";
                break;

//  ADD TEXTBOOK
            case "/dodajPodrecznikDoWypozyczenia":

                // it is like in entities list - it's just simpler
                request = textbookHelper.prepareEntityList(request);

                request.setAttribute("lektorId", request.getParameter("lektorId")); // to display
                userPath = "/methodology/library/borrow";
                break;

// CONFIRM
            case "/potwierdzWypozyczeniePodrecznika":

                // check parameters
                lektorId = request.getParameter("lektorId");
                podrecznikId = request.getParameter("podrecznikId");

                // find entities
                try {
                    intLektorId = Integer.parseInt(lektorId);
                    intPodrecznikId = Integer.parseInt(podrecznikId);
                } catch (NumberFormatException e) {
                    userPath = "/methodology/textbook/viewAll";
                    break;
                }
                lektor = lektorFacade.find(intLektorId);
                podrecznik = podrecznikFacade.find(intPodrecznikId);

                // prepare request
                request.setAttribute("lektor", lektor);
                request.setAttribute("podrecznik", podrecznik);
                userPath = "/methodology/library/confirm";

                // forward it to confirmation
                break;

// SAVE
            case "/wypozyczPodrecznikZBiblioteki":
                // it is confirmed so add to database

                // check parameters
                lektorId = request.getParameter("lektorId");
                podrecznikId = request.getParameter("podrecznikId");

                lektor = lektorFacade.find(Integer.parseInt(lektorId)); // we should try/catch it later
                podrecznik = podrecznikFacade.find(Integer.parseInt(podrecznikId)); // we should try/catch it later

                // now persist:
                persistenceManager.saveBorrowingToDatabase(lektor, podrecznik);

                request.setAttribute("lektorId", lektorId);
                request.setAttribute("podrecznikId", podrecznikId);
                userPath = "/methodology/confirmation"; // and show once more - now with another language
                break;

// REMOVE
            case "/oddajPodrecznikDoBiblioteki":

                // check parameters
                lektorId = request.getParameter("lektorId");
                podrecznikId = request.getParameter("podrecznikId");

                // now persist:
                persistenceManager.deleteBorrowingFromDatabase(Integer.parseInt(lektorId), Integer.parseInt(podrecznikId));
                request.setAttribute("lektorId", lektorId);
                request.setAttribute("podrecznikId", podrecznikId);

                userPath = "/methodology/confirmation"; // and show once more - now with another language
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
//  SEARCH FOR LECTOR
            case "/wyszukajLektoraDoWypozyczenia":

                // it is like in entities list - it's just simpler
                request = lectorHelper.prepareEntityList(request);

                request.setAttribute("podrecznikId", request.getParameter("podrecznikId")); // to display
                userPath = "/methodology/library/borrow";
                break;

//  SEARCH FOR TEXTBOOK
            case "/wyszukajPodrecznikDoWypozyczenia":

                // it is like in entities list - it's just simpler
                request = textbookHelper.prepareEntityList(request);

                request.setAttribute("lektorId", request.getParameter("lektorId")); // to display
                userPath = "/methodology/library/borrow";
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
