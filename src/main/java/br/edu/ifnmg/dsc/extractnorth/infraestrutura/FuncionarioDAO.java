package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.Funcionario;
import br.edu.ifnmg.dsc.extractnorth.servicos.FuncionarioRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class FuncionarioDAO extends DAO<Funcionario> implements FuncionarioRepositorio {

  @PersistenceContext
  private EntityManager entityManager;

  public FuncionarioDAO() {
    super(Funcionario.class);
  }

  @Override
  public Funcionario abrirPorCPF(String cpf) {
    Query consulta = getManager().createQuery("select f from Funcionario f where f.cpf = :p");
    consulta.setParameter("p", cpf);
    return (Funcionario) consulta.getSingleResult();
  }

  @Override
  public Funcionario abrirPorDataNascimento(Date dataNascimento) {
    Query consulta = getManager().createQuery("select f from Funcionario f where f.dataNascimento = :p");
    consulta.setParameter("p", dataNascimento);
    return (Funcionario) consulta.getSingleResult();
  }

  @Override
  public Funcionario abrirPorGenero(String genero) {
    Query consulta = getManager().createQuery("select f from Funcionario f where f.genero = :p");
    consulta.setParameter("p", genero);
    return (Funcionario) consulta.getSingleResult();
  }

  @Override
  @Transactional
  public List<Funcionario> Buscar(Funcionario filtro) {
    String jpql = "select f from Funcionario f join f.endereco e ";
    String where = "";
    List<Object> parametros = new ArrayList<>();

    if (filtro.getNome() != null || filtro.getNome() != "") {
      where += " f.nome like CONCAT('%', :p1 ,'%') ";
      parametros.add(filtro.getNome());
    }

    if (filtro.getTelefone() != null || filtro.getTelefone() != "") {
      if (where.length() > 0)
        where += " and ";

      where += " f.telefone = :p2 ";
      parametros.add(filtro.getTelefone());
    }

    if (filtro.getCpf() != null || filtro.getCpf() != "") {
      if (where.length() > 0)
        where += "and ";

      where += "f.cpf = :p3 ";
      parametros.add(filtro.getCpf());
    }

    if (filtro.getEmail() != null || filtro.getEmail() != "") {
      if (where.length() > 0)
        where += "and ";

      where += "f.email = :p4";
      parametros.add(filtro.getEmail());
    }

    if (filtro.getGenero() != null) {
      if (where.length() > 0)
        where += "and ";

      where += "f.genero = :p5";
      parametros.add(filtro.getGenero());
    }

    if (filtro.getDataNascimento() != null) {
      if (where.length() > 0)
        where += "and ";

      where += "f.dataNascimento = :p6";
      parametros.add(filtro.getDataNascimento());
    }

    if (filtro.getEndereco().getCidade() != null || filtro.getEndereco().getCidade() != "") {
      if (where.length() > 0)
        where += "and ";

      where += "e.cidade = :p7 ";
      parametros.add(filtro.getEndereco().getCidade());
    }

    if (where.length() > 0) {
      jpql += " where " + where;
    }

    TypedQuery<Funcionario> consulta = getManager().createQuery(jpql, Funcionario.class);
    if (where.length() > 0) {
      int ordem = 0;
      for (Object parametro : parametros) {
        ordem++;
        consulta.setParameter(ordem, parametro);
      }
    }

    return consulta.getResultList();
  }

  // cadastro tem que ser no controller, da pra usar o abrir por cpf pra verificar
  // se já existe alguem cadastrado com o cpf informado
  // @Override
  // @Transactional
  // public boolean Cadastrar(String novoFuncionaro) {
  // TypedQuery<Funcionario> consulta = getManager().createQuery("select f FROM
  // Funcionario f WHERE f.cpf = :cpf",
  // Funcionario.class);
  // consulta.setParameter("cpf", novoFuncionaro);

  // List<Funcionario> funcionarios = consulta.getResultList();

  // if (!funcionarios.isEmpty()) {
  // return false;
  // }

  // try {
  // Funcionario funcionario = new Funcionario();
  // funcionario.setNome(novoFuncionaro);
  // funcionario.setCpf(novoFuncionaro);
  // funcionario.setEmail(novoFuncionaro);
  // funcionario.setTelefone(novoFuncionaro);
  // funcionario.setStatus(false);
  // funcionario.setSalario(null);

  // entityManager().persist(funcionario);

  // return true;
  // } catch (Exception ex) {
  // System.out.println("Erro ao cadastrar funcionário: " + ex.getMessage());
  // return false;
  // }
  // }

  // private EntityManager entityManager() {
  // return entityManager;
  // }

  // @Override
  // @Transactional
  // public List<Funcionario> Buscar(Funcionario filtro) {
  // try {
  // String jpql = "select f from Funcionario f";

  // if (!filtro.getCpf().isEmpty()) {
  // jpql += " WHERE f.cpf like :cpf";
  // }

  // TypedQuery<Funcionario> consulta = getManager().createQuery(jpql,
  // Funcionario.class);
  // if (!filtro.getCpf().isEmpty()) {
  // consulta.setParameter("cpf", filtro.getCpf());
  // }
  // return consulta.getResultList();

  // } catch (Exception ex) {
  // return null;
  // }

  // }

}
