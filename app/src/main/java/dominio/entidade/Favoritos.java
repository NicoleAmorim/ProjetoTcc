package dominio.entidade;

public class Favoritos {
    private boolean isTRue;
    private String idServico;

    public boolean isTRue() {
        return isTRue;
    }

    public void setTRue(boolean TRue) {
        isTRue = TRue;
    }

    public String getIdServico() {
        return idServico;
    }

    public void setIdServico(String idServico) {
        this.idServico = idServico;
    }
}
