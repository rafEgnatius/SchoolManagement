/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "stawka_lektora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StawkaLektora.findAll", query = "SELECT s FROM StawkaLektora s"),
    @NamedQuery(name = "StawkaLektora.findByKursId", query = "SELECT s FROM StawkaLektora s WHERE s.stawkaLektoraPK.kursId = :kursId"),
    @NamedQuery(name = "StawkaLektora.findByLektorId", query = "SELECT s FROM StawkaLektora s WHERE s.stawkaLektoraPK.lektorId = :lektorId"),
    @NamedQuery(name = "StawkaLektora.findByStawka", query = "SELECT s FROM StawkaLektora s WHERE s.stawka = :stawka")})
public class StawkaLektora implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StawkaLektoraPK stawkaLektoraPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stawka")
    private BigDecimal stawka;
    @JoinColumn(name = "kurs_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kurs kurs;
    @JoinColumn(name = "lektor_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lektor lektor;

    public StawkaLektora() {
    }

    public StawkaLektora(StawkaLektoraPK stawkaLektoraPK) {
        this.stawkaLektoraPK = stawkaLektoraPK;
    }

    public StawkaLektora(int kursId, int lektorId) {
        this.stawkaLektoraPK = new StawkaLektoraPK(kursId, lektorId);
    }

    public StawkaLektoraPK getStawkaLektoraPK() {
        return stawkaLektoraPK;
    }

    public void setStawkaLektoraPK(StawkaLektoraPK stawkaLektoraPK) {
        this.stawkaLektoraPK = stawkaLektoraPK;
    }

    public BigDecimal getStawka() {
        return stawka;
    }

    public void setStawka(BigDecimal stawka) {
        this.stawka = stawka;
    }

    public Kurs getKurs() {
        return kurs;
    }

    public void setKurs(Kurs kurs) {
        this.kurs = kurs;
    }

    public Lektor getLektor() {
        return lektor;
    }

    public void setLektor(Lektor lektor) {
        this.lektor = lektor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stawkaLektoraPK != null ? stawkaLektoraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StawkaLektora)) {
            return false;
        }
        StawkaLektora other = (StawkaLektora) object;
        if ((this.stawkaLektoraPK == null && other.stawkaLektoraPK != null) || (this.stawkaLektoraPK != null && !this.stawkaLektoraPK.equals(other.stawkaLektoraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StawkaLektora[ stawkaLektoraPK=" + stawkaLektoraPK + " ]";
    }
    
}
