/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "termin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Termin.findAll", query = "SELECT t FROM Termin t"),
    @NamedQuery(name = "Termin.findById", query = "SELECT t FROM Termin t WHERE t.id = :id"),
    @NamedQuery(name = "Termin.findByDzien", query = "SELECT t FROM Termin t WHERE t.dzien = :dzien"),
    @NamedQuery(name = "Termin.findByGodzinaStart", query = "SELECT t FROM Termin t WHERE t.godzinaStart = :godzinaStart"),
    @NamedQuery(name = "Termin.findByGodzinaStop", query = "SELECT t FROM Termin t WHERE t.godzinaStop = :godzinaStop")})
public class Termin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 15)
    @Column(name = "dzien")
    private String dzien;
    @Column(name = "godzina_start")
    @Temporal(TemporalType.TIME)
    private Date godzinaStart;
    @Column(name = "godzina_stop")
    @Temporal(TemporalType.TIME)
    private Date godzinaStop;
    @JoinColumn(name = "kurs_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Kurs kursId;

    public Termin() {
    }

    public Termin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDzien() {
        return dzien;
    }

    public void setDzien(String dzien) {
        this.dzien = dzien;
    }

    public Date getGodzinaStart() {
        return godzinaStart;
    }

    public void setGodzinaStart(Date godzinaStart) {
        this.godzinaStart = godzinaStart;
    }

    public Date getGodzinaStop() {
        return godzinaStop;
    }

    public void setGodzinaStop(Date godzinaStop) {
        this.godzinaStop = godzinaStop;
    }

    public Kurs getKursId() {
        return kursId;
    }

    public void setKursId(Kurs kursId) {
        this.kursId = kursId;
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
        if (!(object instanceof Termin)) {
            return false;
        }
        Termin other = (Termin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Termin[ id=" + id + " ]";
    }
    
}
