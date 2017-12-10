package testcase;
import operate.LoginAndRegister;
import operate.OperateCommon;

/**
 * Created by sunyingying on 2016/12/13.
 */
public class Login {
    LoginAndRegister loginAndRegister = new LoginAndRegister();

    public void testLogin(String username, String password){
        loginAndRegister.login(username, password);
    }
}
