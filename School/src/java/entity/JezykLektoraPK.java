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
public class JezykLektoraPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "lektor_id")
    private int lektorId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "jezyk_id")
    private int jezykId;

    public JezykLektoraPK() {
    }

    public JezykLektoraPK(int lektorId, int jezykId) {
        this.lektorId = lektorId;
        this.jezykId = jezykId;
    }

    public int getLektorId() {
        return lektorId;
    }

    public void setLektorId(int lektorId) {
        this.lektorId = lektorId;
    }

    public int getJezykId() {
        return jezykId;
    }

    public void setJezykId(int jezykId) {
        this.jezykId = jezykId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) lektorId;
        hash += (int) jezykId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JezykLektoraPK)) {
            return false;
        }
        JezykLektoraPK other = (JezykLektoraPK) object;
        if (this.lektorId != other.lektorId) {
            return false;
        }
        if (this.jezykId != other.jezykId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.JezykLektoraPK[ lektorId=" + lektorId + ", jezykId=" + jezykId + " ]";
    }
    
}
