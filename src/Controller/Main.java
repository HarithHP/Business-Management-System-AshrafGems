
package Controller;



import View.AgentMainForm;
import View.ClientMainForm;
import View.OwnerMainForm;
import View.SignInUp;
import View.SupplierMainForm;

public class Main {

    public static void main(String[] args) {
        //AgentMainForm main = new AgentMainForm("a1");
        //SupplierMainForm main = new SupplierMainForm("s2");
        //ClientMainForm main = new ClientMainForm("c2");
       // OwnerMainForm main = new OwnerMainForm(1);
        SignInUp main = new SignInUp();
        main.setVisible(true);

    }
}
