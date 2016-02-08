/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rafa
 */
@WebServlet(name = "MainController",
        loadOnStartup = 1,
        urlPatterns = {"/metodyka",
            "/kurs",
            "/organizacja",
            "/wyloguj"})
public class MainController extends HttpServlet {

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

        request.setCharacterEncoding("UTF-8"); // for Polish characters
        HttpSession session = request.getSession(true);
        String userPath = request.getServletPath();

        switch (userPath) {
            case "/kurs":
                userPath = "/WEB-INF/view/course/index";
                break;
            case "/metodyka":
                userPath = "/WEB-INF/view/methodology/index";
                break;
            case "/organizacja":
                userPath = "/WEB-INF/view/methodology/index";
                break;
            case "/wyloguj":
                session = request.getSession();
                session.invalidate();   // terminate session
                response.sendRedirect("/School/");
                return;
        }

// FORWARD
        String url = userPath + ".jsp";

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

        request.setCharacterEncoding("UTF-8"); // for Polish characters

    }

}
