package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.controllers.UserController;
import sv.edu.udb.catedradsm.models.UserModel;

public class Login extends AppCompatActivity {
    UserController userController = new UserController(Login.this);
    private EditText txtCorreo, txtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String correo = txtCorreo.getText().toString();
                String pass = txtPassword.getText().toString();

                if(correo != "" && correo.length() != 0 && pass != "" && pass.length() != 0){
                    iniciarSesion(correo, pass);
                }else{
                    Toast.makeText(Login.this, "Ingrese sus credenciales.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void iniciarSesion(String correo, String pass){

        userController.iniciarSesion(correo, pass, new UserController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                Toast.makeText(Login.this, respuesta.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}