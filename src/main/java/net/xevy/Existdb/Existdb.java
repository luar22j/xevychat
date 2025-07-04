package net.xevy.Existdb;

import net.xevy.perfil.*;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.exist.xmldb.DatabaseImpl;
import org.exist.xmldb.EXistResource;

public class Existdb {
    public static void conneccionExistdb(String usuario, String Password, String email) {
        System.out.println("cargando ExistDb...");

        final String driver = "org.exist.xmldb.DatabaseImpl"; // driver
        Collection col = null;
        // esto lo ajustais
        String usu = "admin";
        String usuPwd = "Admin1234";
        String URI = "xmldb:exist://xevy.net:8080/exist/xmlrpc/db";

        try {
            // inicializar la base de datos
            Class clase = Class.forName(driver);
            Database database = (Database) clase.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);

            // coger coleccion
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            if (col == null) {
                System.out.println("No se pudo obtener la colección, verifica la URI y las credenciales.");
                return;
            }

            System.out.println("¡ALFIIIIIN!Conexión exitosa a la colección.");

            // Crear la coleccion si no exite
            CollectionManagementService servicio = (CollectionManagementService) col
                    .getService("CollectionManagementService", "1.0");
            servicio.createCollection("Pepebestia");

            // reobtener la coleccion para ver si es nula o no
            col = DatabaseManager.getCollection(URI + "/Pepebestia", usu, usuPwd);
            if (col == null) {
                System.out.println("No se pudo obtener la colección Pepebestia.");
                return;
            }
            // Crear un xml, ahora si res = null coleccion nueva
            String resourceId = "Pepebestia.xml";
            XMLResource res = (XMLResource) col.getResource(resourceId);
            if (res == null) {
                String xmlContent = "<usuarios></usuarios>";
                res = (XMLResource) col.createResource(resourceId, "XMLResource");
                res.setContent(xmlContent);
                col.storeResource(res);
                System.out.println("Recurso XML creado y almacenado correctamente ¡PEPEBESTIAL!.");
            } else {
                System.out.println("Recurso XML ya existe");
            }
            // añadir xml
            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");

            String sQuery = "update insert <usuario><name>" + usuario
                    + "</name><password>" + Password + "</password><email>"
                    + email + "</email></usuario> into /usuarios[last()]";

            xpqs.setProperty("indent", "yes");
            xpqs.query(sQuery);

            System.out.println("Consulta ejecutada correctamente.");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de la base de datos.");
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Error al instanciar la base de datos.");
            e.printStackTrace();
        } catch (XMLDBException e) {
            System.out.println("Error al interactuar con la base de datos.");
            e.printStackTrace();
        }
    }

    public static void loginExistenteExist(String username, String password) {
        System.out.println("Cargando ExistDb...\n Comprovadon usuario");
        // INICIALIZAMOS VARIABLES
        final String driver = "org.exist.xmldb.DatabaseImpl"; // driver
        Collection col = null;
        // esto lo ajustais
        String usu = "admin";
        String usuPwd = "Admin1234";
        String URI = "xmldb:exist://xevy.net:8080/exist/xmlrpc/db";

        try {
            // inicializar la base de datos cargar el driver
            Class clase = Class.forName(driver);
            Database database = (Database) clase.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);

            // cojer coleccion si la coleccion esta
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            if (col == null) {
                System.out.println("No se pudo obtener la colección, verifica la URI y las credenciales.");
                return;
            }

            System.out.println("¡ALFIIIIIN!Conexión exitosa a la colección.");

            // cojer y crear la coleccion la coleccion si no exite
            CollectionManagementService servicio = (CollectionManagementService) col
                    .getService("CollectionManagementService", "1.0");
            servicio.createCollection("Pepebestia");

            // reobtener la coleccion para ver si es nula o no
            col = DatabaseManager.getCollection(URI + "/Pepebestia", usu, usuPwd);
            if (col == null) {
                System.out.println("No se pudo obtener la colección Pepebestia.");
                return;
            }
            // Crear un xml, ahora si res = null coleccion nueva
            String resourceId = "Pepebestia.xml";
            XMLResource res = (XMLResource) col.getResource(resourceId);
            if (res == null) {
                String xmlContent = "<usuarios></usuarios>";
                res = (XMLResource) col.createResource(resourceId, "XMLResource");
                res.setContent(xmlContent);
                col.storeResource(res);
                System.out.println("Recurso XML creado y almacenado correctamente ¡PEPEBESTIAL!.");
            } else {
                System.out.println("Recurso XML ya existe");
            }
            // añadir xml
            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");
            String xQuery = "for $x in doc('/db/Pepebestia/Pepebestia.xml')/usuarios/usuario where $x/name/text() = '"
                    + username
                    + "' return  concat($x/name/text(), ',', $x/password/text())";

            if (!username.isEmpty()) {
                try {

                    ResourceSet result = xpqs.query(xQuery);
                    ResourceIterator i = result.getIterator();
                    Resource resultadosVar = null;

                    // Iterar sobre los resultados
                    while (i.hasMoreResources()) {
                        try {
                            resultadosVar = i.nextResource();
                            System.out.println(resultadosVar.getContent());
                        } finally {
                            try {
                                if (resultadosVar != null) {
                                    ((EXistResource) resultadosVar).freeResources();
                                }
                            } catch (Exception xe) {
                                xe.printStackTrace();
                            }
                        }
                    }
                } catch (XMLDBException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("El nombre de usuario o la contraseña están vacíos.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de la base de datos.");
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Error al instanciar la base de datos.");
            e.printStackTrace();
        } catch (XMLDBException e) {
            System.out.println("Error al interactuar con la base de datos.");
            e.printStackTrace();
        }
        System.out.println("Consulta ejecutada correctamente.");
        return;
    }

}