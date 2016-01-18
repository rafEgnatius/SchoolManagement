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
public class SetUpListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // prepare thing to set up
        ServletContext sc = sce.getServletContext();

        // now language levels
        String ll = sc.getInitParameter("languageLevels");
        List<String> languageLevels = new ArrayList<>();

        for (int i = 0; i < ll.length(); i += 2) {
            languageLevels.add(ll.substring(i, i + 2));
        }
        sc.setAttribute("languageLevels", languageLevels);

        // and questionnaire questions set up
        String[] listOfQuestions = {
            "Pytanie 1",
            "Pytanie 2",
            "Pytanie 3",
            "Pytanie 4",
            "Pytanie 5",
            "Pytanie 6",
            "Pytanie 7",
            "Pytanie 8",
            "Pytanie 9",
            "Pytanie 10",
            "Pytanie 11",
            "Pytanie 12",
            "Pytanie 13",
            "Pytanie 14",
            "Pytanie 15",
            "Pytanie 16",
            "Pytanie 17",
            "Pytanie 18",
            "Pytanie 19",
            "Pytanie 20",
            "Pytanie 21",
            "Pytanie 22"
        };
        sc.setAttribute("listOfQuestions", listOfQuestions);
        sc.setAttribute("finalQuestion", "Pytanie opisowe");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
