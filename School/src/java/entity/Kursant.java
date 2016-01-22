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
@Table(name = "kursant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kursant.findAll", query = "SELECT k FROM Kursant k"),
    @NamedQuery(name = "Kursant.findById", query = "SELECT k FROM Kursant k WHERE k.id = :id"),
    @NamedQuery(name = "Kursant.findByNazwa", query = "SELECT k FROM Kursant k WHERE k.nazwa = :nazwa"),
    @NamedQuery(name = "Kursant.findByTelefon", query = "SELECT k FROM Kursant k WHERE k.telefon = :telefon"),
    @NamedQuery(name = "Kursant.findByEmail", query = "SELECT k FROM Kursant k WHERE k.email = :email")})
public class Kursant extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 25)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 15)
    @Column(name = "telefon")
    private String telefon;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kursant")
    private Collection<Ankieta> ankietaCollection;
    @JoinColumn(name = "firma_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Firma firma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kursant")
    private Collection<Test> testCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kursant")
    private Collection<Obecnosc> obecnoscCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kursant")
    private Collection<KursKursanta> kursKursantaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kursant")
    private Collection<JezykKursanta> jezykKursantaCollection;

    public Kursant() {
    }

    public Kursant(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Ankieta> getAnkietaCollection() {
        return ankietaCollection;
    }

    public void setAnkietaCollection(Collection<Ankieta> ankietaCollection) {
        this.ankietaCollection = ankietaCollection;
    }

    @Override
    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
        firma.addKursant(this);
    }

    @XmlTransient
    public Collection<Test> getTestCollection() {
        return testCollection;
    }

    public void setTestCollection(Collection<Test> testCollection) {
        this.testCollection = testCollection;
    }

    @XmlTransient
    public Collection<Obecnosc> getObecnoscCollection() {
        return obecnoscCollection;
    }

    public void setObecnoscCollection(Collection<Obecnosc> obecnoscCollection) {
        this.obecnoscCollection = obecnoscCollection;
    }

    @XmlTransient
    public Collection<KursKursanta> getKursKursantaCollection() {
        return kursKursantaCollection;
    }

    public void setKursKursantaCollection(Collection<KursKursanta> kursKursantaCollection) {
        this.kursKursantaCollection = kursKursantaCollection;
    }

    @XmlTransient
    public Collection<JezykKursanta> getJezykKursantaCollection() {
        return jezykKursantaCollection;
    }

    public void setJezykKursantaCollection(Collection<JezykKursanta> jezykKursantaCollection) {
        this.jezykKursantaCollection = jezykKursantaCollection;
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
        if (!(object instanceof Kursant)) {
            return false;
        }
        Kursant other = (Kursant) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "entity.Kursant[ id=" + id + " ]";
    }

    // additional
    
    void addObecnosc(Obecnosc obecnosc) {
       obecnoscCollection.add(obecnosc);
    }
    
}
