package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import sv.edu.udb.catedradsm.controllers.UserController;

public class MainActivity extends AppCompatActivity {

    UserController userController = new UserController(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /*
        userController.realizarPeticion(new UserController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                Toast.makeText(MainActivity.this, respuesta.toString(), Toast.LENGTH_LONG).show();
            }
        });

     */
    }
}