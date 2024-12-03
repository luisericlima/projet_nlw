package br.com.devluis.certification_nlw.seed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CreateSeed { //CLASSE PARA INSERCAO DE DADOS VIA QUERY DO BANCO DE DADOS

  private final JdbcTemplate jdbcTemplate;

  public CreateSeed(DataSource dataSource){
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public static void main(String[] args) { //DEFININDO OS DADOS DA DATABASE
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5434/pg_nlw");
    dataSource.setUsername("admin");
    dataSource.setPassword("admin");

    CreateSeed createSeed = new CreateSeed(dataSource);
    createSeed.run(args);
  }

  public void run(String...args){ //DECLARANDO ONDE ESTA LOCALIZADA  QUERY
    executeSqlFile("src/main/resources/create.sql");
  }

  private void executeSqlFile(String filePath){ //METODO PARA EXECUTAR A QUERY CUJO ENDERECO ESTA NO METODO RUN
    try{
    String sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));
    jdbcTemplate.execute(sqlScript);
    System.out.println("Seed realizado com SUCESSO");
  }
  catch(IOException e){
    System.out.println("Erro ao executar arquivo " + e.getMessage());
    }
  }

}
