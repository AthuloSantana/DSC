package br.edu.ifnmg.dsc.extractnorth.servicos;

import br.edu.ifnmg.dsc.extractnorth.entidades.Cliente;

public interface ClienteRepositorio extends Repositorio<Cliente> {
    
    public Cliente abrirPorCNPJ(String cnpj);

    public Cliente abrirPorAtividade(String atividade);

}