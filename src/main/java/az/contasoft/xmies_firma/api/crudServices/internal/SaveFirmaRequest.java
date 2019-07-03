package az.contasoft.xmies_firma.api.crudServices.internal;

public class SaveFirmaRequest {

    private long idFirma;
    private String adi;
    private String address;
    private String telefon;
    private int isActive;

    public Long getIdFirma() {
        return idFirma;
    }

    public void setIdFirma(Long idFirma) {
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public SaveFirmaRequest(Long idFirma, String adi, String address, String telefon, Integer isActive) {
        this.idFirma = idFirma;
        this.adi = adi;
        this.address = address;
        this.telefon = telefon;
        this.isActive = isActive;
    }

    public SaveFirmaRequest() {

    }

    @Override
    public String toString() {
        return "SaveFirmaRequest{" +
                "idFirma=" + idFirma +
                ", adi='" + adi + '\'' +
                ", address='" + address + '\'' +
                ", telefon='" + telefon + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
