package br.edu.ifnmg.dsc.extractnorth.servicos;

import java.util.List;

import br.edu.ifnmg.dsc.extractnorth.entidades.Funcionario;

public interface FuncionarioRepositorio extends Repositorio<Funcionario> {

    public Funcionario abrirPorCPF(String cpf);

    public List<Funcionario> buscarFuncionarioPorStatus(boolean status);

}