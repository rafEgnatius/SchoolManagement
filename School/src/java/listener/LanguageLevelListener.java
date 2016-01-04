package listener;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Web application lifecycle listener.
 *
 * @author Rafa
 */
public class LanguageLevelListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        ServletContext sc = sce.getServletContext();
        String ll = sc.getInitParameter("languageLevels");
        List<String> languageLevels = new ArrayList<>();
        
        for (int i=0; i<ll.length(); i+=2) {
            languageLevels.add(ll.substring(i, i+2));
        }
        sc.setAttribute("languageLevels", languageLevels);
    }
    

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
