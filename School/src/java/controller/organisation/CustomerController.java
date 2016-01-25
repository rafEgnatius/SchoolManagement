/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.organisation;

import entity.Firma;
import helper.CustomerHelper;
import java.io.IOException;
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
@WebServlet(name = "CustomerController",
        loadOnStartup = 1,
        urlPatterns = {"/firmy",
            "/pokazFirme",
            "/dodajFirme",
            "/dodajFirmePotwierdz",
            "/dodajFirmeZapisz",
            "/edytujFirme"})
public class CustomerController extends HttpServlet {

    @EJB
    FirmaFacade firmaFacade;

    @EJB
    StawkaFirmyFacade stawkaFirmyFacade;

    @EJB
    PersistenceManager persistenceManager;

    @EJB
    CustomerHelper customerHelper;

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

        Firma firma;

        int intFirmaId;
        String firmaId;

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {
// VIEW ALL
            case "/firmy":

                // sorting and pagination works this way:
                // if there is no sortBy it means we are here for the first time
                // so we check if we have pageNumber
                // if not it means that we are really for the first time here
                // let's get initial data...
                // ... like page number
                // use helper to get lektor list prepared in our request
                request = customerHelper.prepareEntityList(request);

                userPath = "/organisation/customer/viewAll";
                break;
// ADD             
            case "/dodajFirme":
                // just ask for a form

                userPath = "/organisation/customer/form";
                break;

// EDIT             
            case "/edytujFirme":
                // get id from request
                firmaId = request.getQueryString();

                // cast it to the integer
                try {
                    intFirmaId = Integer.parseInt(firmaId);
                } catch (NumberFormatException e) {
                    intFirmaId = 0; // (it seems that we have some kind of a problem)
                }

                if (intFirmaId > 0) {
                    // find the lektor entity
                    firma = firmaFacade.find(intFirmaId);
                    // set as a request attribute all the fields
                    // and this is so because of the form validation
                    // when we give the form values that are correct
                    request.setAttribute("id", firma.getId());
                    request.setAttribute("nip", firma.getNip());
                    request.setAttribute("nazwa", firma.getNazwa());
                    request.setAttribute("symbol", firma.getSymbol());
                    request.setAttribute("miasto", firma.getMiasto());
                    request.setAttribute("osoba", firma.getOsoba());
                    request.setAttribute("telefon", firma.getTelefon());
                    request.setAttribute("komorka", firma.getKomorka());
                    request.setAttribute("email", firma.getEmail());
                    request.setAttribute("adres", firma.getAdres());

                }
                // then ask for a form 
                userPath = "/organisation/customer/form";

                break;

// SAVE
            case "/dodajFirmeZapisz":
                // it is confirmed (in POST) so add to database

                String id = request.getParameter("id");
                String nip = request.getParameter("nip");
                String nazwa = request.getParameter("nazwa");
                String symbol = request.getParameter("symbol");
                String miasto = request.getParameter("miasto");
                String osoba = request.getParameter("osoba");
                String telefon = request.getParameter("telefon");
                String komorka = request.getParameter("komorka");
                String email = request.getParameter("email");
                String adres = request.getParameter("adres");

                intFirmaId = persistenceManager.saveCustomerToDatabase(id, nip, nazwa, symbol, miasto, osoba, telefon, komorka, email, adres);

                // prepare request for one customer
                request = customerHelper.prepareEntityView(request, intFirmaId+"");
                
                userPath = "/organisation/customer/viewOne";
                break;

// VIEW ONE
            case "/pokazFirme":
                // first get lektor id from request
                // then prepare another lists that we will need
                // meaning: entity etc.

                request = customerHelper.prepareEntityView(request, request.getQueryString());

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

        //HttpSession session = request.getSession(); // let's get session - we might need it
        request.setCharacterEncoding("UTF-8"); // for Polish characters
        String userPath = request.getServletPath(); // this way we know where to go

        switch (userPath) {

// CONFIRM
            case "/dodajFirmePotwierdz":

                // check with id whether editing or creating
                String id = request.getParameter("id");
                if (id == null || id.equals("")) {
                    id = "-1"; // meaning this is the act of creation
                }
                // validate
                String nip = request.getParameter("nip");
                String nazwa = request.getParameter("nazwa");
                String symbol = request.getParameter("symbol");
                String miasto = request.getParameter("miasto");
                String osoba = request.getParameter("osoba");
                String telefon = request.getParameter("telefon");
                String komorka = request.getParameter("komorka");
                String email = request.getParameter("email");
                String adres = request.getParameter("adres");

                boolean formError = false;

                if (!FormValidator.validateString(nazwa)) {
                    request.setAttribute("nazwaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(symbol)) {
                    request.setAttribute("symbolError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(miasto)) {
                    request.setAttribute("miastoError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(osoba)) {
                    request.setAttribute("osobaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(telefon)) {
                    request.setAttribute("telefonError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(komorka)) {
                    request.setAttribute("komorkaError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(email)) {
                    request.setAttribute("emailError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(adres)) {
                    request.setAttribute("adresError", "*");
                    formError = true;
                }
                if (!FormValidator.validateString(nip)) {
                    request.setAttribute("nipError", "*");
                    formError = true;
                }

                if (formError) {
                    request.setAttribute("formError", "formError");

                    request.setAttribute("id", id);
                    request.setAttribute("nazwa", nazwa);
                    request.setAttribute("symbol", symbol);
                    request.setAttribute("miasto", miasto);
                    request.setAttribute("osoba", osoba);
                    request.setAttribute("telefon", telefon);
                    request.setAttribute("komorka", komorka);
                    request.setAttribute("email", email);
                    request.setAttribute("adres", adres);
                    request.setAttribute("nip", nip);

                    userPath = "/organisation/customer/form";
                } else {
                    request.setAttribute("id", id);
                    userPath = "/organisation/customer/confirm";
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
}
