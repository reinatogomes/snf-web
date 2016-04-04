package com.snf.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.snf.dao.ServicoDAO;
import com.snf.enums.TipoTransacao;
import com.snf.model.Caixa;
import com.snf.model.Funcionario;
import com.snf.model.Servico;
import com.snf.model.Transacao;
import com.snf.vo.ServicoDataValorVO;

public class ServicoService implements Serializable {

	private static final long serialVersionUID = -3854194992056453807L;

	static final Logger log = Logger.getLogger(ServicoService.class);

	@Inject
	private ServicoDAO servicoDAO;

	@Inject
	private CaixaService caixaService;
	
	@Inject
	private TransacaoService transacaoService;
	
	public Servico salvar(Servico servico) {
		Caixa caixaAberto = caixaService.getCaixaAberto();
		caixaAberto.adicionarValor(servico.getValor());
		Transacao transacao = criarTransacao(servico, caixaAberto);
		transacaoService.salvar(transacao);
		caixaService.salvar(caixaAberto);
		return servicoDAO.save(servico);
	}

	private Transacao criarTransacao(Servico servico, Caixa caixaAberto) {
		Transacao transacao = new Transacao();
		transacao.setCaixa(caixaAberto);
		transacao.setData(new Date());
		transacao.setTipo(TipoTransacao.RECEITA);
		transacao.setValor(servico.getValor());
		return transacao;
	}

	public List<Servico> getAll() {
		return servicoDAO.getAll();
	}

	public void remover(Servico servico) {
		servicoDAO.delete(servico.getId());
	}

	public Servico getServico(Long id) {
		return servicoDAO.getById(id);
	}

	public List<Servico> getServicosByPeriodoAndFuncionario(Date dataInicio, Date dataFim, Funcionario funcionario) {
		return servicoDAO.getServicosByPeriodoAndFuncionario(dataInicio, dataFim, funcionario);
	}

	public List<ServicoDataValorVO> servicosByPeriodoAndFuncionario(Date dataInicial, Date dataFinal,
			Funcionario funcionario) {
		return servicoDAO.servicosByPeriodoAndFuncionario(dataInicial, dataFinal, funcionario);
	}
}
