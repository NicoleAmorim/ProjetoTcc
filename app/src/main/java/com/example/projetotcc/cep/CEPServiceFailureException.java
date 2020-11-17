package com.example.projetotcc.cep;

 /* Exceção lançada na ocorrência de algum erro inesperado no acesso ao serviço de busca de CEPs dos Correios */

public class CEPServiceFailureException extends RuntimeException {

    private static final long serialVersionUID = 1462228622695384135L;

    public CEPServiceFailureException() {
        super();
    }

    public CEPServiceFailureException(Throwable cause) {
        super(cause);
    }

}

