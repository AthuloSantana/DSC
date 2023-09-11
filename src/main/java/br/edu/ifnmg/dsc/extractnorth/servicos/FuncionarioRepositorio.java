package br.edu.ifnmg.dsc.extractnorth.servicos;

import java.util.Date;

import br.edu.ifnmg.dsc.extractnorth.entidades.Funcionario;

public interface FuncionarioRepositorio extends Repositorio<Funcionario> {

  public boolean Cadastrar(String novoFuncionario);

  public Funcionario abrirPorCPF(String cpf);

  public Funcionario abrirPorDataNascimento(Date dataNascimento);

  public Funcionario abrirPorGenero(String genero);

}
