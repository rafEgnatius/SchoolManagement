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
public class StawkaLektoraPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "kurs_id")
    private int kursId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lektor_id")
    private int lektorId;

    public StawkaLektoraPK() {
    }

    public StawkaLektoraPK(int kursId, int lektorId) {
        this.kursId = kursId;
        this.lektorId = lektorId;
    }

    public int getKursId() {
        return kursId;
    }

    public void setKursId(int kursId) {
        this.kursId = kursId;
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
        hash += (int) kursId;
        hash += (int) lektorId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StawkaLektoraPK)) {
            return false;
        }
        StawkaLektoraPK other = (StawkaLektoraPK) object;
        if (this.kursId != other.kursId) {
            return false;
        }
        if (this.lektorId != other.lektorId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StawkaLektoraPK[ kursId=" + kursId + ", lektorId=" + lektorId + " ]";
    }
    
}
