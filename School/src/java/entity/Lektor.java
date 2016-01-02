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
@Table(name = "lektor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lektor.findAll", query = "SELECT l FROM Lektor l"),
    @NamedQuery(name = "Lektor.findById", query = "SELECT l FROM Lektor l WHERE l.id = :id"),
    @NamedQuery(name = "Lektor.findByNazwa", query = "SELECT l FROM Lektor l WHERE l.nazwa = :nazwa"),
    @NamedQuery(name = "Lektor.findByMiasto", query = "SELECT l FROM Lektor l WHERE l.miasto = :miasto"),
    @NamedQuery(name = "Lektor.findByTelefon", query = "SELECT l FROM Lektor l WHERE l.telefon = :telefon"),
    @NamedQuery(name = "Lektor.findByEmail", query = "SELECT l FROM Lektor l WHERE l.email = :email"),
    @NamedQuery(name = "Lektor.findByUmowa", query = "SELECT l FROM Lektor l WHERE l.umowa = :umowa"),
    @NamedQuery(name = "Lektor.findByNip", query = "SELECT l FROM Lektor l WHERE l.nip = :nip")})
public class Lektor extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 25)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 25)
    @Column(name = "miasto")
    private String miasto;
    @Size(max = 15)
    @Column(name = "telefon")
    private String telefon;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Size(max = 15)
    @Column(name = "umowa")
    private String umowa;
    @Size(max = 15)
    @Column(name = "nip")
    private String nip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lektorId")
    private Collection<Ankieta> ankietaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lektor")
    private Collection<JezykLektora> jezykLektoraCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lektor")
    private Collection<StawkaLektora> stawkaLektoraCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lektor")
    private Collection<Wyplata> wyplataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lektor")
    private Collection<Wypozyczenie> wypozyczenieCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lektor")
    private Collection<Rachunek> rachunekCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lektor")
    private Collection<Kurs> kursCollection;

    public Lektor() {
    }

    public Lektor(Integer id) {
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

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUmowa() {
        return umowa;
    }

    public void setUmowa(String umowa) {
        this.umowa = umowa;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    @XmlTransient
    public Collection<Ankieta> getAnkietaCollection() {
        return ankietaCollection;
    }

    public void setAnkietaCollection(Collection<Ankieta> ankietaCollection) {
        this.ankietaCollection = ankietaCollection;
    }

    @XmlTransient
    public Collection<JezykLektora> getJezykLektoraCollection() {
        return jezykLektoraCollection;
    }

    public void setJezykLektoraCollection(Collection<JezykLektora> jezykLektoraCollection) {
        this.jezykLektoraCollection = jezykLektoraCollection;
    }

    @XmlTransient
    public Collection<StawkaLektora> getStawkaLektoraCollection() {
        return stawkaLektoraCollection;
    }

    public void setStawkaLektoraCollection(Collection<StawkaLektora> stawkaLektoraCollection) {
        this.stawkaLektoraCollection = stawkaLektoraCollection;
    }

    @XmlTransient
    public Collection<Wyplata> getWyplataCollection() {
        return wyplataCollection;
    }

    public void setWyplataCollection(Collection<Wyplata> wyplataCollection) {
        this.wyplataCollection = wyplataCollection;
    }

    @XmlTransient
    public Collection<Wypozyczenie> getWypozyczenieCollection() {
        return wypozyczenieCollection;
    }

    public void setWypozyczenieCollection(Collection<Wypozyczenie> wypozyczenieCollection) {
        this.wypozyczenieCollection = wypozyczenieCollection;
    }

    @XmlTransient
    public Collection<Rachunek> getRachunekCollection() {
        return rachunekCollection;
    }

    public void setRachunekCollection(Collection<Rachunek> rachunekCollection) {
        this.rachunekCollection = rachunekCollection;
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
        if (!(object instanceof Lektor)) {
            return false;
        }
        Lektor other = (Lektor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Lektor[ id=" + id + " ]";
    }
    
}
