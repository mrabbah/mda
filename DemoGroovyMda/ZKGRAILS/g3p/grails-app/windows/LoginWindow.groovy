import org.zkoss.zul.*
import org.zkoss.zkplus.databind.*
import org.apache.commons.logging.Log 
import org.apache.commons.logging.LogFactory
import org.zkoss.zk.ui.WrongValueException;

import ma.rcar.g3p.mapping.*

/**
 * Utilisateur Window Object
 **/
class LoginWindow extends Window{
	public void onLogin() {
        Textbox Login = (Textbox) this.getFellow("username");
        Textbox Password = (Textbox) this.getFellow("password");
        try {
            //Users user = usersService.checkUser(Login.getText(), Password.getText());
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

