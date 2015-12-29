/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "jezyk")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jezyk.findAll", query = "SELECT j FROM Jezyk j"),
    @NamedQuery(name = "Jezyk.findById", query = "SELECT j FROM Jezyk j WHERE j.id = :id"),
    @NamedQuery(name = "Jezyk.findByNazwa", query = "SELECT j FROM Jezyk j WHERE j.nazwa = :nazwa"),
    @NamedQuery(name = "Jezyk.findBySymbol", query = "SELECT j FROM Jezyk j WHERE j.symbol = :symbol")})
public class Jezyk extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 15)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 10)
    @Column(name = "symbol")
    private String symbol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jezyk")
    private Collection<JezykLektora> jezykLektoraCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jezyk")
    private Collection<Podrecznik> podrecznikCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jezyk")
    private Collection<JezykKursanta> jezykKursantaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jezyk")
    private Collection<Kurs> kursCollection;

    public Jezyk() {
    }

    public Jezyk(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @XmlTransient
    public Collection<JezykLektora> getJezykLektoraCollection() {
        return jezykLektoraCollection;
    }

    public void setJezykLektoraCollection(Collection<JezykLektora> jezykLektoraCollection) {
        this.jezykLektoraCollection = jezykLektoraCollection;
    }

    @XmlTransient
    public Collection<Podrecznik> getPodrecznikCollection() {
        return podrecznikCollection;
    }

    public void setPodrecznikCollection(Collection<Podrecznik> podrecznikCollection) {
        this.podrecznikCollection = podrecznikCollection;
    }

    @XmlTransient
    public Collection<JezykKursanta> getJezykKursantaCollection() {
        return jezykKursantaCollection;
    }

    public void setJezykKursantaCollection(Collection<JezykKursanta> jezykKursantaCollection) {
        this.jezykKursantaCollection = jezykKursantaCollection;
    }

    @XmlTransient
    public Collection<Kurs> getKursCollection() {
        return kursCollection;
    }

    public void setKursCollection(Collection<Kurs> kursCollection) {
        this.kursCollection = kursCollection;
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
        if (!(object instanceof Jezyk)) {
            return false;
        }
        Jezyk other = (Jezyk) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Jezyk[ id=" + id + " ]";
    }
    
}
