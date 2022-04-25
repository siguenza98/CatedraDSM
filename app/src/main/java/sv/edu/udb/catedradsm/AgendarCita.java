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

public class AgendarCita extends AppCompatActivity {
    CitasController controller = new CitasController(AgendarCita.this);
    private EditText edtMotivo;
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
        setContentView(R.layout.activity_agendar_cita);

        edtMotivo = (EditText) findViewById(R.id.edtMotivo);
        edtSucursal = (EditText) findViewById(R.id.edtSucursal);
        edtEstilista = (EditText) findViewById(R.id.edtEstilista);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtHora = (EditText) findViewById(R.id.edtHora);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        btnAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println("Metodo click");
                String motivo = edtMotivo.getText().toString();
                String sucursal = edtMotivo.getText().toString();
                String estilista = edtMotivo.getText().toString();
                String fecha = edtMotivo.getText().toString();
                String hora = edtMotivo.getText().toString();
                System.out.println("invocando método del controller");
                CitasModel cita = new CitasModel(1,Integer.parseInt(estilista),Integer.parseInt(sucursal),motivo,fecha,hora,10.00);

                guardar(cita);
            }
        });
    }

    public void guardar(CitasModel citas){
        controller.agendarCita(citas, new CitasController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(AgendarCita.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    String mensaje = obj.getString("respuesta");
                    String code = obj.getString("code");

                    if(mensaje.equals("OK")){
                        Toast.makeText(AgendarCita.this, "Cita agendada con éxito", Toast.LENGTH_SHORT).show();
                        //redirigir hacia activity principal
                    }else{
                        System.out.println("ERROR");
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}