package cruds.receita;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cruds.gestaoPedidos.estoque.Estoque;

public class Receita {
  private int id;
  private LocalDate dataReceita;
  // receita vai ter uma lista de medicamentos
  private String medicamentos;
  private String medicoCRM;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDate getDataReceita() {
    return dataReceita;
  }

  public void setDataReceita(LocalDate dataReceita) {
    this.dataReceita = dataReceita;
  }

  public String getMedicamentos() {
    return medicamentos;
  }

  public void setMedicamentos(String medicamentos) {
    this.medicamentos = medicamentos;
  }

  public String getMedicoCRM() {
    return medicoCRM;
  }

  public void setMedicoCRM(String medicoCRM) {
    this.medicoCRM = medicoCRM;
  }

}
