package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.controllers.CitasController;
import sv.edu.udb.catedradsm.models.CitasModel;

public class VerCita extends AppCompatActivity {
    CitasController controller = new CitasController(VerCita.this);
    private EditText edtNombre;
    private EditText edtSucursal;
    private EditText edtEstilista;
    private EditText edtFecha;
    private EditText edtHora;
    private Button btnAgregar;
    private Button btnCancelar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cita);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtSucursal = (EditText) findViewById(R.id.edtSucursal);
        edtEstilista = (EditText) findViewById(R.id.edtEstilista);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtHora = (EditText) findViewById(R.id.edtHora);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);


        CitasModel cita = new CitasModel(2);
        System.out.println("invocando método del controller ver cita");

        cargarDatos(cita);
    }

    public void cargarDatos(CitasModel cita){
        controller.verCita(cita, new CitasController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(VerCita.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    if(respuesta.toString().length()>0){
                        String id = obj.getString("id");
                        String idcliente = obj.getString("idcliente");
                        String idestilista = obj.getString("idestilista");
                        String idsucursal = obj.getString("idsucursal");
                        String motivo = obj.getString("motivo");
                        String fecha = obj.getString("fecha");
                        String hora = obj.getString("hora");
                        String costototal = obj.getString("costototal");

                        edtNombre.setText(motivo);
                        edtEstilista.setText(idestilista);
                        edtSucursal.setText(idsucursal);
                        edtFecha.setText(fecha);
                        edtHora.setText(hora);


                    }else{
                        Toast.makeText(VerCita.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }

                    /*String mensaje = obj.getString("respuesta");
                    String code = obj.getString("code");

                    if(mensaje.equals("OK")){
                        Toast.makeText(VerCita.this, "Cita agendada con éxito", Toast.LENGTH_SHORT).show();
                        //System.out.println(obj.toString());
                        //redirigir hacia activity principal
                    }else{
                        System.out.println("ERROR");
                    }*/

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}