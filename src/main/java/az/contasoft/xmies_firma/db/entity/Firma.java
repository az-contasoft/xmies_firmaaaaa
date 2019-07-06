package az.contasoft.xmies_firma.db.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "firma")
public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "firma", nullable = false, unique = true)

    private long idFirma;
    private String adi;
    private String address;
    private String telefon;
    private int isActive;

    public long getIdFirma() {
        return idFirma;
    }

    public void setIdFirma(long idFirma) {
        this.idFirma = idFirma;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Firma(String adi, String address, String telefon, int isActive) {
        this.adi = adi;
        this.address = address;
        this.telefon = telefon;
        this.isActive = isActive;
    }

    public Firma() {

    }

    @Override
    public String toString() {
        return "Firma{" +
                "idFirma=" + idFirma +
                ", adi='" + adi + '\'' +
                ", address='" + address + '\'' +
                ", telefon='" + telefon + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
