package cruds.receita;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReceitaControl {

  private ObservableList<Receita> lista = FXCollections.observableArrayList();
  private IntegerProperty id = new SimpleIntegerProperty(0);
  private ObjectProperty<LocalDate> dataReceita = new SimpleObjectProperty<>(LocalDate.now());
  private StringProperty medicoCRM = new SimpleStringProperty("");
  private StringProperty medicamentos = new SimpleStringProperty("");

  private int contador = 0;

  private ReceitaDAO receitaDAO;

  public ReceitaControl() throws ReceitaException {
    receitaDAO = new ReceitaDAOImpl();
  }

  public void entidadeParaTela(Receita r) {
    if (r != null) {
      this.id.set(r.getId());
      this.dataReceita.set(r.getDataReceita());
      this.medicamentos.set(r.getMedicamentos());
      this.medicoCRM.set(r.getMedicoCRM());
    }
  }

  public void excluir(Receita r) {
    System.out.println("Excluindo a receita de id: " + r.getId());
    lista.remove(r);
  }

  public void gravar() throws ReceitaException {
    Receita r = new Receita();
    r.setDataReceita(this.dataReceita.get());
    r.setMedicoCRM(this.medicoCRM.get());
    // Separa os medicamentos por ,
    String[] medicamentos = this.medicamentos.get().split(",");
    StringBuilder stb = new StringBuilder();
    for (int i = 0; i < medicamentos.length; i++) {
      if (i != medicamentos.length - 1) {
        stb.append(medicamentos[i]);
        stb.append(",");
      } else {
        stb.append(medicamentos[i]);
      }
    }
    r.setMedicamentos(stb.toString());

    // id = 0 sempre que for um novo registro
    if (id.get() == 0) {
      // muda o valor do id para estar de acordo com o contador
      contador += 1;
      r.setId(contador);
      System.out.println(r.getId());
      receitaDAO.inserir(r);
    } else {
      // Devido a assoativa ter como chave o id de estoque, não é possivel atualizar
      // os medicamentos, informar o usuário desta regra
      r.setId(this.id.get());
      receitaDAO.atualizar(r);
    }
    pesquisarTodos();
    limparTudo();
    System.out.println("Lista tamanho: " + lista.size());
  }

  public void limparTudo() {
    id.set(0);
    dataReceita.set(LocalDate.now());
    medicamentos.set("");
    medicoCRM.set("");
  }

  public void pesquisarPorCrm() throws ReceitaException {
    lista.clear();
    lista.addAll(receitaDAO.pesquisarPorCRM(medicoCRM.get()));
  }

  public void pesquisarTodos() throws ReceitaException {
    lista.clear();
    lista.addAll(receitaDAO.pesquisarTodos());
  }

  public boolean verificaMedicamentos() throws ReceitaException {
    String[] arrayMed = this.medicamentos.getValue().split(",");
    List<String> meds = new ArrayList<>();
    for (String item : arrayMed) {
      meds.add(item.strip());
    }
    return receitaDAO.pesquisarMedicamento(meds);
  }

  public ObservableList<Receita> getLista() {
    return lista;
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public ObjectProperty<LocalDate> dataReceitaProperty() {
    return dataReceita;
  }

  public StringProperty medicamentosProperty() {
    return medicamentos;
  }

  public StringProperty medicoCRMProperty() {
    return medicoCRM;
  }

}
