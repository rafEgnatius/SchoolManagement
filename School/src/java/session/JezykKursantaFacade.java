/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.JezykKursanta;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafa
 */
@Stateless
public class JezykKursantaFacade extends AbstractFacade<JezykKursanta> {
    @PersistenceContext(unitName = "SchoolPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JezykKursantaFacade() {
        super(JezykKursanta.class);
    }
    
}
