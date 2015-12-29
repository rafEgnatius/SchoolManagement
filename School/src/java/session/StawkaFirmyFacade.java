/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.StawkaFirmy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafa
 */
@Stateless
public class StawkaFirmyFacade extends AbstractFacade<StawkaFirmy> {
    @PersistenceContext(unitName = "SchoolPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StawkaFirmyFacade() {
        super(StawkaFirmy.class);
    }
    
}
