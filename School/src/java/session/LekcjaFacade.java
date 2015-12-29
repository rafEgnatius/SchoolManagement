/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Lekcja;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafa
 */
@Stateless
public class LekcjaFacade extends AbstractFacade<Lekcja> {
    @PersistenceContext(unitName = "SchoolPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LekcjaFacade() {
        super(Lekcja.class);
    }
    
}
