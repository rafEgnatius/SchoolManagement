/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import entity.Firma;
import entity.Kurs;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import session.KursFacade;
import session.StawkaFirmyFacade;
import session.StawkaLektoraFacade;

/**
 *
 * @author Rafa
 */
public class CourseHelper {

        public HttpServletRequest prepareEntityView(HttpServletRequest request, String mainEntityId,
                KursFacade kursFacade, StawkaFirmyFacade stawkaFirmyFacade, StawkaLektoraFacade stawkaLektoraFacade) {

        // set attributes
        Kurs kurs = kursFacade.find(Integer.parseInt(mainEntityId)); // we should try/catch it later
        request.setAttribute("kurs", kurs);

        Firma firma = kurs.getFirma();
        request.setAttribute("firma", firma);
        
        List stawkaFirmyList = stawkaFirmyFacade.findAll();
        request.setAttribute("stawkaFirmyList", stawkaFirmyList);
        
        List stawkaLektoraList = stawkaLektoraFacade.findAll();
        request.setAttribute("stawkaLektoraList", stawkaLektoraList);
        
        return request;
    }

}
