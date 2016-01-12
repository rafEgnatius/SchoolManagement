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
public class KursKursantaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "kurs_id")
    private int kursId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kursant_id")
    private int kursantId;

    public KursKursantaPK() {
    }

    public KursKursantaPK(int kursId, int kursantId) {
        this.kursId = kursId;
        this.kursantId = kursantId;
    }

    public int getKursId() {
        return kursId;
    }

    public void setKursId(int kursId) {
        this.kursId = kursId;
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
        hash += (int) kursId;
        hash += (int) kursantId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KursKursantaPK)) {
            return false;
        }
        KursKursantaPK other = (KursKursantaPK) object;
        if (this.kursId != other.kursId) {
            return false;
        }
        if (this.kursantId != other.kursantId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KursKursantaPK[ kursId=" + kursId + ", kursantId=" + kursantId + " ]";
    }
    
}
