package br.com.softdesign;

import org.hamcrest.Matchers;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class HauxTest {

    MetodosDaClasseHaux utilitarios = new MetodosDaClasseHaux();

    @Test
    public void verificarLogin(){
        utilitarios.metodoLogin();
    }

   @Test
    public void registrarUmCliente() {
        utilitarios.metodoRegistraCliente();
    }

    @Test
    public void buscarTodosUsuarios() {
       utilitarios.metodoBuscaTodosClientes();
    }

    @Test
    public void buscarUmCliente() {
       utilitarios.metodoBuscaUmCliente("Luis");
                given()
                .then()
                .body("[0].name", Matchers.is("Luis"))
                ;
    }
}