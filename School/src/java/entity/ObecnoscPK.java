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
public class ObecnoscPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "lekcja_id")
    private int lekcjaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kursant_id")
    private int kursantId;

    public ObecnoscPK() {
    }

    public ObecnoscPK(int lekcjaId, int kursantId) {
        this.lekcjaId = lekcjaId;
        this.kursantId = kursantId;
    }

    public int getLekcjaId() {
        return lekcjaId;
    }

    public void setLekcjaId(int lekcjaId) {
        this.lekcjaId = lekcjaId;
    }

    public int getKursantId() {
        return kursantId;
    }

    public void setKursantId(int kursantId) {
        this.kursantId = kursantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) lekcjaId;
        hash += (int) kursantId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObecnoscPK)) {
            return false;
        }
        ObecnoscPK other = (ObecnoscPK) object;
        if (this.lekcjaId != other.lekcjaId) {
            return false;
        }
        if (this.kursantId != other.kursantId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ObecnoscPK[ lekcjaId=" + lekcjaId + ", kursantId=" + kursantId + " ]";
    }
    
}
