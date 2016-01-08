/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Firma;
import entity.Kurs;
import entity.KursKursanta;
import entity.Kursant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import session.KursFacade;
import session.KursKursantaFacade;
import session.KursantFacade;
import session.StawkaFirmyFacade;
import session.StawkaLektoraFacade;

/**
 *
 * @author Rafa
 */
@Stateless
public class CourseHelper {

    @EJB
    private KursFacade kursFacade;
    
    @EJB
    private KursantFacade kursantFacade;

    @EJB
    private KursKursantaFacade kursKursantaFacade;

    @EJB
    private StawkaFirmyFacade stawkaFirmyFacade;

    @EJB
    private StawkaLektoraFacade stawkaLektoraFacade;

    
    public boolean alreadyThere(int kursId, int kursantId) {
        
        List kursKursantaList = kursKursantaFacade.findAll();
        Kurs kurs = kursFacade.find(kursId);
        Kursant kursant = kursantFacade.find(kursantId);
        
        Iterator it = kursKursantaList.iterator();
        while(it.hasNext()) {
            KursKursanta kursKursanta = (KursKursanta) it.next();
            if (kursKursanta.getKurs().equals(kurs) && kursKursanta.getKursant().equals(kursant)) {
                return true;
            }
        }
        return false;
    }
    
    public HttpServletRequest prepareEntityView(HttpServletRequest request, String mainEntityId) {

        // set attributes
        Kurs kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
        request.setAttribute("kurs", kurs);

        Firma firma = kurs.getFirma();
        request.setAttribute("firma", firma);

        // if firma is null there is no point...
        if (firma != null) {
            // first find kursKursantaList with only those records that match specific course
            List kursantList = new ArrayList();
            Iterator myIterator = kursKursantaFacade.findAll().iterator();
            while (myIterator.hasNext()) {
                KursKursanta kursKursanta = (KursKursanta) myIterator.next();
                if (kursKursanta.getKurs().equals(kurs)) {
                    // than add participants that are on this short list
                    kursantList.add(kursKursanta.getKursant());
                }
            }
            // and set the final list as an attribute
            request.setAttribute("kursantList", kursantList);
        }

        // and set another attributes
        List stawkaFirmyList = stawkaFirmyFacade.findAll();
        request.setAttribute("stawkaFirmyList", stawkaFirmyList);

        List stawkaLektoraList = stawkaLektoraFacade.findAll();
        request.setAttribute("stawkaLektoraList", stawkaLektoraList);

        return request;
    }

    

}
