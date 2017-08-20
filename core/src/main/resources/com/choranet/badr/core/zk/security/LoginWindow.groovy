package _PACKAGE_

import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory
import org.zkoss.zk.ui.*;


/**
 * Utilisateur Window Object
 **/
class LoginWindow extends Window {
	public void onLogin() {
        Textbox Login = (Textbox) this.getFellow("j_username");
        Textbox Password = (Textbox) this.getFellow("j_password");
        Checkbox cb = (Checkbox) this.getFellow("remember_me");
        //Execution.getS
        //cb.isChecked()
        //def utilisateur = Utilisateur.findByLogin
        try {
            //Ressource
            if (true) {
                Messagebox.show("User OK");
                Label message = (Label) this.getFellow("message");
                message.setVisible(false);
            } else {
                Label message = (Label) this.getFellow("message");
                message.setVisible(true);
                message.setValue("Login et ou mot de passe incorrecte");
            }
        } catch (WrongValueException wrongValueException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, wrongValueException.getMessage());
        } catch (InterruptedException interruptedException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, interruptedException.getMessage());
        } catch(Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
        }
    }
}

