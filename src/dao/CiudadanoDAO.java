package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import modelo.Ciudadano;
import modelo.Requisito;
import modelo.SolicitudLicencia;


public class CiudadanoDAO {
   
    // Método que se usará exclusivamente para guardar en la Base de Datos.
    public void registrarCiudadano(Ciudadano ciudadano, SolicitudLicencia solicitud, Requisito requisito) {
       
        // Consultas para cada una de las tablas
        String sqlCiudadano = "INSERT INTO Ciudadano (nombre, curp, telefono) VALUES (?, ?, ?)";
        String sqlSolicitud = "INSERT INTO SolicitudLicencia (id_ciudadano, tipo_licencia, esta_aprobada) VALUES (?, ?, ?)";
        String sqlRequisito = "INSERT INTO Requisito (folio_solicitud, nombre_documento, fue_entregado) VALUES (?, ?, ?)";


        try {
            Connection conexion = ConexionBD.conectar();


            if (conexion != null) {
                // Guardar Ciudadano y obtener su ID
                PreparedStatement pstmtCiudadano = conexion.prepareStatement(sqlCiudadano, java.sql.Statement.RETURN_GENERATED_KEYS);
                pstmtCiudadano.setString(1, ciudadano.getNombre());
                pstmtCiudadano.setString(2, ciudadano.getCurp());
                pstmtCiudadano.setString(3, ciudadano.getTelefono());
                pstmtCiudadano.executeUpdate();


                // Recuperar el ID de Ciudadano generado por la BD
                java.sql.ResultSet rsCiudadano = pstmtCiudadano.getGeneratedKeys();
                int idCiudadanoGenerado = -1;
                if (rsCiudadano.next()) {
                    idCiudadanoGenerado = rsCiudadano.getInt(1);
                }


                // Guardar Solicitud y obtener su ID
                PreparedStatement pstmtSolicitud = conexion.prepareStatement(sqlSolicitud, java.sql.Statement.RETURN_GENERATED_KEYS);
                pstmtSolicitud.setInt(1, idCiudadanoGenerado); // FK
                pstmtSolicitud.setString(2, solicitud.getTipoLicencia());
                pstmtSolicitud.setBoolean(3, solicitud.getEstaAprobada());
                pstmtSolicitud.executeUpdate();


                // Recuperar el folio generado por la BD
                java.sql.ResultSet rsSolicitud = pstmtSolicitud.getGeneratedKeys();
                int folioGenerado = -1;
                if (rsSolicitud.next()) {
                    folioGenerado = rsSolicitud.getInt(1);
                }


                // Guardar Requisito utilizando el folio de la Solicitud
                PreparedStatement pstmtRequisito = conexion.prepareStatement(sqlRequisito);
                pstmtRequisito.setInt(1, folioGenerado); // Fk
                pstmtRequisito.setString(2, requisito.getNombreDocumento());
                pstmtRequisito.setBoolean(3, requisito.getFueEntregado());
                pstmtRequisito.executeUpdate();


                System.out.println("\n🟢 ¡Éxito! Trámite guardado correctamente.");


                pstmtCiudadano.close();
                pstmtRequisito.close();
                pstmtSolicitud.close();
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("🔴 Error al guardar en la Base de Datos: " + e.getMessage());
        }
    }
}
