/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import converter.LocalDateAttributeConverter;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "wypozyczenie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wypozyczenie.findAll", query = "SELECT w FROM Wypozyczenie w"),
    @NamedQuery(name = "Wypozyczenie.findByPodrecznikId", query = "SELECT w FROM Wypozyczenie w WHERE w.wypozyczeniePK.podrecznikId = :podrecznikId"),
    @NamedQuery(name = "Wypozyczenie.findByLektorId", query = "SELECT w FROM Wypozyczenie w WHERE w.wypozyczeniePK.lektorId = :lektorId"),
    @NamedQuery(name = "Wypozyczenie.findByData", query = "SELECT w FROM Wypozyczenie w WHERE w.data = :data")})
public class Wypozyczenie implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WypozyczeniePK wypozyczeniePK;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate data;
    @JoinColumn(name = "lektor_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lektor lektor;
    @JoinColumn(name = "podrecznik_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Podrecznik podrecznik;

    public Wypozyczenie() {
    }

    public Wypozyczenie(WypozyczeniePK wypozyczeniePK) {
        this.wypozyczeniePK = wypozyczeniePK;
    }

    public Wypozyczenie(int podrecznikId, int lektorId) {
        this.wypozyczeniePK = new WypozyczeniePK(podrecznikId, lektorId);
    }

    public WypozyczeniePK getWypozyczeniePK() {
        return wypozyczeniePK;
    }

    public void setWypozyczeniePK(WypozyczeniePK wypozyczeniePK) {
        this.wypozyczeniePK = wypozyczeniePK;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Lektor getLektor() {
        return lektor;
    }

    public void setLektor(Lektor lektor) {
        this.lektor = lektor;
    }

    public Podrecznik getPodrecznik() {
        return podrecznik;
    }

    public void setPodrecznik(Podrecznik podrecznik) {
        this.podrecznik = podrecznik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wypozyczeniePK != null ? wypozyczeniePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wypozyczenie)) {
            return false;
        }
        Wypozyczenie other = (Wypozyczenie) object;
        if ((this.wypozyczeniePK == null && other.wypozyczeniePK != null) || (this.wypozyczeniePK != null && !this.wypozyczeniePK.equals(other.wypozyczeniePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wypozyczenie[ wypozyczeniePK=" + wypozyczeniePK + " ]";
    }
    
}
