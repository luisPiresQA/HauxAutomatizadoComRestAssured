package br.com.softdesign;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MetodosDaClasseHaux {

    private final String url = "localhost:8080/";
    private final String pathLogin = "/login";
    private final String pathBuscaUmCliente = "client";
    private String token;
    private org.json.JSONObject corpoParaIncluir = new org.json.JSONObject();
    private org.json.JSONObject conteudoCorpoCliente = new org.json.JSONObject();
    private JSONObject params = new JSONObject();
    private String nome;
    private int indice;
    private final String senha = "ss12345678910pas";
    private String usuariosSistema[]={"cartolas","sabia","teodoro","Julia","capacete","piscina"};


    @Test
    public void metodoLogin() {
        Random aleatorio = new Random();
        indice = aleatorio.nextInt(5) + 0;

        params.put("username", usuariosSistema[indice]);
        params.put("password", senha);
        token = given()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post(url + pathLogin)
                .then()
                .statusCode(HttpStatus.SC_OK)//status
                .body("auth", equalTo(true))
                .extract().path("token");
    }

    private Object metodoPrepraCorpoIncluiCliente() {
        return corpoParaIncluir = new org.json.JSONObject()
                .put("newClient",
                        new org.json.JSONObject()
                                .put("name", "testenome")
                                .put("birthDate", "15/03/1998")
                                .put("biologicalSex", "M")
                                .put("cpf", geradorCPF())
                                .put("phone", "5133197216")
                                .put("email", "user@example.com")
                                .put("sourceChannel", "Site")
                                .put("address",
                                        new org.json.JSONObject()
                                                .put("cep", "91870200")
                                                .put("complement", "casa")
                                                .put("state", "RS")
                                                .put("city", "Porto Algre")
                                                .put("street", "casa")
                                                .put("number", "34")
                                                .put("district", "Centro"))

                                .put("fraschises",
                                        new JSONArray()
                                                .put(new org.json.JSONObject()
                                                        .put("_id", "34567890")
                                                        .put("name", "Caxias do Sul")
                                                        .put("slug", "caxias")
                                                        .put("city", "Caxias do Sul")))
                                .put("sendPassword", true));
    }

    public void metodoRegistraCliente() {
        metodoPrepraCorpoIncluiCliente();
        given()
                .when().header("Authorization", "Bearer " + getTOKEN())
                .contentType(ContentType.JSON)
                .body(corpoParaIncluir.toString()).log().all()
                .post(url )
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .body("inserted", is(true));
    }

    public void metodoBuscaTodosClientes() {
                 given()
                .contentType(ContentType.JSON)
                .when().header("Autor", "AlgumTipo " + getTOKEN())
                .get(url)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("[0].clientId", is(notNullValue()));
    }

    public String metodoBuscaUmCliente(String nome) {
        return nome = given()
                .when().header("Authorization", "Bearer " + getTOKEN())
                .param("filter", nome)
                .param("franchise", 1)
                .get(url + pathBuscaUmCliente)
                .then().statusCode(HttpStatus.SC_OK)
                .log()
                .all()
                .extract().path("[0].name");
    }

    private String getTOKEN() {
        metodoLogin();
        return this.token;
    }

    private String geradorCPF() {
        int d1 = 0, d2 = 0, rest = 0;
        String nDigResult;
        String concatNumber;
        String generatedNumber;
        Random randomNumber = new Random();
        int n1 = randomNumber.nextInt(10);
        int n2 = randomNumber.nextInt(10);
        int n3 = randomNumber.nextInt(10);
        int n4 = randomNumber.nextInt(10);
        int n5 = randomNumber.nextInt(10);
        int n6 = randomNumber.nextInt(10);
        int n7 = randomNumber.nextInt(10);
        int n8 = randomNumber.nextInt(10);
        int n9 = randomNumber.nextInt(10);
        int sum = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;
        int value = (sum / 11) * 11;
        d1 = sum - value;
        rest = (d1 % 11);
        if (d1 < 2) {
            d1 = 0;
        } else {
            d1 = 11 - rest;
        }
        int sum2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;
        int value2 = (sum2 / 11) * 11;
        d2 = sum2 - value2;
        rest = (d2 % 11);
        if (d2 < 2) {
            d2 = 0;
        } else {
            d2 = 11 - rest;
        }
        concatNumber = String.valueOf(n1) + String.valueOf(n2) + String.valueOf(n3) + n4 + n5 + n6 + n7 + n8 + n9;
        nDigResult = String.valueOf(d1) + String.valueOf(d2);
        generatedNumber = concatNumber + nDigResult;
        return generatedNumber;
    }
}
