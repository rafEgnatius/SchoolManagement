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
@Table(name = "firma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firma.findAll", query = "SELECT f FROM Firma f"),
    @NamedQuery(name = "Firma.findById", query = "SELECT f FROM Firma f WHERE f.id = :id"),
    @NamedQuery(name = "Firma.findByNip", query = "SELECT f FROM Firma f WHERE f.nip = :nip"),
    @NamedQuery(name = "Firma.findByNazwa", query = "SELECT f FROM Firma f WHERE f.nazwa = :nazwa"),
    @NamedQuery(name = "Firma.findBySymbol", query = "SELECT f FROM Firma f WHERE f.symbol = :symbol"),
    @NamedQuery(name = "Firma.findByMiasto", query = "SELECT f FROM Firma f WHERE f.miasto = :miasto"),
    @NamedQuery(name = "Firma.findByOsoba", query = "SELECT f FROM Firma f WHERE f.osoba = :osoba"),
    @NamedQuery(name = "Firma.findByTelefon", query = "SELECT f FROM Firma f WHERE f.telefon = :telefon"),
    @NamedQuery(name = "Firma.findByKomorka", query = "SELECT f FROM Firma f WHERE f.komorka = :komorka"),
    @NamedQuery(name = "Firma.findByEmail", query = "SELECT f FROM Firma f WHERE f.email = :email"),
    @NamedQuery(name = "Firma.findByAdres", query = "SELECT f FROM Firma f WHERE f.adres = :adres")})
public class Firma extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 15)
    @Column(name = "nip")
    private String nip;
    @Size(max = 25)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 10)
    @Column(name = "symbol")
    private String symbol;
    @Size(max = 25)
    @Column(name = "miasto")
    private String miasto;
    @Size(max = 45)
    @Column(name = "osoba")
    private String osoba;
    @Size(max = 15)
    @Column(name = "telefon")
    private String telefon;
    @Size(max = 15)
    @Column(name = "komorka")
    private String komorka;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Size(max = 45)
    @Column(name = "adres")
    private String adres;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firmaId")
    private Collection<Kursant> kursantCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma")
    private Collection<Wplata> wplataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma")
    private Collection<Kurs> kursCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma")
    private Collection<Faktura> fakturaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma")
    private Collection<StawkaFirmy> stawkaFirmyCollection;

    public Firma() {
    }

    public Firma(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getOsoba() {
        return osoba;
    }

    public void setOsoba(String osoba) {
        this.osoba = osoba;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKomorka() {
        return komorka;
    }

    public void setKomorka(String komorka) {
        this.komorka = komorka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @XmlTransient
    public Collection<Kursant> getKursantCollection() {
        return kursantCollection;
    }

    public void setKursantCollection(Collection<Kursant> kursantCollection) {
        this.kursantCollection = kursantCollection;
    }

    @XmlTransient
    public Collection<Wplata> getWplataCollection() {
        return wplataCollection;
    }

    public void setWplataCollection(Collection<Wplata> wplataCollection) {
        this.wplataCollection = wplataCollection;
    }

    @XmlTransient
    public Collection<Kurs> getKursCollection() {
        return kursCollection;
    }

    public void setKursCollection(Collection<Kurs> kursCollection) {
        this.kursCollection = kursCollection;
    }

    @XmlTransient
    public Collection<Faktura> getFakturaCollection() {
        return fakturaCollection;
    }

    public void setFakturaCollection(Collection<Faktura> fakturaCollection) {
        this.fakturaCollection = fakturaCollection;
    }

    @XmlTransient
    public Collection<StawkaFirmy> getStawkaFirmyCollection() {
        return stawkaFirmyCollection;
    }

    public void setStawkaFirmyCollection(Collection<StawkaFirmy> stawkaFirmyCollection) {
        this.stawkaFirmyCollection = stawkaFirmyCollection;
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
        if (!(object instanceof Firma)) {
            return false;
        }
        Firma other = (Firma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Firma[ id=" + id + " ]";
    }
    
}
