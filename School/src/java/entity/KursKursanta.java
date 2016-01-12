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
@Table(name = "kurs_kursanta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KursKursanta.findAll", query = "SELECT k FROM KursKursanta k"),
    @NamedQuery(name = "KursKursanta.findByKursId", query = "SELECT k FROM KursKursanta k WHERE k.kursKursantaPK.kursId = :kursId"),
    @NamedQuery(name = "KursKursanta.findByKursantId", query = "SELECT k FROM KursKursanta k WHERE k.kursKursantaPK.kursantId = :kursantId"),
    @NamedQuery(name = "KursKursanta.findByOpis", query = "SELECT k FROM KursKursanta k WHERE k.opis = :opis")})
public class KursKursanta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KursKursantaPK kursKursantaPK;
    @Size(max = 45)
    @Column(name = "opis")
    private String opis;
    @JoinColumn(name = "kurs_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kurs kurs;
    @JoinColumn(name = "kursant_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kursant kursant;

    public KursKursanta() {
    }

    public KursKursanta(KursKursantaPK kursKursantaPK) {
        this.kursKursantaPK = kursKursantaPK;
    }

    public KursKursanta(int kursId, int kursantId) {
        this.kursKursantaPK = new KursKursantaPK(kursId, kursantId);
    }

    public KursKursantaPK getKursKursantaPK() {
        return kursKursantaPK;
    }

    public void setKursKursantaPK(KursKursantaPK kursKursantaPK) {
        this.kursKursantaPK = kursKursantaPK;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Kurs getKurs() {
        return kurs;
    }

    public void setKurs(Kurs kurs) {
        this.kurs = kurs;
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
        hash += (kursKursantaPK != null ? kursKursantaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KursKursanta)) {
            return false;
        }
        KursKursanta other = (KursKursanta) object;
        if ((this.kursKursantaPK == null && other.kursKursantaPK != null) || (this.kursKursantaPK != null && !this.kursKursantaPK.equals(other.kursKursantaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KursKursanta[ kursKursantaPK=" + kursKursantaPK + " ]";
    }
    
}
