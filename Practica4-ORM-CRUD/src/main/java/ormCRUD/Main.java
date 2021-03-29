package ormCRUD;

import ormCRUD.controladores.MainController;
import ormCRUD.controladores.ControladorPlantilla;
import ormCRUD.encapsulacion.Controller;
import ormCRUD.services.ConexionBD;
import io.javalin.Javalin;

import java.sql.SQLException;

public class Main {
    private static String modoConexion = "";
    public static void main(String[] args) throws SQLException {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/public"); //desde la carpeta de resources
        }).start(getHerokuAssignedPort());
        new ControladorPlantilla(app);
        new MainController(app).aplicarRutas();
        ConexionBD.getInstance().InciarBD();
        Controller.getInstance().crearDatosPorDefecto();
    }
    public static String getModoConexion() {
        return modoConexion;
    }
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000;
    }

}
