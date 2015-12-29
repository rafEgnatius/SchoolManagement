/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "wyplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wyplata.findAll", query = "SELECT w FROM Wyplata w"),
    @NamedQuery(name = "Wyplata.findById", query = "SELECT w FROM Wyplata w WHERE w.wyplataPK.id = :id"),
    @NamedQuery(name = "Wyplata.findByData", query = "SELECT w FROM Wyplata w WHERE w.data = :data"),
    @NamedQuery(name = "Wyplata.findByKwota", query = "SELECT w FROM Wyplata w WHERE w.kwota = :kwota"),
    @NamedQuery(name = "Wyplata.findByOpis", query = "SELECT w FROM Wyplata w WHERE w.opis = :opis"),
    @NamedQuery(name = "Wyplata.findByLektorId", query = "SELECT w FROM Wyplata w WHERE w.wyplataPK.lektorId = :lektorId")})
public class Wyplata implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WyplataPK wyplataPK;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private BigDecimal kwota;
    @Size(max = 45)
    @Column(name = "opis")
    private String opis;
    @JoinColumn(name = "lektor_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lektor lektor;

    public Wyplata() {
    }

    public Wyplata(WyplataPK wyplataPK) {
        this.wyplataPK = wyplataPK;
    }

    public Wyplata(int id, int lektorId) {
        this.wyplataPK = new WyplataPK(id, lektorId);
    }

    public WyplataPK getWyplataPK() {
        return wyplataPK;
    }

    public void setWyplataPK(WyplataPK wyplataPK) {
        this.wyplataPK = wyplataPK;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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
        hash += (wyplataPK != null ? wyplataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wyplata)) {
            return false;
        }
        Wyplata other = (Wyplata) object;
        if ((this.wyplataPK == null && other.wyplataPK != null) || (this.wyplataPK != null && !this.wyplataPK.equals(other.wyplataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wyplata[ wyplataPK=" + wyplataPK + " ]";
    }
    
}
