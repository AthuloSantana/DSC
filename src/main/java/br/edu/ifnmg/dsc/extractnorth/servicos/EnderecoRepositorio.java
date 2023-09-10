package br.edu.ifnmg.dsc.extractnorth.servicos;

import br.edu.ifnmg.dsc.extractnorth.entidades.Endereco;
import br.edu.ifnmg.dsc.extractnorth.entidades.Pessoa;

public interface EnderecoRepositorio extends Repositorio<Endereco> {

    public Endereco abrirPorCidade(String cidade);
    
    public Endereco abrirPorPessoa(Pessoa pessoa);

}
