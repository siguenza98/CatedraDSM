package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import sv.edu.udb.catedradsm.controllers.CitasController;
import sv.edu.udb.catedradsm.controllers.SucursalController;
import sv.edu.udb.catedradsm.controllers.EstilistaController;
import sv.edu.udb.catedradsm.controllers.ServicioController;
import sv.edu.udb.catedradsm.models.CitasModel;
import sv.edu.udb.catedradsm.models.SucursalModel;
import sv.edu.udb.catedradsm.models.EstilistaModel;
import sv.edu.udb.catedradsm.models.ServicioModel;

public class AgendarCita extends AppCompatActivity {
    CitasController controller = new CitasController(AgendarCita.this);
    SucursalController sucursalController = new SucursalController(AgendarCita.this);
    EstilistaController estilistaController = new EstilistaController(AgendarCita.this);
    ServicioController servicioController = new ServicioController(AgendarCita.this);
    List<SucursalModel> listaSucursales;
    List<EstilistaModel> listaEstilistas;
    List<ServicioModel> listaServicios;
    private EditText edtMotivo;
    private Spinner spMotivo;
    private EditText edtSucursal;
    private Spinner spSucursal;
    private EditText edtEstilista;
    private Spinner spEstilista;
    private EditText edtFecha;
    private EditText edtHora;
    private Button btnAgregar;
    private Button btnCancelar;
    private String userId = "";
    private double precio;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        userId = datos.getString("userId");

        edtMotivo = (EditText) findViewById(R.id.edtMotivo);
        spMotivo = (Spinner) findViewById(R.id.spMotivo);
        edtSucursal = (EditText) findViewById(R.id.edtSucursal);
        spSucursal = (Spinner) findViewById(R.id.spSucursal);
        edtEstilista = (EditText) findViewById(R.id.edtEstilista);
        spEstilista = (Spinner) findViewById(R.id.spEstilista);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtHora = (EditText) findViewById(R.id.edtHora);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        cargarDatosSucursales(new SucursalModel());
        cargarDatosServicios();

        spSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SucursalModel sucursal = (SucursalModel) spSucursal.getSelectedItem();

                cargarDatosEstilistas(sucursal.getId());

                edtSucursal.setText(String.valueOf(sucursal.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spEstilista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EstilistaModel estilista = (EstilistaModel) spEstilista.getSelectedItem();
                edtEstilista.setText(String.valueOf(estilista.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spMotivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ServicioModel servicio = (ServicioModel) spMotivo.getSelectedItem();
                edtMotivo.setText(String.valueOf(servicio.getServicio()));
                precio = servicio.getPrecio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String motivo = edtMotivo.getText().toString();
                String sucursal = edtSucursal.getText().toString();
                String estilista = edtEstilista.getText().toString();
                String fecha = edtFecha.getText().toString();
                String hora = edtHora.getText().toString();
                CitasModel cita = new CitasModel(Integer.parseInt(userId),Integer.parseInt(estilista),Integer.parseInt(sucursal),motivo,fecha,hora,precio);
                guardar(cita);

                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
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

    public void cargarDatosSucursales(SucursalModel sucursal){
        sucursalController.obtenerSucursales(sucursal, new SucursalController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(AgendarCita.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    String mensaje = obj.getString("respuesta");

                    if(mensaje.equals("OK")){
                        JSONArray jsonArray = obj.getJSONArray("sucursales");

                        listaSucursales = new ArrayList<SucursalModel>();
                        SucursalModel sucursal;

                        for(int i = 0; i<jsonArray.length(); i++){
                            sucursal = new SucursalModel();
                            JSONObject item = (JSONObject)jsonArray.get(i);
                            sucursal.setId(Integer.parseInt(item.getString("id")));
                            sucursal.setNombre(item.getString("nombre"));
                            sucursal.setTelefono(item.getString("telcontacto"));
                            sucursal.setDireccion(item.getString("direccion"));

                            listaSucursales.add(sucursal);
                        }

                        llenarListaSucursales();
                    }else{
                        Toast.makeText(AgendarCita.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void cargarDatosEstilistas(int idSucursal){
        estilistaController.obtenerEstilistas(idSucursal, new EstilistaController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(AgendarCita.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    String mensaje = obj.getString("respuesta");
                    System.out.println("respuesta estilistas: "+obj.toString());
                    if(mensaje.equals("OK")){
                        JSONArray jsonArray = obj.getJSONArray("estilistas");

                        System.out.println("array: "+jsonArray.toString());

                        listaEstilistas = new ArrayList<EstilistaModel>();
                        EstilistaModel estilista;

                        for(int i = 0; i<jsonArray.length(); i++){
                            estilista = new EstilistaModel();
                            JSONObject item = (JSONObject)jsonArray.get(i);
                            estilista.setId(Integer.parseInt(item.getString("id")));
                            estilista.setIdSucursal(Integer.parseInt(item.getString("idsucursal")));
                            estilista.setNombre(item.getString("nombre"));
                            estilista.setApellido(item.getString("apellido"));
                            estilista.setTelefono(item.getString("telefono"));
                            estilista.setCorreo(item.getString("correo"));

                            listaEstilistas.add(estilista);
                        }

                        llenarListaEstilistas();
                    }else{
                        Toast.makeText(AgendarCita.this, "Vista, Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void cargarDatosServicios(){
        servicioController.obtenerServicios(new SucursalController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(AgendarCita.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    String mensaje = obj.getString("respuesta");

                    if(mensaje.equals("OK")){
                        JSONArray jsonArray = obj.getJSONArray("servicios");

                        listaServicios = new ArrayList<ServicioModel>();
                        ServicioModel servicio;

                        for(int i = 0; i<jsonArray.length(); i++){
                            servicio = new ServicioModel();
                            JSONObject item = (JSONObject)jsonArray.get(i);
                            servicio.setId(Integer.parseInt(item.getString("id")));
                            servicio.setServicio(item.getString("servicio"));
                            servicio.setPrecio(Double.parseDouble(item.getString("precio")));

                            listaServicios.add(servicio);
                        }

                        llenarListaServicios();
                    }else{
                        Toast.makeText(AgendarCita.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void llenarListaSucursales(){
        ArrayAdapter<SucursalModel> adapter = new ArrayAdapter<SucursalModel>(this,
                android.R.layout.simple_spinner_item, listaSucursales);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSucursal.setAdapter(adapter);
    }

    private void llenarListaEstilistas(){
        ArrayAdapter<EstilistaModel> adapter = new ArrayAdapter<EstilistaModel>(this,
                android.R.layout.simple_spinner_item, listaEstilistas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEstilista.setAdapter(adapter);
    }

    private void llenarListaServicios(){
        ArrayAdapter<ServicioModel> adapter = new ArrayAdapter<ServicioModel>(this,
                android.R.layout.simple_spinner_item, listaServicios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMotivo.setAdapter(adapter);
    }
}