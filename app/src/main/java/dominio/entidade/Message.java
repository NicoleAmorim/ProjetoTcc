package dominio.entidade;

public class Message {
    private String text;
    private String ID;
    private long time;
    private boolean servidor;
    private String remetenteID;
    private String destinatarioID;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRemetenteID() {
        return remetenteID;
    }

    public void setRemetenteID(String remetenteID) {
        this.remetenteID = remetenteID;
    }

    public String getDestinatarioID() {
        return destinatarioID;
    }

    public void setDestinatarioID(String destinatarioID) {
        this.destinatarioID = destinatarioID;
    }

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }

    public boolean isServidor() {
        return servidor;
    }

    public void setServidor(boolean servidor) {
        this.servidor = servidor;
    }
}
