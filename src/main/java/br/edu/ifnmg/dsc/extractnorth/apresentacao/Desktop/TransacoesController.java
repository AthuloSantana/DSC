package br.edu.ifnmg.dsc.extractnorth.apresentacao.Desktop;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.inject.Inject;

import br.edu.ifnmg.dsc.extractnorth.entidades.Estoque;
import br.edu.ifnmg.dsc.extractnorth.entidades.TipoUsuario;
import br.edu.ifnmg.dsc.extractnorth.entidades.TransacaoFinanceira;
import br.edu.ifnmg.dsc.extractnorth.servicos.EstoqueRepositorio;
import br.edu.ifnmg.dsc.extractnorth.servicos.TransacaoRepositorio;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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
@FxmlView("TransacoesView.fxml")
public class TransacoesController extends Controller {

    private TransacaoFinanceira entidade;

    @Inject
    TransacaoRepositorio transacoes;

    @Autowired
    private TransacaoRepositorio repositorio;

    @Autowired
    private EstoqueRepositorio estoqueRepositorio;

    @FXML
    private TextField txtNomeBusca;

    @FXML
    private TableView<TransacaoFinanceira> tblBusca;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPrecoVenda;

    @FXML
    private TextField txtPrecoCusto;

    @FXML
    private ChoiceBox<TipoUsuario> inpPessoa;

    @FXML
    private Label lblId;

    @FXML
    private TabPane abas;

    public TransacoesController() {

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
        TableColumn<TransacaoFinanceira, String> nome = new TableColumn<>("NOME");

        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblBusca.getColumns().add(nome);

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<TransacaoFinanceira, Double> precoCompra = new TableColumn<>("P. CUSTO");

        precoCompra.setCellValueFactory(new PropertyValueFactory<>("precoCompra"));
        tblBusca.getColumns().add(precoCompra);

        tblBusca.getColumns().removeAll(tblBusca.getColumns());
        TableColumn<TransacaoFinanceira, Double> precoVenda = new TableColumn<>("P. VENDA");

        precoVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        tblBusca.getColumns().add(precoVenda);

        // Confirugar o modo de seleção

        TableViewSelectionModel<TransacaoFinanceira> selectionModel = tblBusca.getSelectionModel();

        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        tblBusca.setSelectionModel(selectionModel);
    }

    public TransacaoFinanceira getEntidade() {
        return entidade;
    }

    public void setEntidade(TransacaoFinanceira entidade) {
        this.entidade = entidade;
    }

    public void carregarCampos() {
        lblId.setText(Long.toString(entidade.getId()));
        txtNome.setText(entidade.getNome());
        txtPrecoCusto.setText(entidade.getPrecoCompra().toString());
        txtPrecoVenda.setText(entidade.getPrecoVenda().toString());
    }

    public void carregarEntidade() {
        entidade.setNome(txtNome.getText());
        entidade.setPrecoCompra(Double.parseDouble(txtPrecoCusto.getText()));
        entidade.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText()));
    }

    @FXML
    public void buscar(Event e) {

        TransacaoFinanceira filtro = new TransacaoFinanceira();

        filtro.setNome(txtNomeBusca.getText());

        List<TransacaoFinanceira> resultado = repositorio.Buscar(filtro);

        tblBusca.getItems().removeAll(tblBusca.getItems());
        tblBusca.getItems().addAll(resultado);

    }

    @FXML
    public void editar(Event e) {
        setEntidade((TransacaoFinanceira) tblBusca.getSelectionModel().getSelectedItem());
        carregarCampos();
        abas.getTabs().get(1).setDisable(false);
        abas.getSelectionModel().select(1);

    }

    @FXML
    public void novo(Event e) {
        setEntidade(new TransacaoFinanceira());
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
