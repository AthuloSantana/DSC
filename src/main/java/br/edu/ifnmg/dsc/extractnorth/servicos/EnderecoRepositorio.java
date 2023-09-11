package br.edu.ifnmg.dsc.extractnorth.servicos;

import java.util.List;

import br.edu.ifnmg.dsc.extractnorth.entidades.Endereco;

public interface EnderecoRepositorio extends Repositorio<Endereco> {

    public List<Endereco> abrirPorCidade(String cidade);
    
}