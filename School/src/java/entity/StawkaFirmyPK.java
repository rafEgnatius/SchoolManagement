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
public class StawkaFirmyPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "firma_id")
    private int firmaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "natywny")
    private boolean natywny;

    public StawkaFirmyPK() {
    }

    public StawkaFirmyPK(int firmaId, boolean natywny) {
        this.firmaId = firmaId;
        this.natywny = natywny;
    }

    public int getFirmaId() {
        return firmaId;
    }

    public void setFirmaId(int firmaId) {
        this.firmaId = firmaId;
    }

    public boolean getNatywny() {
        return natywny;
    }

    public void setNatywny(boolean natywny) {
        this.natywny = natywny;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) firmaId;
        hash += (natywny ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StawkaFirmyPK)) {
            return false;
        }
        StawkaFirmyPK other = (StawkaFirmyPK) object;
        if (this.firmaId != other.firmaId) {
            return false;
        }
        if (this.natywny != other.natywny) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StawkaFirmyPK[ firmaId=" + firmaId + ", natywny=" + natywny + " ]";
    }
    
}
