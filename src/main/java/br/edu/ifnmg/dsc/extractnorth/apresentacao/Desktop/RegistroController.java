package br.edu.ifnmg.dsc.extractnorth.apresentacao.Desktop;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.TipoUsuario;
import br.edu.ifnmg.dsc.extractnorth.entidades.Usuario;
import br.edu.ifnmg.dsc.extractnorth.servicos.UsuarioRepositorio;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import net.rgielen.fxweaver.core.FxmlView;

@Service
@FxmlView("RegistroView.fxml")
public class RegistroController extends Controller {

    @Inject
    UsuarioRepositorio usuarios;

    @FXML
    private TextField txtNovoUsuario;

    @FXML
    private TextField txtNomeNovoUsuario;

    @FXML
    private PasswordField pwdNovaSenha;

    @FXML
    private Button btnCadastrar;

    @FXML
    TitledPane RegistroView;

    @FXML
    private ChoiceBox<TipoUsuario> inpTipoUsuario;

    public RegistroController() {

    }

    @FXML
    @Override
    public void initialize() {

        super.initialize();
        inpTipoUsuario.setItems(FXCollections.observableArrayList(TipoUsuario.values()));

    }

    @FXML
    public void cadastrarNovoUsuario(Event e) {

        try {

            String login = txtNomeNovoUsuario.getText();
            Usuario usuarioExistente = usuarios.Abrir(login);

            if (usuarioExistente == null) {
                Usuario usuario = new Usuario();
                usuario.setNome(txtNomeNovoUsuario.getText());
                usuario.setLogin(txtNovoUsuario.getText());
                usuario.setSenha(pwdNovaSenha.getText());
                usuario.setTipoUsuario(inpTipoUsuario.getValue());
                usuarios.Salvar(usuario);

                Alert alert = new Alert(AlertType.INFORMATION, "Usuário cadastrado com sucesso ! ", ButtonType.OK);
                alert.showAndWait();

                txtNovoUsuario.clear();
                pwdNovaSenha.clear();
                carregarScene(RegistroView, LoginController.class);

            } else {
                Alert alert = new Alert(AlertType.ERROR, "Já existe um usuário cadastrado com esse login!.",
                        ButtonType.OK);
                alert.showAndWait();
            }

        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR, "Erro ao cadastrar usuário.", ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    public void retornarTelaLogin() {
        carregarScene(RegistroView, LoginController.class);
    }

}
