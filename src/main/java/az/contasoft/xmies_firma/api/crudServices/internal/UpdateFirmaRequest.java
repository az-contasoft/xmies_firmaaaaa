package az.contasoft.xmies_firma.api.crudServices.internal;

public class UpdateFirmaRequest {

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

    public UpdateFirmaRequest(long idFirma, String adi, String address, String telefon, int isActive) {
        this.idFirma = idFirma;
        this.adi = adi;
        this.address = address;
        this.telefon = telefon;
        this.isActive = isActive;
    }

    public UpdateFirmaRequest() {

    }

    @Override
    public String toString() {
        return "UpdateFirmaRequest{" +
                "idFirma=" + idFirma +
                ", adi='" + adi + '\'' +
                ", address='" + address + '\'' +
                ", telefon='" + telefon + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
