/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Termin;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafa
 */
@Stateless
public class TerminFacade extends AbstractFacade<Termin> {
    @PersistenceContext(unitName = "Szkola004PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TerminFacade() {
        super(Termin.class);
    }
    
}
