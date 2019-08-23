package az.contasoft.xmies_firma.api.searchServices.internal;

public class RequestText {

    private String enteredText;

    public String getEnteredText() {
        return enteredText;
    }

    public void setEnteredText(String enteredText) {
        this.enteredText = enteredText;
    }

    public RequestText(String enteredText) {
        this.enteredText = enteredText;
    }

    public RequestText() {
    }

    @Override
    public String toString() {
        return "RequestText{" +
                "enteredText='" + enteredText + '\'' +
                '}';
    }
}
