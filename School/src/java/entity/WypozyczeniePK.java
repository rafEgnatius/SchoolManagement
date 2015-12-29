/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafa
 */
@Embeddable
public class WypozyczeniePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "podrecznik_id")
    private int podrecznikId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lektor_id")
    private int lektorId;

    public WypozyczeniePK() {
    }

    public WypozyczeniePK(int podrecznikId, int lektorId) {
        this.podrecznikId = podrecznikId;
        this.lektorId = lektorId;
    }

    public int getPodrecznikId() {
        return podrecznikId;
    }

    public void setPodrecznikId(int podrecznikId) {
        this.podrecznikId = podrecznikId;
    }

    public int getLektorId() {
        return lektorId;
    }

    public void setLektorId(int lektorId) {
        this.lektorId = lektorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) podrecznikId;
        hash += (int) lektorId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WypozyczeniePK)) {
            return false;
        }
        WypozyczeniePK other = (WypozyczeniePK) object;
        if (this.podrecznikId != other.podrecznikId) {
            return false;
        }
        if (this.lektorId != other.lektorId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WypozyczeniePK[ podrecznikId=" + podrecznikId + ", lektorId=" + lektorId + " ]";
    }
    
}
