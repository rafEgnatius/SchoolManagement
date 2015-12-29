/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Wplata;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafa
 */
@Stateless
public class WplataFacade extends AbstractFacade<Wplata> {
    @PersistenceContext(unitName = "Szkola004PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WplataFacade() {
        super(Wplata.class);
    }
    
}
