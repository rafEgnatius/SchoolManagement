/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import converter.LocalDateAttributeConverter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Basic;
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
@Table(name = "rachunek")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rachunek.findAll", query = "SELECT r FROM Rachunek r"),
    @NamedQuery(name = "Rachunek.findById", query = "SELECT r FROM Rachunek r WHERE r.id = :id"),
    @NamedQuery(name = "Rachunek.findByNumer", query = "SELECT r FROM Rachunek r WHERE r.numer = :numer"),
    @NamedQuery(name = "Rachunek.findByData", query = "SELECT r FROM Rachunek r WHERE r.data = :data"),
    @NamedQuery(name = "Rachunek.findByKwota", query = "SELECT r FROM Rachunek r WHERE r.kwota = :kwota"),
    @NamedQuery(name = "Rachunek.findByOpis", query = "SELECT r FROM Rachunek r WHERE r.opis = :opis")})
public class Rachunek extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 25)
    @Column(name = "numer")
    private String numer;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private BigDecimal kwota;
    @Size(max = 45)
    @Column(name = "opis")
    private String opis;
    @JoinColumn(name = "lektor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lektor lektor;

    public Rachunek() {
    }

    public Rachunek(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rachunek)) {
            return false;
        }
        Rachunek other = (Rachunek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rachunek[ id=" + id + " ]";
    }
    
}
