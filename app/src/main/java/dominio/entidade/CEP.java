package dominio.entidade;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CEP {
    private String CEP;
    private String Rua;
    private String Complemento;
    private String Bairro;
    private String Estado;
    private String Numero;
    private String Cidade;


    public CEP() { }


    public CEP(String cep) throws CEPException, JSONException {
        this.buscar(cep);
    }


    public final void buscar(String cep) throws CEPException, JSONException {
        this.setCEP(cep);

        String url = "http://viacep.com.br/ws/" + cep + "/json/";

        JSONObject obj = new JSONObject(this.get(url));

        if (!obj.has("erro")) {
            this.setCEP(obj.getString("cep"));
            this.setRua(obj.getString("logradouro"));
            this.setComplemento(obj.getString("complemento"));
            this.setBairro(obj.getString("bairro"));
            this.setEstado(obj.getString("uf"));
            this.setCidade(obj.getString("localidade"));
        } else {
            throw new CEPException("Não foi possível encontrar o CEP", cep);
        }
    }

    public final String get(String urlToRead) throws CEPException {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (MalformedURLException | ProtocolException ex) {
            throw new CEPException(ex.getMessage());
        } catch (IOException ex) {
            throw new CEPException(ex.getMessage());
        }

        return result.toString();
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getRua() {
        return Rua;
    }

    public void setRua(String rua) {
        Rua = rua;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }
}
