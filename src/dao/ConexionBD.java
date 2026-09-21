package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    //credenciales
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_tramites";
    private static final String USUARIO = "admin";
    private static final String PASSWORD = "password123";

    //MÉTODO que funciona como puente de comunicación
    public static Connection conectar() {
        Connection conexion = null; 

               try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("\n🟢 ¡Conexión exitosa a la Base de Datos!");
        } catch (SQLException e) {
            System.out.println("\n🔴 Error: No se pudo conectar a la Base de Datos. Verifique que el contenedor este encendido.");
            System.out.println("\nDetalle técnico: " + e.getMessage());
        }


        return conexion;
 }
 }

