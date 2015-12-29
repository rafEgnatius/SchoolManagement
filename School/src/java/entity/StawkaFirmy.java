/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "stawka_firmy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StawkaFirmy.findAll", query = "SELECT s FROM StawkaFirmy s"),
    @NamedQuery(name = "StawkaFirmy.findByFirmaId", query = "SELECT s FROM StawkaFirmy s WHERE s.stawkaFirmyPK.firmaId = :firmaId"),
    @NamedQuery(name = "StawkaFirmy.findByStawka", query = "SELECT s FROM StawkaFirmy s WHERE s.stawka = :stawka"),
    @NamedQuery(name = "StawkaFirmy.findByNatywny", query = "SELECT s FROM StawkaFirmy s WHERE s.stawkaFirmyPK.natywny = :natywny")})
public class StawkaFirmy implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StawkaFirmyPK stawkaFirmyPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stawka")
    private BigDecimal stawka;
    @JoinColumn(name = "firma_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Firma firma;

    public StawkaFirmy() {
    }

    public StawkaFirmy(StawkaFirmyPK stawkaFirmyPK) {
        this.stawkaFirmyPK = stawkaFirmyPK;
    }

    public StawkaFirmy(int firmaId, boolean natywny) {
        this.stawkaFirmyPK = new StawkaFirmyPK(firmaId, natywny);
    }

    public StawkaFirmyPK getStawkaFirmyPK() {
        return stawkaFirmyPK;
    }

    public void setStawkaFirmyPK(StawkaFirmyPK stawkaFirmyPK) {
        this.stawkaFirmyPK = stawkaFirmyPK;
    }

    public BigDecimal getStawka() {
        return stawka;
    }

    public void setStawka(BigDecimal stawka) {
        this.stawka = stawka;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stawkaFirmyPK != null ? stawkaFirmyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StawkaFirmy)) {
            return false;
        }
        StawkaFirmy other = (StawkaFirmy) object;
        if ((this.stawkaFirmyPK == null && other.stawkaFirmyPK != null) || (this.stawkaFirmyPK != null && !this.stawkaFirmyPK.equals(other.stawkaFirmyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StawkaFirmy[ stawkaFirmyPK=" + stawkaFirmyPK + " ]";
    }
    
}
