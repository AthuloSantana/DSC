package br.edu.ifnmg.dsc.extractnorth.apresentacao.Desktop;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.inject.Inject;

import br.edu.ifnmg.dsc.extractnorth.entidades.Cliente;
import br.edu.ifnmg.dsc.extractnorth.entidades.Endereco;
import br.edu.ifnmg.dsc.extractnorth.servicos.ClienteRepositorio;
import br.edu.ifnmg.dsc.extractnorth.servicos.EnderecoRepositorio;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;

@Service
@FxmlView("ClientesView.fxml")
public class ClienteController extends Controller {

    private Cliente entidade;

    @Inject
    ClienteRepositorio clientes;

    @Autowired
    private ClienteRepositorio repositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @FXML
    private TextField txtNomeBusca;

    @FXML
    private TextField txtCnpjBusca;

    @FXML
    private TableView<Cliente> tblBusca;

    @FXML
    private TextField inpNome;

    @FXML
    private TextField inpCnpj;

    @FXML
    private TextField inpTelefone;

    @FXML
    private TextField inpEmail;

    @FXML
    private TextField inpRua;

    @FXML
    private TextField inpBairro;

    @FXML
    private TextField inpCidade;

    @FXML
    private TextField inpNumero;

    @FXML
    private TextField inpEstado;

    @FXML
    private TextField inpAtividade;

    @FXML
    private Label lblId;

    @FXML
    private TabPane abas;

    public ClienteController() {

    }

    @FXML
    @Override
    public void initialize() {

        super.initialize();

        configurarTabela();

        abas.getTabs().get(1).setDisable(true);

    }

    private void configurarTabela() {

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<Cliente, String> nome = new TableColumn<>("NOME");

        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblBusca.getColumns().add(nome);

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<Cliente, String> cnpj = new TableColumn<>("CNPJ");

        cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        tblBusca.getColumns().add(cnpj);

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<Cliente, String> atividade = new TableColumn<>("ATIVIDADE");

        atividade.setCellValueFactory(new PropertyValueFactory<>("atividade"));
        tblBusca.getColumns().add(atividade);

        // Confirugar o modo de seleção

        TableViewSelectionModel<Cliente> selectionModel = tblBusca.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        tblBusca.setSelectionModel(selectionModel);
    }

    public Cliente getEntidade() {
        return entidade;
    }

    public void setEntidade(Cliente entidade) {
        this.entidade = entidade;
    }

    public void carregarCampos() {
        lblId.setText(Long.toString(entidade.getId()));
        inpNome.setText(entidade.getNome());
        inpCnpj.setText(entidade.getCnpj());
        inpAtividade.setText(entidade.getAtividade());
        inpTelefone.setText(entidade.getTelefone());
        inpEmail.setText(entidade.getEmail());
        inpRua.setText(entidade.getEndereco().getRua());
        inpBairro.setText(entidade.getEndereco().getBairro());
        inpCidade.setText(entidade.getEndereco().getCidade());
        inpNumero.setText(entidade.getEndereco().getNumero());
        inpNumero.setText(entidade.getEndereco().getEstado());
    }

    public void carregarEntidade() {
        entidade.setNome(inpNome.getText());
        entidade.setCnpj(inpCnpj.getText());
        entidade.setEmail(inpEmail.getText());
        entidade.setAtividade(inpAtividade.getText());

        Endereco endereco = new Endereco();
        endereco.setRua(inpRua.getText());
        endereco.setNumero(inpNumero.getText());
        endereco.setBairro(inpBairro.getText());
        endereco.setCidade(inpCidade.getText());
        endereco.setEstado(inpEstado.getText());

        entidade.setEndereco(endereco);
    }

    @FXML
    public void buscar(Event e) {

        Cliente filtro = new Cliente();

        filtro.setCnpj(inpCnpj.getText());
        filtro.setNome(inpNome.getText());

        List<Cliente> resultado = repositorio.Buscar(filtro);

        tblBusca.getItems().removeAll(tblBusca.getItems());
        tblBusca.getItems().addAll(resultado);

    }

    @FXML
    public void editar(Event e) {
        setEntidade((Cliente) tblBusca.getSelectionModel().getSelectedItem());
        carregarCampos();
        abas.getTabs().get(1).setDisable(false);
        abas.getSelectionModel().select(1);

    }

    @FXML
    public void novo(Event e) {
        setEntidade(new Cliente());
        carregarCampos();
        abas.getTabs().get(1).setDisable(false);
        abas.getSelectionModel().select(1);
    }

    @FXML
    public void salvar(Event e) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente salvar as alterações?", ButtonType.YES,
                ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            carregarEntidade();

            if (enderecoRepositorio.Salvar(entidade.getEndereco()) && repositorio.Salvar(entidade)) {
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
