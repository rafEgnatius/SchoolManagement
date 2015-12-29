/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
@Table(name = "obecnosc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Obecnosc.findAll", query = "SELECT o FROM Obecnosc o"),
    @NamedQuery(name = "Obecnosc.findByLekcjaId", query = "SELECT o FROM Obecnosc o WHERE o.obecnoscPK.lekcjaId = :lekcjaId"),
    @NamedQuery(name = "Obecnosc.findByKursantId", query = "SELECT o FROM Obecnosc o WHERE o.obecnoscPK.kursantId = :kursantId"),
    @NamedQuery(name = "Obecnosc.findByObecny", query = "SELECT o FROM Obecnosc o WHERE o.obecny = :obecny")})
public class Obecnosc implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ObecnoscPK obecnoscPK;
    @Column(name = "obecny")
    private Boolean obecny;
    @JoinColumn(name = "kursant_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kursant kursant;
    @JoinColumn(name = "lekcja_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lekcja lekcja;

    public Obecnosc() {
    }

    public Obecnosc(ObecnoscPK obecnoscPK) {
        this.obecnoscPK = obecnoscPK;
    }

    public Obecnosc(int lekcjaId, int kursantId) {
        this.obecnoscPK = new ObecnoscPK(lekcjaId, kursantId);
    }

    public ObecnoscPK getObecnoscPK() {
        return obecnoscPK;
    }

    public void setObecnoscPK(ObecnoscPK obecnoscPK) {
        this.obecnoscPK = obecnoscPK;
    }

    public Boolean getObecny() {
        return obecny;
    }

    public void setObecny(Boolean obecny) {
        this.obecny = obecny;
    }

    public Kursant getKursant() {
        return kursant;
    }

    public void setKursant(Kursant kursant) {
        this.kursant = kursant;
    }

    public Lekcja getLekcja() {
        return lekcja;
    }

    public void setLekcja(Lekcja lekcja) {
        this.lekcja = lekcja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (obecnoscPK != null ? obecnoscPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obecnosc)) {
            return false;
        }
        Obecnosc other = (Obecnosc) object;
        if ((this.obecnoscPK == null && other.obecnoscPK != null) || (this.obecnoscPK != null && !this.obecnoscPK.equals(other.obecnoscPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Obecnosc[ obecnoscPK=" + obecnoscPK + " ]";
    }
    
}
