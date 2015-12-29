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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "jezyk_kursanta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JezykKursanta.findAll", query = "SELECT j FROM JezykKursanta j"),
    @NamedQuery(name = "JezykKursanta.findByKursantId", query = "SELECT j FROM JezykKursanta j WHERE j.jezykKursantaPK.kursantId = :kursantId"),
    @NamedQuery(name = "JezykKursanta.findByJezykId", query = "SELECT j FROM JezykKursanta j WHERE j.jezykKursantaPK.jezykId = :jezykId"),
    @NamedQuery(name = "JezykKursanta.findByPoziom", query = "SELECT j FROM JezykKursanta j WHERE j.poziom = :poziom")})
public class JezykKursanta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JezykKursantaPK jezykKursantaPK;
    @Size(max = 5)
    @Column(name = "poziom")
    private String poziom;
    @JoinColumn(name = "jezyk_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jezyk jezyk;
    @JoinColumn(name = "kursant_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kursant kursant;

    public JezykKursanta() {
    }

    public JezykKursanta(JezykKursantaPK jezykKursantaPK) {
        this.jezykKursantaPK = jezykKursantaPK;
    }

    public JezykKursanta(int kursantId, int jezykId) {
        this.jezykKursantaPK = new JezykKursantaPK(kursantId, jezykId);
    }

    public JezykKursantaPK getJezykKursantaPK() {
        return jezykKursantaPK;
    }

    public void setJezykKursantaPK(JezykKursantaPK jezykKursantaPK) {
        this.jezykKursantaPK = jezykKursantaPK;
    }

    public String getPoziom() {
        return poziom;
    }

    public void setPoziom(String poziom) {
        this.poziom = poziom;
    }

    public Jezyk getJezyk() {
        return jezyk;
    }

    public void setJezyk(Jezyk jezyk) {
        this.jezyk = jezyk;
    }

    public Kursant getKursant() {
        return kursant;
    }

    public void setKursant(Kursant kursant) {
        this.kursant = kursant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jezykKursantaPK != null ? jezykKursantaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JezykKursanta)) {
            return false;
        }
        JezykKursanta other = (JezykKursanta) object;
        if ((this.jezykKursantaPK == null && other.jezykKursantaPK != null) || (this.jezykKursantaPK != null && !this.jezykKursantaPK.equals(other.jezykKursantaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.JezykKursanta[ jezykKursantaPK=" + jezykKursantaPK + " ]";
    }
    
}
