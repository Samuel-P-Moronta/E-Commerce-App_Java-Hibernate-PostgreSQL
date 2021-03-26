package ormCRUD.services;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {
    private String URL = "jdbc:h2:tcp://localhost/~/myFirstORM9";
    private static ConexionBD conexionBD;
    private static Server tcp;
    private static Server webServer;
    public ConexionBD() throws SQLException {
        registroDriver();
    }

    public static ConexionBD getInstance() throws SQLException {
        if(conexionBD==null){
            conexionBD = new ConexionBD();
        }
        return conexionBD;
    }

    public static void InciarBD() throws SQLException {
        tcp = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();//sube eL modo servidor H2
        String status = Server.createWebServer( "-webPort", "9091", "-webAllowOthers", "-webDaemon").start().getStatus();
        System.out.println("Status Web: "+status);

    }
    public static void detenerBD() throws SQLException {
        tcp.stop();
    }
    private void registroDriver() {
        try {
            Class.forName("org.h2.Driver");
            System.out.println("DRIVER: [org.h2.Driver] registred");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
            System.out.print("CONNECTION...[SUCCESS]");
        } catch (SQLException ex) {
          Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
