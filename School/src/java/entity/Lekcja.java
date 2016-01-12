/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import converter.LocalDateAttributeConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "lekcja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lekcja.findAll", query = "SELECT l FROM Lekcja l"),
    @NamedQuery(name = "Lekcja.findById", query = "SELECT l FROM Lekcja l WHERE l.id = :id"),
    @NamedQuery(name = "Lekcja.findByData", query = "SELECT l FROM Lekcja l WHERE l.data = :data"),
    @NamedQuery(name = "Lekcja.findByOdwolana", query = "SELECT l FROM Lekcja l WHERE l.odwolana = :odwolana")})
public class Lekcja extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate data;
    @Column(name = "odwolana")
    private Boolean odwolana;
    @JoinColumn(name = "kurs_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Kurs kurs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lekcja")
    private Collection<Obecnosc> obecnoscCollection;

    public Lekcja() {
    }

    public Lekcja(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean getOdwolana() {
        return odwolana;
    }

    public void setOdwolana(Boolean odwolana) {
        this.odwolana = odwolana;
    }

    public Kurs getKurs() {
        return kurs;
    }

    public void setKurs(Kurs kursId) {
        this.kurs = kursId;
    }

    @XmlTransient
    public Collection<Obecnosc> getObecnoscCollection() {
        return obecnoscCollection;
    }

    public void setObecnoscCollection(Collection<Obecnosc> obecnoscCollection) {
        this.obecnoscCollection = obecnoscCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lekcja)) {
            return false;
        }
        Lekcja other = (Lekcja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Lekcja[ id=" + id + " ]";
    }
    
}
