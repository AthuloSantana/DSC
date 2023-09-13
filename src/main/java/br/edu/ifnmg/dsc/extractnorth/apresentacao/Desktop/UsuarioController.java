package br.edu.ifnmg.dsc.extractnorth.apresentacao.Desktop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifnmg.dsc.extractnorth.entidades.TipoUsuario;
import br.edu.ifnmg.dsc.extractnorth.entidades.Usuario;
import br.edu.ifnmg.dsc.extractnorth.servicos.UsuarioRepositorio;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;

@Service
@FxmlView("UsuariosView.fxml")
public class UsuarioController extends Controller {

    // A entidade em edição
    private Usuario entidade;

    ////////////////////////////////////////////
    // Beans do Spring
    ////////////////////////////////////////////

    @Autowired
    private UsuarioRepositorio repositorio;

    ////////////////////////////////////////////
    // Componentes Visuais do JavaFX
    ////////////////////////////////////////////

    @FXML
    private TextField txtLoginBusca;

    @FXML
    private TableView<Usuario> tblBusca;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtNomeBusca;

    @FXML
    private TextField txtNome;

    @FXML
    private PasswordField pwdSenha;

    @FXML
    private Label lblId;

    @FXML
    private ChoiceBox<TipoUsuario> inpTipoUsuario;

    @FXML
    private TabPane abas;

    public UsuarioController() {
    }

    @FXML
    @Override
    public void initialize() {

        // Inicialização da classe mãe Controller
        super.initialize();

        // Configurar tabela
        configurarTabela();

        inpTipoUsuario.setItems(FXCollections.observableArrayList(TipoUsuario.values()));

        // Desabilita a aba de edição
        abas.getTabs().get(1).setDisable(true);

    }

    public void configurarTabela() {

        // Configurar as duas colunas

        tblBusca.getColumns().removeAll(tblBusca.getColumns());

        TableColumn<Usuario, Integer> codigo = new TableColumn<>("CÓDIGO");

        codigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo"));

        tblBusca.getColumns().add(codigo);

        TableColumn<Usuario, String> nome = new TableColumn<>("NOME");

        nome.setCellValueFactory(
                new PropertyValueFactory<>("nome"));

        tblBusca.getColumns().add(nome);

        TableColumn<Usuario, String> login = new TableColumn<>("LOGIN");

        login.setCellValueFactory(
                new PropertyValueFactory<>("login"));

        tblBusca.getColumns().add(login);
        // Confirugar o modo de seleção

        TableViewSelectionModel<Usuario> selectionModel = tblBusca.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        tblBusca.setSelectionModel(selectionModel);
    }

    public Usuario getEntidade() {
        return entidade;
    }

    public void setEntidade(Usuario entidade) {
        this.entidade = entidade;
    }

    public void carregarCampos() {
        lblId.setText(Long.toString(entidade.getId()));
        txtLogin.setText(entidade.getLogin());
        pwdSenha.setText(entidade.getSenha());
        txtNome.setText(entidade.getNome());
        inpTipoUsuario.setValue(entidade.getTipoUsuario());
    }

    public void carregarEntidade() {
        entidade.setLogin(txtLogin.getText());
        entidade.setSenha(pwdSenha.getText());
        entidade.setNome(txtNome.getText());
        entidade.setTipoUsuario(inpTipoUsuario.getValue());
    }

    @FXML
    public void buscar(Event e) {

        Usuario filtro = new Usuario();

        filtro.setLogin(txtLoginBusca.getText());
        filtro.setNome(txtNomeBusca.getText());

        List<Usuario> resultado = repositorio.Buscar(filtro);

        tblBusca.getItems().removeAll(tblBusca.getItems());

        tblBusca.getItems().addAll(resultado);

    }

    @FXML
    public void editar(Event e) {
        setEntidade((Usuario) tblBusca.getSelectionModel().getSelectedItem());
        carregarCampos();
        abas.getTabs().get(1).setDisable(false);
        abas.getSelectionModel().select(1);
    }

    // @FXML
    // public void novo(Event e) {
    // setEntidade(new Usuario());
    // carregarCampos();
    // abas.getTabs().get(1).setDisable(false);
    // abas.getSelectionModel().select(1);
    // }

    @FXML
    public void salvar(Event e) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente salvar as alterações?", ButtonType.YES,
                ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            carregarEntidade();
            if (repositorio.Salvar(entidade)) {
                Alert confirmacao = new Alert(AlertType.INFORMATION, "Registro salvo com sucesso! ", ButtonType.OK);
                confirmacao.showAndWait();
            } else {
                Alert confirmacao = new Alert(AlertType.ERROR,
                        "Falha ao salvar o registro! Contacte o administrador do sistema.", ButtonType.OK);
                confirmacao.showAndWait();
            }
        } else {
            Alert confirmacao = new Alert(AlertType.INFORMATION, "Operação cancelada! ", ButtonType.OK);
            confirmacao.showAndWait();
        }
    }

    @FXML
    public void cancelar(Event e) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente cancelar a edição?", ButtonType.YES,
                ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            abas.getTabs().get(1).setDisable(true);
            abas.getSelectionModel().select(0);
        }
    }

}