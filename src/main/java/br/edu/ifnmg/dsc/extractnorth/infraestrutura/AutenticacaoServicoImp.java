package br.edu.ifnmg.dsc.extractnorth.infraestrutura;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ifnmg.dsc.extractnorth.entidades.Usuario;
import br.edu.ifnmg.dsc.extractnorth.servicos.AutenticacaoServico;
import br.edu.ifnmg.dsc.extractnorth.servicos.UsuarioRepositorio;

public class AutenticacaoServicoImp implements AutenticacaoServico {
    private Usuario usuario;

    @Autowired
    private UsuarioRepositorio repositorio;

    public AutenticacaoServicoImp() {
        this.usuario = null;

    }

    @Override
    public boolean autenticar(String login, String senha) {
        try {
            usuario = repositorio.Abrir(login);
            return usuario != null && usuario.getSenha().equals(senha);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }

}
