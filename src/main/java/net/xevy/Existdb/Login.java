package net.xevy.Existdb;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.base.ResourceSet;

public class Login {
    public static boolean loginExistente(String username, String password) throws XMLDBException {
        if (!username.isEmpty() && !password.isEmpty()) {
            String usu = "admin";
            String usuPwd = "Admin1234";
            String URI = "xmldb:exist://xevy.net:8080/exist/xmlrpc/db";
            Collection col = null;
            
            // coger coleccion
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            if (col == null) {
                System.out.println("No se pudo obtener la colección, verifica la URI y las credenciales.");
                return false;
            }

            XPathQueryService xpqs = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpqs.setProperty("indent", "yes");

            String xQuery = "for $x in doc('db/Pepebestia/Pepebestia.xml')/usuarios/usuario where $x/name/text() = "+ username +" return $x/name/text() return head($results)";

            // Para ejecutar la consulta
            ResourceSet result = xpqs.query(xQuery);

            if (result.getSize() > 0) {
                System.out.println("Usuario encontrado.");
                return true;
            } else {
                System.out.println("Usuario no encontrado.");
                return false;
            }
        } else {
            System.out.println("Usuario o contraseña vacíos.");
            return false;
        }
    }
}