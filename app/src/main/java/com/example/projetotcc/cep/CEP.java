package com.example.projetotcc.cep;

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
    private String Logradouro;
    private String Complemento;
    private String Bairro;
    private String Estado;
    //private String Uf;

    /**
     * Constrói uma nova classe
     */
    public CEP() {
        this.CEP = null;
        this.Logradouro = null;
        this.Complemento = null;
        this.Bairro = null;
        this.Estado = null;
        //this.Uf = null;
    }

    /**
     * Constrói uma nova classe e busca um CEP no ViaCEP
     *
     * @param cep
     * @throws CEPException caso ocorra algum erro
     */
    public CEP(String cep) throws CEPException, JSONException {
        this.buscar(cep);
    }

    /**
     * Busca um CEP no ViaCEP
     *
     * @param cep
     * @throws CEPException caso ocorra algum erro
     */
    public final void buscar(String cep) throws CEPException, JSONException {
        this.CEP = cep;

        // define a url
        String url = "http://viacep.com.br/ws/" + cep + "/json/";

        // define os dados
        JSONObject obj = new JSONObject(this.get(url));

        if (!obj.has("erro")) {
            this.CEP = obj.getString("cep");
            this.Logradouro = obj.getString("logradouro");
            this.Complemento = obj.getString("complemento");
            this.Bairro = obj.getString("bairro");
            this.Estado = obj.getString("uf");
            //this.Uf = obj.getString("uf");
        } else {
            throw new CEPException("Não foi possível encontrar o CEP", cep);
        }
    }

    /**
     * Retonar o CEP
     *
     * @return
     */
    public String getCep() {
        return this.CEP;
    }

    /**
     * Retorna o nome da rua, avenida, travessa, ...
     *
     * @return
     */
    public String getLogradouro() {
        return this.Logradouro;
    }

    /**
     * Retorna se tem algum complemento Ex: lado impar
     *
     * @return
     */
    public String getComplemento() {
        return this.Complemento;
    }

    /**
     * Retorna o Bairro
     *
     * @return
     */
    public String getBairro() {
        return this.Bairro;
    }

    /**
     * Retorna a Cidade
     *
     * @return
     */
    public String getEstado() {
        return this.Estado;
    }

    /**
     * Retorna o UF
     *
     * @return
     */
    /*public String getUf() {
        return this.Uf;
    }*/


    /**
     * Procedimento para obtem dados via GET
     *
     * @param urlToRead endereço
     * @return conteúdo remoto
     * @throws CEPException caso ocorra algum erro
     */
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
}
