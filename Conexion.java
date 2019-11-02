import java.sql.*;

public class Conexion {

    private Connection con;
    private String url;

    public Conexion() {
        url = "jdbc:ucanaccess://database/Silica.accdb";
    }

    public void conectar() throws SQLException {
        con = DriverManager.getConnection(url);
    }

    public Connection getConexion() {
        return this.con;
    }

    public void desconectar() throws SQLException {
        con.close();
    }
}