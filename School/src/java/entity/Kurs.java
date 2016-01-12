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
@Table(name = "kurs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kurs.findAll", query = "SELECT k FROM Kurs k"),
    @NamedQuery(name = "Kurs.findById", query = "SELECT k FROM Kurs k WHERE k.id = :id"),
    @NamedQuery(name = "Kurs.findBySymbol", query = "SELECT k FROM Kurs k WHERE k.symbol = :symbol"),
    @NamedQuery(name = "Kurs.findByOpis", query = "SELECT k FROM Kurs k WHERE k.opis = :opis"),
    @NamedQuery(name = "Kurs.findByRok", query = "SELECT k FROM Kurs k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kurs.findBySemestr", query = "SELECT k FROM Kurs k WHERE k.semestr = :semestr"),
    @NamedQuery(name = "Kurs.findBySala", query = "SELECT k FROM Kurs k WHERE k.sala = :sala")})
public class Kurs extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "symbol")
    private String symbol;
    @Size(max = 45)
    @Column(name = "opis")
    private String opis;
    @Size(max = 10)
    @Column(name = "rok")
    private String rok;
    @Size(max = 10)
    @Column(name = "semestr")
    private String semestr;
    @Size(max = 45)
    @Column(name = "sala")
    private String sala;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kurs")
    private Collection<Lekcja> lekcjaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kurs")
    private Collection<Test> testCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kurs")
    private Collection<Termin> terminCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kurs")
    private Collection<KursKursanta> kursKursantaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kurs")
    private Collection<StawkaLektora> stawkaLektoraCollection;
    @JoinColumn(name = "firma_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Firma firma;
    @JoinColumn(name = "jezyk_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Jezyk jezyk;
    @JoinColumn(name = "lektor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lektor lektor;
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Program program;

    public Kurs() {
    }

    public Kurs(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getSemestr() {
        return semestr;
    }

    public void setSemestr(String semestr) {
        this.semestr = semestr;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    @XmlTransient
    public Collection<Lekcja> getLekcjaCollection() {
        return lekcjaCollection;
    }

    public void setLekcjaCollection(Collection<Lekcja> lekcjaCollection) {
        this.lekcjaCollection = lekcjaCollection;
    }

    @XmlTransient
    public Collection<Test> getTestCollection() {
        return testCollection;
    }

    public void setTestCollection(Collection<Test> testCollection) {
        this.testCollection = testCollection;
    }

    @XmlTransient
    public Collection<Termin> getTerminCollection() {
        return terminCollection;
    }

    public void setTerminCollection(Collection<Termin> terminCollection) {
        this.terminCollection = terminCollection;
    }

    @XmlTransient
    public Collection<KursKursanta> getKursKursantaCollection() {
        return kursKursantaCollection;
    }

    public void setKursKursantaCollection(Collection<KursKursanta> kursKursantaCollection) {
        this.kursKursantaCollection = kursKursantaCollection;
    }

    @XmlTransient
    public Collection<StawkaLektora> getStawkaLektoraCollection() {
        return stawkaLektoraCollection;
    }

    public void setStawkaLektoraCollection(Collection<StawkaLektora> stawkaLektoraCollection) {
        this.stawkaLektoraCollection = stawkaLektoraCollection;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirmaId(Firma firma) {
        this.firma = firma;
    }

    public Jezyk getJezyk() {
        return jezyk;
    }

    public void setJezyk(Jezyk jezykId) {
        this.jezyk = jezykId;
    }

    public Lektor getLektor() {
        return lektor;
    }

    public void setLektorId(Lektor lektor) {
        this.lektor = lektor;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
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
        if (!(object instanceof Kurs)) {
            return false;
        }
        Kurs other = (Kurs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Kurs[ id=" + id + " ]";
    }
}
