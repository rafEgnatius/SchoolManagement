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
public class JezykKursantaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "kursant_id")
    private int kursantId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "jezyk_id")
    private int jezykId;

    public JezykKursantaPK() {
    }

    public JezykKursantaPK(int kursantId, int jezykId) {
        this.kursantId = kursantId;
        this.jezykId = jezykId;
    }

    public int getKursantId() {
        return kursantId;
    }

    public void setKursantId(int kursantId) {
        this.kursantId = kursantId;
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
        hash += (int) kursantId;
        hash += (int) jezykId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JezykKursantaPK)) {
            return false;
        }
        JezykKursantaPK other = (JezykKursantaPK) object;
        if (this.kursantId != other.kursantId) {
            return false;
        }
        if (this.jezykId != other.jezykId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.JezykKursantaPK[ kursantId=" + kursantId + ", jezykId=" + jezykId + " ]";
    }
    
}
