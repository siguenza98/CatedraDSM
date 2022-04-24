package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import sv.edu.udb.catedradsm.controllers.UserController;

public class Login extends AppCompatActivity {
    UserController userController = new UserController(Login.this);
    private EditText txtCorreo, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

    }

    public void iniciarSesion(){
        String correo = txtCorreo.getText().toString();
        String pass = txtPassword.getText().toString();

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