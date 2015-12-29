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
@Table(name = "faktura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faktura.findAll", query = "SELECT f FROM Faktura f"),
    @NamedQuery(name = "Faktura.findById", query = "SELECT f FROM Faktura f WHERE f.id = :id"),
    @NamedQuery(name = "Faktura.findByNumer", query = "SELECT f FROM Faktura f WHERE f.numer = :numer"),
    @NamedQuery(name = "Faktura.findByData", query = "SELECT f FROM Faktura f WHERE f.data = :data"),
    @NamedQuery(name = "Faktura.findByKwota", query = "SELECT f FROM Faktura f WHERE f.kwota = :kwota"),
    @NamedQuery(name = "Faktura.findByOpis", query = "SELECT f FROM Faktura f WHERE f.opis = :opis")})
public class Faktura extends AbstractEntity implements Serializable {
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
    @JoinColumn(name = "firma_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Firma firma;

    public Faktura() {
    }

    public Faktura(Integer id) {
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

    public Firma getFirma() {
        return firma;
    }

    public void setFirmaId(Firma firma) {
        this.firma = firma;
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
        if (!(object instanceof Faktura)) {
            return false;
        }
        Faktura other = (Faktura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Faktura[ id=" + id + " ]";
    }
    
}
