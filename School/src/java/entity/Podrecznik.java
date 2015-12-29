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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "podrecznik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podrecznik.findAll", query = "SELECT p FROM Podrecznik p"),
    @NamedQuery(name = "Podrecznik.findById", query = "SELECT p FROM Podrecznik p WHERE p.id = :id"),
    @NamedQuery(name = "Podrecznik.findByNazwa", query = "SELECT p FROM Podrecznik p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Podrecznik.findByPoziom", query = "SELECT p FROM Podrecznik p WHERE p.poziom = :poziom")})
public class Podrecznik extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 5)
    @Column(name = "poziom")
    private String poziom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "podrecznik")
    private Collection<Wypozyczenie> wypozyczenieCollection;
    @JoinColumn(name = "jezyk_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Jezyk jezyk;

    public Podrecznik() {
    }

    public Podrecznik(Integer id) {
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

    public String getPoziom() {
        return poziom;
    }

    public void setPoziom(String poziom) {
        this.poziom = poziom;
    }

    @XmlTransient
    public Collection<Wypozyczenie> getWypozyczenieCollection() {
        return wypozyczenieCollection;
    }

    public void setWypozyczenieCollection(Collection<Wypozyczenie> wypozyczenieCollection) {
        this.wypozyczenieCollection = wypozyczenieCollection;
    }

    public Jezyk getJezyk() {
        return jezyk;
    }

    public void setJezyk(Jezyk jezyk) {
        this.jezyk = jezyk;
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
        if (!(object instanceof Podrecznik)) {
            return false;
        }
        Podrecznik other = (Podrecznik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Podrecznik[ id=" + id + " ]";
    }
    
}
