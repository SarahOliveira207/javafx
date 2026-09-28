package com.template.validator;

/**
 * Interface genérica para definir um contrato de validação para campos (Princípio Aberto/Fechado - OCP e Substituição de Liskov - LSP).
 *
 * @param <T> Tipo do valor a ser validado
 */
public interface Validador<T> {

    boolean validar(T valor);

    /**
     * Retorna a mensagem de erro associada à falha na validação.
     *
     * @return Mensagem de erro amigável
     */
    String getMensagemErro();

    /**
     * Retorna o valor original que está sendo validado.
     *
     * @return O valor associado ao validador
     */
    T getValor();
}
