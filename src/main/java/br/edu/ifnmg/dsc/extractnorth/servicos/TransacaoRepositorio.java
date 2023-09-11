package br.edu.ifnmg.dsc.extractnorth.servicos;

import java.util.Date;
import br.edu.ifnmg.dsc.extractnorth.entidades.TransacaoFinanceira;

public interface TransacaoRepositorio extends Repositorio<TransacaoFinanceira> {

    public TransacaoFinanceira abrirPorStatusTransacao(int statusTransacao);

    public TransacaoFinanceira abrirPorTipoTransacao(int tipoTransacao);

    public TransacaoFinanceira abrirPorDataTransacao(Date dataTransacao);

}
