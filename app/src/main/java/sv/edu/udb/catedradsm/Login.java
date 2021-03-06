package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import sv.edu.udb.catedradsm.controllers.UserController;
import sv.edu.udb.catedradsm.models.UserModel;

public class Login extends AppCompatActivity {
    UserController userController = new UserController(Login.this);
    private EditText txtCorreo, txtPassword;
    private TextView txtCrearCuenta;
    private Button btnLogin;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtCrearCuenta = (TextView)findViewById(R.id.txtCrearCuenta);
        btnLogin = (Button)findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String correo = txtCorreo.getText().toString();
                String pass = txtPassword.getText().toString();

                if(correo != "" && correo.length() != 0 && pass != "" && pass.length() != 0){
                    iniciarSesion(correo, pass);
                    verificarLogin();
                }else{
                    Toast.makeText(Login.this, "Ingrese sus credenciales.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtCrearCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(Login.this, "CLICK", Toast.LENGTH_SHORT).show();

               //REDIRECCIONAR A CREAR CUENTA
                Intent i = new Intent(getBaseContext(), crearCuenta.class);

                startActivity(i);
            }
        });

        sharedpreferences = getSharedPreferences("usuario", 0);
        verificarLogin();
    }

    public void verificarLogin(){
        String correo = sharedpreferences.getString("correo", null); // getting
        String userId = sharedpreferences.getString("userId", null); // getting
        //Toast.makeText(Login.this, correo, Toast.LENGTH_SHORT).show();


        if(correo != null){
<<<<<<< HEAD
<<<<<<< HEAD
            Toast.makeText(Login.this, "logueado", Toast.LENGTH_SHORT).show();
=======
            Toast.makeText(Login.this, "Ha iniciado sesi??n.", Toast.LENGTH_SHORT).show();
>>>>>>> origin/Daniel
=======
            Toast.makeText(Login.this, "Ha iniciado sesi??n.", Toast.LENGTH_SHORT).show();
>>>>>>> 96e67c62cf57c9a853910334d79c416ae3a80264
            //REDIRECCIONAR A VISTA PRINCIPAL
            Intent i = new Intent(getBaseContext(), vista_inicio.class);
            i.putExtra("userId",userId);
            startActivity(i);
        }


    };
    public void iniciarSesion(String correo, String pass){

        userController.iniciarSesion(correo, pass, new UserController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try {
                    JSONObject obj = new JSONObject(respuesta.toString());

                    String mensaje = obj.getString("respuesta");
                    String code = obj.getString("code");

                    if(mensaje.equals("OK")){

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        JSONObject userData = obj.getJSONObject("user");

                        String userId = userData.getString("id");
                        editor.putString("userId",userId);

                        String userCorreo = userData.getString("correo");
                        editor.putString("correo", userCorreo);

                        String userNombre = userData.getString("nombre");
                        editor.putString("nombre", userNombre);

                        String userApellido = userData.getString("apellido");
                        editor.putString("apellido", userApellido);

                        String userTelefono = userData.getString("telefono");
                        editor.putString("telefono", userTelefono);
                        editor.apply();
                    }else{
                        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}