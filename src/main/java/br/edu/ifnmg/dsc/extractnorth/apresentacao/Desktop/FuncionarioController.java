package br.edu.ifnmg.dsc.extractnorth.apresentacao.Desktop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.inject.Inject;

import br.edu.ifnmg.dsc.extractnorth.entidades.Endereco;
import br.edu.ifnmg.dsc.extractnorth.entidades.Funcionario;
import br.edu.ifnmg.dsc.extractnorth.entidades.Genero;
import br.edu.ifnmg.dsc.extractnorth.servicos.EnderecoRepositorio;
import br.edu.ifnmg.dsc.extractnorth.servicos.FuncionarioRepositorio;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
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
@FxmlView("FuncionariosView.fxml")
public class FuncionarioController extends Controller {

    private Funcionario entidade;

    @Inject
    FuncionarioRepositorio funcionarios;

    @Autowired
    private FuncionarioRepositorio repositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @FXML
    private TextField txtNomeBusca;

    @FXML
    private TextField txtCpfBusca;

    @FXML
    private TableView<Funcionario> tblBusca;

    @FXML
    private TextField inpNome;

    @FXML
    private TextField inpCpf;

    @FXML
    private TextField inpTelefone;

    @FXML
    private TextField inpSalario;

    @FXML
    private TextField inpEmail;

    @FXML
    private DatePicker inpDataNascimento;

    @FXML
    private ChoiceBox<Genero> inpGenero;

    @FXML
    private TextField inpCargo;

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
    private CheckBox checkBoxAtivo;

    @FXML
    private Label lblId;

    @FXML
    private TabPane abas;

    public FuncionarioController() {

    }

    @FXML
    @Override
    public void initialize() {

        super.initialize();

        configurarTabela();

        inpGenero.setItems(FXCollections.observableArrayList(Genero.values()));

        abas.getTabs().get(1).setDisable(true);

    }

    private void configurarTabela() {

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<Funcionario, String> nome = new TableColumn<>("NOME");

        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblBusca.getColumns().add(nome);

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<Funcionario, String> cpf = new TableColumn<>("CPF");

        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tblBusca.getColumns().add(cpf);

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<Funcionario, String> cargo = new TableColumn<>("CARGO");

        cargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        tblBusca.getColumns().add(cargo);

        // Confirugar o modo de seleção

        TableViewSelectionModel<Funcionario> selectionModel = tblBusca.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        tblBusca.setSelectionModel(selectionModel);
    }

    public Funcionario getEntidade() {
        return entidade;
    }

    public void setEntidade(Funcionario entidade) {
        this.entidade = entidade;
    }

    public void carregarCampos() {
        lblId.setText(Long.toString(entidade.getId()));
        inpNome.setText(entidade.getNome());
        inpCpf.setText(entidade.getCpf());
        inpTelefone.setText(entidade.getTelefone());
        inpSalario.setText(entidade.getSalario().toString());
        inpEmail.setText(entidade.getEmail());
        inpGenero.setValue(entidade.getGenero());
        inpCargo.setText(entidade.getCargo());
        inpRua.setText(entidade.getEndereco().getRua());
        inpBairro.setText(entidade.getEndereco().getBairro());
        inpCidade.setText(entidade.getEndereco().getCidade());
        inpNumero.setText(entidade.getEndereco().getNumero());
        inpNumero.setText(entidade.getEndereco().getEstado());
        checkBoxAtivo.setSelected(entidade.isStatus());
        inpDataNascimento.setValue(entidade.getDataNascimento());
    }

    public void carregarEntidade() {
        entidade.setNome(inpNome.getText());
        entidade.setCpf(inpCpf.getText());
        entidade.setSalario(Double.parseDouble(inpSalario.getText()));
        entidade.setEmail(inpEmail.getText());
        entidade.setDataNascimento(inpDataNascimento.getValue());
        entidade.setGenero(inpGenero.getValue());
        entidade.setCargo(inpCargo.getText());
        entidade.setStatus(checkBoxAtivo.isSelected());
        checkBoxAtivo.setSelected(entidade.isStatus());

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

        Funcionario filtro = new Funcionario();

        filtro.setCpf(inpCpf.getText());
        filtro.setNome(inpNome.getText());

        List<Funcionario> resultado = repositorio.Buscar(filtro);

        tblBusca.getItems().removeAll(tblBusca.getItems());
        tblBusca.getItems().addAll(resultado);

    }

    @FXML
    public void editar(Event e) {
        setEntidade((Funcionario) tblBusca.getSelectionModel().getSelectedItem());
        carregarCampos();
        abas.getTabs().get(1).setDisable(false);
        abas.getSelectionModel().select(1);

    }

    @FXML
    public void novo(Event e) {
        setEntidade(new Funcionario());
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
