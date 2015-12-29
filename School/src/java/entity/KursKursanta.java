/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafa
 */
@Entity
@Table(name = "kurs_kursanta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KursKursanta.findAll", query = "SELECT k FROM KursKursanta k"),
    @NamedQuery(name = "KursKursanta.findById", query = "SELECT k FROM KursKursanta k WHERE k.id = :id")})
public class KursKursanta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "kurs_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Kurs kursId;
    @JoinColumn(name = "kursant_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Kursant kursantId;

    public KursKursanta() {
    }

    public KursKursanta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Kurs getKursId() {
        return kursId;
    }

    public void setKursId(Kurs kursId) {
        this.kursId = kursId;
    }

    public Kursant getKursantId() {
        return kursantId;
    }

    public void setKursantId(Kursant kursantId) {
        this.kursantId = kursantId;
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
        if (!(object instanceof KursKursanta)) {
            return false;
        }
        KursKursanta other = (KursKursanta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KursKursanta[ id=" + id + " ]";
    }
    
}