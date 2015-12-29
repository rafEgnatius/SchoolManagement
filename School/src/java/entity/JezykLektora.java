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
@Table(name = "jezyk_lektora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JezykLektora.findAll", query = "SELECT j FROM JezykLektora j"),
    @NamedQuery(name = "JezykLektora.findByLektorId", query = "SELECT j FROM JezykLektora j WHERE j.jezykLektoraPK.lektorId = :lektorId"),
    @NamedQuery(name = "JezykLektora.findByJezykId", query = "SELECT j FROM JezykLektora j WHERE j.jezykLektoraPK.jezykId = :jezykId"),
    @NamedQuery(name = "JezykLektora.findByNatywny", query = "SELECT j FROM JezykLektora j WHERE j.natywny = :natywny")})
public class JezykLektora implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JezykLektoraPK jezykLektoraPK;
    @Column(name = "natywny")
    private Boolean natywny;
    @JoinColumn(name = "jezyk_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jezyk jezyk;
    @JoinColumn(name = "lektor_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lektor lektor;

    public JezykLektora() {
    }

    public JezykLektora(JezykLektoraPK jezykLektoraPK) {
        this.jezykLektoraPK = jezykLektoraPK;
    }

    public JezykLektora(int lektorId, int jezykId) {
        this.jezykLektoraPK = new JezykLektoraPK(lektorId, jezykId);
    }

    public JezykLektoraPK getJezykLektoraPK() {
        return jezykLektoraPK;
    }

    public void setJezykLektoraPK(JezykLektoraPK jezykLektoraPK) {
        this.jezykLektoraPK = jezykLektoraPK;
    }

    public Boolean getNatywny() {
        return natywny;
    }

    public void setNatywny(Boolean natywny) {
        this.natywny = natywny;
    }

    public Jezyk getJezyk() {
        return jezyk;
    }

    public void setJezyk(Jezyk jezyk) {
        this.jezyk = jezyk;
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
        hash += (jezykLektoraPK != null ? jezykLektoraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JezykLektora)) {
            return false;
        }
        JezykLektora other = (JezykLektora) object;
        if ((this.jezykLektoraPK == null && other.jezykLektoraPK != null) || (this.jezykLektoraPK != null && !this.jezykLektoraPK.equals(other.jezykLektoraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.JezykLektora[ jezykLektoraPK=" + jezykLektoraPK + " ]";
    }
    
}
