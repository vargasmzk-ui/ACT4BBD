// Declaración de paquete
package main;

// Importación de herramientas
import java.util.Scanner;
import java.util.ArrayList; // Importamos la herramienta que creará la lista

// Importación de clases de otro paquete
import modelo.Ciudadano;
import modelo.Requisito;
import modelo.SolicitudLicencia;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<SolicitudLicencia> listaSolicitudes = new ArrayList<>();

        boolean salir = false;
        int cFolio = 1000;

        System.out.println("=".repeat(50));
        System.out.println("    VENTANILLA DE TRAMITES CIUDADANOS");
        System.out.println("=".repeat(50));

        while (!salir) {
            System.out.println("\n");
            System.out.println("=".repeat(50));
            System.out.println("    MENÚ PRINCIPAL");
            System.out.println("=".repeat(50));
            System.out.println("[1] Registrar nuevo trámite de licencia.\n[2] Consultar historial de trámites.\n[3] Salir del sistema.\nElija una opción:");

            int op = sc.nextInt();
            sc.nextLine();

            if (op == 1) {
                System.out.println("\n");
                System.out.println("=".repeat(50));
                System.out.println("    [A] Datos del ciudadano.");
                System.out.println("=".repeat(50));
                System.out.println(">> Nombre completo: ");
                String nombre = sc.nextLine();
                System.out.println(">> CURP: ");
                String curp = sc.nextLine();
                System.out.println(">> Teléfono: ");
                String telefono = sc.nextLine();

                Ciudadano ciudadano = new Ciudadano(nombre, curp, telefono);

                System.out.println("\n");
                System.out.println("=".repeat(50));
                System.out.println("    [B] Datos del Trámite.");
                System.out.println("=".repeat(50));
                System.out.println(">> Tipo de licencia:\n[1] Primera Vez.\n[2] Renovación.\nElija una opción:");
                
                int opLicencia = sc.nextInt();
                sc.nextLine();

                String tipoLicencia = "";

                if (opLicencia == 1) {
                    tipoLicencia = "Primera Vez";
                } else if (opLicencia == 2) {
                    tipoLicencia = "Renovación";
                } else {
                    tipoLicencia = "No especificado.";
                    System.out.println("\n>> ¡ADVERTENCIA!: Opción no válida, trámite marcado como NO ESPECIFICADO.");
                }

                Requisito requisito = new Requisito("Identificación Oficial y Comprobante de domicilio.");
                System.out.println("\n>> ¿El Ciudadano entregó todos los documentos?\n>> [1] Sí\n>> [2] NO");
                int entrego = sc.nextInt();

                if (entrego == 1) {
                    requisito.marcarComoEntregado();
                }

                SolicitudLicencia nuevaSolicitud = new SolicitudLicencia(cFolio, tipoLicencia, ciudadano, requisito);
                nuevaSolicitud.procesarSolicitud();

                listaSolicitudes.add(nuevaSolicitud);

                cFolio++;
            
            } else if (op == 2) {
                System.out.println("\n");    
                System.out.println("=".repeat(50));    
                System.out.println("    HISTORIAL DE TRÁMITES");    
                System.out.println("=".repeat(50));    

                if (listaSolicitudes.isEmpty()) {
                    System.out.println(">> Aún no hay ningún trámite registrado en el sistema.");
                } else {
                    for (int i = 0; i < listaSolicitudes.size(); i++) {
                        System.out.println("\nRegistro #" + (i+1) + ":");
                        listaSolicitudes.get(i).consultarEstado();
                    }
                }
            } else if (op == 3) {
                salir = true;
                System.out.println("\nCerrando la ventanilla... ¡Se han guardado " + listaSolicitudes.size() + " trámites hoy!");
            } else {
                System.out.println("\n>> Opción no válida. Por favor, intenta de nuevo.");
            }
        }

        sc.close();
    }
}