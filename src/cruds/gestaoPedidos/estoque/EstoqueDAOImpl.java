package cruds.gestaoPedidos.estoque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cruds.receita.ReceitaControl;

public class EstoqueDAOImpl implements EstoqueDAO {

  private static final String DB_CLASS = "org.mariadb.jdbc.Driver";
  private static final String DB_URL = "jdbc:mariadb://localhost:3306/hospitaldb?allowPublicKeyRetrieval=true";
  private static final String DB_USER = "root";
  private static final String DB_PASS = "123456";
  private Connection con = null;

  public EstoqueDAOImpl() throws EstoqueException {
    try {
      Class.forName(DB_CLASS);
      con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      throw new EstoqueException(e);
    }
  }

  @Override
  public void inserir(Estoque e) throws EstoqueException {
    try {
      String SQL = """
          INSERT INTO estoque (id, medicamento, quantidade, fornecedor, funcionarioRegistro)
          VALUES (?, ?, ?, ?, ?)
          """;
      PreparedStatement stm = con.prepareStatement(SQL);
      stm.setInt(1, 0);
      stm.setString(2, e.getMedicamento());
      stm.setInt(3, e.getQuantidade());
      stm.setString(4, e.getFornecedor());
      stm.setInt(5, e.getFuncionarioRegistro());
      int i = stm.executeUpdate();
      System.out.println(i);
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
  }

  @Override
  public void atualizar(Estoque e) throws EstoqueException {
    try {
      String SQL = """
          UPDATE estoque SET medicamento=?, quantidade=?, fornecedor=?,funcionarioRegistro=?
          WHERE id=?
          """;
      PreparedStatement stm = con.prepareStatement(SQL);
      stm.setString(1, e.getMedicamento());
      stm.setInt(2, e.getQuantidade());
      stm.setString(3, e.getFornecedor());
      stm.setInt(4, e.getFuncionarioRegistro());
      stm.setInt(5, e.getId());
      int i = stm.executeUpdate();
      System.out.println(i);
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
  }

  @Override
  public void remover(Estoque e) throws EstoqueException {
    try {
      // verifica se medicamento esta em uma receita
      removeReceitaComMed(e);
      String SQL = """
              DELETE FROM estoque WHERE id=?
          """;
      PreparedStatement stm = con.prepareStatement(SQL);
      stm.setInt(1, e.getId());
      int i = stm.executeUpdate();
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
  }

  private void removeReceitaComMed(Estoque e) throws EstoqueException {

    try {
      List<Integer> ids = buscaIdReceita(e);
      // busca e verifica se o medicamento aparece em alguma receita
      if (!ids.isEmpty()) {
        // se sim, remove a associativa receita e estoque
        removerDaAssociativa(e);
        // e remove a receita
        for (Integer id : ids) {
          // pra cada id de receita da lista de ids, executa o delete
          String SQL = """
              DELETE FROM receita
              WHERE id=?
              """;
          PreparedStatement stm = con.prepareStatement(SQL);
          stm.setInt(1, id);
          int i = stm.executeUpdate();
        }
      }
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
  }

  private List<Integer> buscaIdReceita(Estoque e) throws EstoqueException {
    List<Integer> lista = new ArrayList<>();
    try {
      String SQL = """
            SELECT receitaId FROM receitaEstoque
            WHERE estoqueId=?
          """;
      PreparedStatement stm = con.prepareStatement(SQL);
      stm.setInt(1, e.getId());
      // Lista com o resultado da query
      ResultSet rs = stm.executeQuery();
      // Enquanto existir elementos na lista continua o while
      while (rs.next()) {
        Integer i = rs.getInt("receitaId");
        lista.add(i);
      }
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
    return lista;
  }

  private void removerDaAssociativa(Estoque e) throws EstoqueException {

    try {
      String SQL = """
              DELETE FROM receitaEstoque WHERE estoqueId=?
          """;
      PreparedStatement stm = con.prepareStatement(SQL);
      stm.setInt(1, e.getId());
      int i = stm.executeUpdate();
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
  }

  @Override
  public List<Estoque> pesquisarPorMedicamento(String medicamento) throws EstoqueException {
    List<Estoque> lista = new ArrayList<>();
    try {
      String SQL = """
            SELECT * FROM estoque WHERE medicamento LIKE ?
          """;
      PreparedStatement stm = con.prepareStatement(SQL);
      stm.setString(1, "%" + medicamento + "%");
      // Lista com o resultado da query
      ResultSet rs = stm.executeQuery();
      // Enquanto existir elementos na lista continua o while
      while (rs.next()) {
        Estoque e = new Estoque();
        e.setId(rs.getInt("id"));
        e.setMedicamento(rs.getString("medicamento"));
        e.setQuantidade(rs.getInt("quantidade"));
        e.setFornecedor(rs.getString("fornecedor"));
        e.setFuncionarioRegistro(rs.getInt("funcionarioRegistro"));
        lista.add(e);
      }
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
    return lista;
  }

  @Override
  public List<Estoque> pesquisarTodos() throws EstoqueException {
    List<Estoque> lista = new ArrayList<>();
    try {
      String SQL = """
          SELECT * FROM estoque
          """;
      PreparedStatement stm = con.prepareStatement(SQL);
      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        Estoque e = new Estoque();
        e.setId(rs.getInt("id"));
        e.setMedicamento(rs.getString("medicamento"));
        e.setQuantidade(rs.getInt("quantidade"));
        e.setFornecedor(rs.getString("fornecedor"));
        e.setFuncionarioRegistro(rs.getInt("funcionarioRegistro"));
        lista.add(e);
      }
    } catch (SQLException er) {
      er.printStackTrace();
      throw new EstoqueException(er);
    }
    return lista;
  }

}
