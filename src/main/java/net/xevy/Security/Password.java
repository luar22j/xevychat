package net.xevy.Security;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import com.google.common.hash.Hashing;

public class Password {
    String passwd;
    public Password(String passwd){
        this.passwd=secPasswd(passwd);
        
    }

    public String secPasswd(String passwd){
        String salt = crearSalt();
        String hash = "$SHA$"+salt+"$"+Hashing.sha256().hashString(Hashing.sha256().hashString(passwd, StandardCharsets.UTF_8).toString()+salt, StandardCharsets.UTF_8).toString();
        return hash;
    }

    public String getPasswd(){
        return this.passwd;
    }

    public String crearSalt(){
        Random rand = new Random();
        String caracteres = caracteresSalt();
        String salt = "";
        for(int i=0;i<16;i++){
            int numcaracter = rand.nextInt(caracteres.length()-1);
            salt += caracteres.charAt(numcaracter); 
        }
        return salt;
    }

    public String caracteresSalt(){
        String caracteres = "";
        for(int i=48;i!=123;i++){
            caracteres += ((char) i);
            if(i==57){
                i=64;
            }else if(i==90){
                i=97;
            }
        }
        return caracteres;
    }
}
