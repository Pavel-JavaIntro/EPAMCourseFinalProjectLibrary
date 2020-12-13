package by.pavka.library.model.dao.impl;

import by.pavka.library.entity.EntityExtractor;
import by.pavka.library.entity.LibraryEntity;
import by.pavka.library.entity.LibraryEntityException;
import by.pavka.library.entity.criteria.Criteria;
import by.pavka.library.entity.criteria.EntityField;
import by.pavka.library.model.dao.DBConnector;
import by.pavka.library.model.dao.DaoException;
import by.pavka.library.model.dao.LibraryDao;
import by.pavka.library.model.util.ColumnFieldMapper;
import by.pavka.library.model.util.TableEntityMapper;
import by.pavka.library.model.util.converter.ConverterFactory;
import by.pavka.library.model.util.converter.FieldColumnConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class implements LibraryDao
 *
 * @param <T>
 * @version 1.0
 * @Author Pavel Kassitchev
 */
public class LibraryDaoImpl<T extends LibraryEntity> implements LibraryDao<T>, EntityExtractor<T> {
  private static final String INSERT = "INSERT INTO %s ";
  private static final String LIST_ALL = "SELECT * FROM ";
  private static final String GET = "SELECT * FROM %s WHERE id=?";
  private static final String UPDATE = "UPDATE %s SET %s WHERE id=?";
  private static final String DELETE = "DELETE FROM %s WHERE id=?";
  public static final String ID = "id";

  private final String tableName;
  private final EntityExtractor<T> entityExtractor;
  private final DBConnector connector;

  public LibraryDaoImpl(TableEntityMapper mapper, DBConnector connector) throws DaoException {
    if (connector == null) {
      throw new DaoException("LibraryDao connector is null");
    }
    this.connector = connector;
    tableName = mapper.getTableName();
    entityExtractor = mapper.getExtractor();
  }

  public DBConnector getConnector() {
    return connector;
  }

  @Override
  public int add(T entity) throws DaoException {
    PreparedStatement statement = null;
    String sql = String.format(INSERT, getTableName());
    try {
      sql += formInsertRequest(entity);
      System.out.println(sql);
      statement = connector.obtainPreparedStatement(sql);
      System.out.println("Before execution: " + statement);
      int affectedRows = statement.executeUpdate();
      System.out.println("After execution: " + affectedRows + " " + statement);
      ResultSet resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        return resultSet.getInt(1);
      } else {
        throw new DaoException("No generated key");
      }
    } catch (DaoException | SQLException e) {
      throw new DaoException("LibraryDao add exception", e);
    } finally {
      connector.closeStatement(statement);
    }
  }

  @Override
  public List<T> read() throws DaoException {
    return list(LIST_ALL + getTableName());
  }

  @Override
  public List<T> read(Criteria criteria, boolean strict) throws DaoException {
    System.out.println(LIST_ALL + getTableName() + interpret(criteria, strict));
    return list(LIST_ALL + getTableName() + interpret(criteria, strict));
  }

  @Override
  public T get(int id) throws DaoException {
    PreparedStatement statement = null;
    ResultSet resultSet;
    String sql = String.format(GET, getTableName());
    try {
      statement = connector.obtainPreparedStatement(sql);
      statement.setInt(1, id);
      resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return formEntity(resultSet);
      } else {
        return null;
      }
    } catch (DaoException | SQLException e) {
      throw new DaoException("SimpleLibraryDao remove excdeption", e);
    } finally {
      connector.closeStatement(statement);
    }
  }

  @Override
  public void update(int id, EntityField<?> field) throws DaoException {
    PreparedStatement statement = null;
    String assignment = ConverterFactory.getInstance().getConverter().formColumnName(field) + "=?";
    String sql = String.format(UPDATE, getTableName(), assignment);
    System.out.println("SQL :" + sql);
    try {
      statement = connector.obtainPreparedStatement(sql);
      statement.setObject(1, field.getValue());
      statement.setInt(2, id);
      System.out.println("INSIDE DAO: " + statement);
      statement.executeUpdate();
    } catch (DaoException | SQLException e) {
      throw new DaoException("SimpleLibraryDao update exception", e);
    } finally {
      connector.closeStatement(statement);
    }
  }

  @Override
  public void remove(int id) throws DaoException {
    PreparedStatement statement = null;
    String sql = String.format(DELETE, getTableName());
    try {
      statement = connector.obtainPreparedStatement(sql);
      statement.setInt(1, id);
      statement.executeUpdate();
    } catch (DaoException | SQLException e) {
      throw new DaoException("SimpleLibraryDao remove excdeption", e);
    } finally {
      connector.closeStatement(statement);
    }
  }

  @Override
  public boolean contains(Criteria criteria, boolean strict) throws DaoException {
    PreparedStatement statement = null;
    ResultSet resultSet;
    String sql = LIST_ALL + getTableName() + interpret(criteria, strict);
    System.out.println("SQL = " + sql);
    try {
      statement = connector.obtainPreparedStatement(sql);
      resultSet = statement.executeQuery();
      return resultSet.next();
    } catch (DaoException | SQLException e) {
      throw new DaoException("SimpleLibraryDao contains exception", e);
    } finally {
      connector.closeStatement(statement);
    }
  }

  @Override
  public void close() {
  }

  private String getTableName() {
    return tableName;
  }

  @Override
  public T extractEntity() {
    return entityExtractor.extractEntity();
  }

  private String interpret(Criteria criteria, boolean strict) {
    if (criteria == null) {
      return "";
    }
    StringBuilder builder = new StringBuilder(" WHERE ");
    FieldColumnConverter converter = ConverterFactory.getInstance().getConverter();
    for (int i = 0; i < criteria.size(); i++) {
      EntityField<?> field = criteria.getConstraint(i);
      builder.append(converter.formColumnName(field));
      if (!strict && field.getValue() instanceof String) {
        builder.append(" LIKE '%").append(field.getValue()).append("%'");
      } else {
        builder.append("='").append(field.getValue()).append("'");
      }
      if (i < criteria.size() - 1) {
        builder.append(" AND ");
      }
    }
    return builder.toString();
  }

  private List<T> list(String sql) throws DaoException {
    List<T> items = new ArrayList<>();
    PreparedStatement statement = null;
    ResultSet resultSet;
    try {
      statement = connector.obtainPreparedStatement(sql);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        T item = formEntity(resultSet);
        items.add(item);
      }
    } catch (DaoException | SQLException e) {
      throw new DaoException("SimpleLibraryDao list exception", e);
    } finally {
      connector.closeStatement(statement);
    }
    return items;
  }

  private T formEntity(ResultSet resultSet) throws SQLException {
    T item = extractEntity();
    int id = resultSet.getInt(ID);
    item.setId(id);
    ResultSetMetaData metaData = resultSet.getMetaData();
    int count = metaData.getColumnCount();
    ColumnFieldMapper<T> mapper = ColumnFieldMapper.getInstance(item);
    for (int i = 2; i <= count; i++) {
      String name = metaData.getColumnName(i);
      String fieldName = mapper.getFieldName(name);
      item.setValue(fieldName, resultSet.getObject(i));
    }
    return item;
  }

  private String formInsertRequest(T entity) {
    StringBuilder template = new StringBuilder("(");
    StringBuilder values = new StringBuilder(" VALUES (");
    ColumnFieldMapper<T> mapper = ColumnFieldMapper.getInstance(entity);
    for (EntityField field : entity.getFields()) {
      if (field.getValue() == null) {
        continue;
      }
      template.append(mapper.getColumnName(field)).append(",");
      values.append("'").append(field.getValue()).append("',");
    }
    template.replace(template.length() - 1, template.length(), ")");
    values.replace(values.length() - 1, values.length(), ")");
    return template.toString() + values.toString();
  }
}
