/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import converter.LocalDateAttributeConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
@Table(name = "ankieta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ankieta.findAll", query = "SELECT a FROM Ankieta a"),
    @NamedQuery(name = "Ankieta.findById", query = "SELECT a FROM Ankieta a WHERE a.id = :id"),
    @NamedQuery(name = "Ankieta.findByData", query = "SELECT a FROM Ankieta a WHERE a.data = :data"),
    @NamedQuery(name = "Ankieta.findByPytanie1", query = "SELECT a FROM Ankieta a WHERE a.pytanie1 = :pytanie1"),
    @NamedQuery(name = "Ankieta.findByPytanie2", query = "SELECT a FROM Ankieta a WHERE a.pytanie2 = :pytanie2"),
    @NamedQuery(name = "Ankieta.findByPytanie3", query = "SELECT a FROM Ankieta a WHERE a.pytanie3 = :pytanie3"),
    @NamedQuery(name = "Ankieta.findByPytanie4", query = "SELECT a FROM Ankieta a WHERE a.pytanie4 = :pytanie4"),
    @NamedQuery(name = "Ankieta.findByPytanie5", query = "SELECT a FROM Ankieta a WHERE a.pytanie5 = :pytanie5"),
    @NamedQuery(name = "Ankieta.findByPytanie6", query = "SELECT a FROM Ankieta a WHERE a.pytanie6 = :pytanie6"),
    @NamedQuery(name = "Ankieta.findByPytanie7", query = "SELECT a FROM Ankieta a WHERE a.pytanie7 = :pytanie7"),
    @NamedQuery(name = "Ankieta.findByPytanie8", query = "SELECT a FROM Ankieta a WHERE a.pytanie8 = :pytanie8"),
    @NamedQuery(name = "Ankieta.findByPytanie9", query = "SELECT a FROM Ankieta a WHERE a.pytanie9 = :pytanie9"),
    @NamedQuery(name = "Ankieta.findByPytanie10", query = "SELECT a FROM Ankieta a WHERE a.pytanie10 = :pytanie10"),
    @NamedQuery(name = "Ankieta.findByPytanie11", query = "SELECT a FROM Ankieta a WHERE a.pytanie11 = :pytanie11"),
    @NamedQuery(name = "Ankieta.findByPytanie12", query = "SELECT a FROM Ankieta a WHERE a.pytanie12 = :pytanie12"),
    @NamedQuery(name = "Ankieta.findByPytanie13", query = "SELECT a FROM Ankieta a WHERE a.pytanie13 = :pytanie13"),
    @NamedQuery(name = "Ankieta.findByPytanie14", query = "SELECT a FROM Ankieta a WHERE a.pytanie14 = :pytanie14"),
    @NamedQuery(name = "Ankieta.findByPytanie15", query = "SELECT a FROM Ankieta a WHERE a.pytanie15 = :pytanie15"),
    @NamedQuery(name = "Ankieta.findByPytanie16", query = "SELECT a FROM Ankieta a WHERE a.pytanie16 = :pytanie16"),
    @NamedQuery(name = "Ankieta.findByPytanie17", query = "SELECT a FROM Ankieta a WHERE a.pytanie17 = :pytanie17"),
    @NamedQuery(name = "Ankieta.findByPytanie18", query = "SELECT a FROM Ankieta a WHERE a.pytanie18 = :pytanie18"),
    @NamedQuery(name = "Ankieta.findByPytanie19", query = "SELECT a FROM Ankieta a WHERE a.pytanie19 = :pytanie19"),
    @NamedQuery(name = "Ankieta.findByPytanie20", query = "SELECT a FROM Ankieta a WHERE a.pytanie20 = :pytanie20"),
    @NamedQuery(name = "Ankieta.findByPytanie21", query = "SELECT a FROM Ankieta a WHERE a.pytanie21 = :pytanie21"),
    @NamedQuery(name = "Ankieta.findByPytanie22", query = "SELECT a FROM Ankieta a WHERE a.pytanie22 = :pytanie22"),
    @NamedQuery(name = "Ankieta.findByPytanieOpisowe23", query = "SELECT a FROM Ankieta a WHERE a.pytanieOpisowe23 = :pytanieOpisowe23")})
public class Ankieta extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate data;
    @Column(name = "pytanie_1")
    private Boolean pytanie1;
    @Column(name = "pytanie_2")
    private Boolean pytanie2;
    @Column(name = "pytanie_3")
    private Boolean pytanie3;
    @Column(name = "pytanie_4")
    private Boolean pytanie4;
    @Column(name = "pytanie_5")
    private Boolean pytanie5;
    @Column(name = "pytanie_6")
    private Boolean pytanie6;
    @Column(name = "pytanie_7")
    private Boolean pytanie7;
    @Column(name = "pytanie_8")
    private Boolean pytanie8;
    @Column(name = "pytanie_9")
    private Boolean pytanie9;
    @Column(name = "pytanie_10")
    private Boolean pytanie10;
    @Column(name = "pytanie_11")
    private Boolean pytanie11;
    @Column(name = "pytanie_12")
    private Boolean pytanie12;
    @Column(name = "pytanie_13")
    private Boolean pytanie13;
    @Column(name = "pytanie_14")
    private Boolean pytanie14;
    @Column(name = "pytanie_15")
    private Boolean pytanie15;
    @Column(name = "pytanie_16")
    private Boolean pytanie16;
    @Column(name = "pytanie_17")
    private Boolean pytanie17;
    @Column(name = "pytanie_18")
    private Boolean pytanie18;
    @Column(name = "pytanie_19")
    private Boolean pytanie19;
    @Column(name = "pytanie_20")
    private Boolean pytanie20;
    @Column(name = "pytanie_21")
    private Boolean pytanie21;
    @Column(name = "pytanie_22")
    private Boolean pytanie22;
    @Size(max = 205)
    @Column(name = "pytanie_opisowe_23")
    private String pytanieOpisowe23;
    @JoinColumn(name = "kursant_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Kursant kursantId;
    @JoinColumn(name = "lektor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lektor lektorId;

    public Ankieta() {
    }

    public Ankieta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean getPytanie1() {
        return pytanie1;
    }

    public void setPytanie1(Boolean pytanie1) {
        this.pytanie1 = pytanie1;
    }

    public Boolean getPytanie2() {
        return pytanie2;
    }

    public void setPytanie2(Boolean pytanie2) {
        this.pytanie2 = pytanie2;
    }

    public Boolean getPytanie3() {
        return pytanie3;
    }

    public void setPytanie3(Boolean pytanie3) {
        this.pytanie3 = pytanie3;
    }

    public Boolean getPytanie4() {
        return pytanie4;
    }

    public void setPytanie4(Boolean pytanie4) {
        this.pytanie4 = pytanie4;
    }

    public Boolean getPytanie5() {
        return pytanie5;
    }

    public void setPytanie5(Boolean pytanie5) {
        this.pytanie5 = pytanie5;
    }

    public Boolean getPytanie6() {
        return pytanie6;
    }

    public void setPytanie6(Boolean pytanie6) {
        this.pytanie6 = pytanie6;
    }

    public Boolean getPytanie7() {
        return pytanie7;
    }

    public void setPytanie7(Boolean pytanie7) {
        this.pytanie7 = pytanie7;
    }

    public Boolean getPytanie8() {
        return pytanie8;
    }

    public void setPytanie8(Boolean pytanie8) {
        this.pytanie8 = pytanie8;
    }

    public Boolean getPytanie9() {
        return pytanie9;
    }

    public void setPytanie9(Boolean pytanie9) {
        this.pytanie9 = pytanie9;
    }

    public Boolean getPytanie10() {
        return pytanie10;
    }

    public void setPytanie10(Boolean pytanie10) {
        this.pytanie10 = pytanie10;
    }

    public Boolean getPytanie11() {
        return pytanie11;
    }

    public void setPytanie11(Boolean pytanie11) {
        this.pytanie11 = pytanie11;
    }

    public Boolean getPytanie12() {
        return pytanie12;
    }

    public void setPytanie12(Boolean pytanie12) {
        this.pytanie12 = pytanie12;
    }

    public Boolean getPytanie13() {
        return pytanie13;
    }

    public void setPytanie13(Boolean pytanie13) {
        this.pytanie13 = pytanie13;
    }

    public Boolean getPytanie14() {
        return pytanie14;
    }

    public void setPytanie14(Boolean pytanie14) {
        this.pytanie14 = pytanie14;
    }

    public Boolean getPytanie15() {
        return pytanie15;
    }

    public void setPytanie15(Boolean pytanie15) {
        this.pytanie15 = pytanie15;
    }

    public Boolean getPytanie16() {
        return pytanie16;
    }

    public void setPytanie16(Boolean pytanie16) {
        this.pytanie16 = pytanie16;
    }

    public Boolean getPytanie17() {
        return pytanie17;
    }

    public void setPytanie17(Boolean pytanie17) {
        this.pytanie17 = pytanie17;
    }

    public Boolean getPytanie18() {
        return pytanie18;
    }

    public void setPytanie18(Boolean pytanie18) {
        this.pytanie18 = pytanie18;
    }

    public Boolean getPytanie19() {
        return pytanie19;
    }

    public void setPytanie19(Boolean pytanie19) {
        this.pytanie19 = pytanie19;
    }

    public Boolean getPytanie20() {
        return pytanie20;
    }

    public void setPytanie20(Boolean pytanie20) {
        this.pytanie20 = pytanie20;
    }

    public Boolean getPytanie21() {
        return pytanie21;
    }

    public void setPytanie21(Boolean pytanie21) {
        this.pytanie21 = pytanie21;
    }

    public Boolean getPytanie22() {
        return pytanie22;
    }

    public void setPytanie22(Boolean pytanie22) {
        this.pytanie22 = pytanie22;
    }

    public String getPytanieOpisowe23() {
        return pytanieOpisowe23;
    }

    public void setPytanieOpisowe23(String pytanieOpisowe23) {
        this.pytanieOpisowe23 = pytanieOpisowe23;
    }

    public Kursant getKursantId() {
        return kursantId;
    }

    public void setKursantId(Kursant kursantId) {
        this.kursantId = kursantId;
    }

    public Lektor getLektorId() {
        return lektorId;
    }

    public void setLektorId(Lektor lektorId) {
        this.lektorId = lektorId;
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
        if (!(object instanceof Ankieta)) {
            return false;
        }
        Ankieta other = (Ankieta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ankieta[ id=" + id + " ]";
    }
    
}
