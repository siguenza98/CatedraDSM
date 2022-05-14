package sv.edu.udb.catedradsm.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.models.CitasModel;

import sv.edu.udb.catedradsm.models.ConexionModel;

public class CitasController {
    String url = "";
    Context context;
    String respuesta = "";

    public CitasController(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String mensaje);
        void onResponse(Object respuesta);
    }

    public void agendarCita(CitasModel cita, VolleyResponseListener volleyResponseListener){
        url = "https://udbdsmapi.000webhostapp.com/api/cita/create";
        JSONObject datos = new JSONObject();

        try{
            datos.put("idcliente",cita.getIdCliente());
            datos.put("idestilista",cita.getIdEstilista());
            datos.put("idsucursal",cita.getIdSucursal());
            datos.put("motivo",cita.getMotivo());
            datos.put("fecha",cita.getFecha());
            datos.put("hora",cita.getHora());
            datos.put("costototal",cita.getCosto());
<<<<<<< HEAD
            datos.put("estado",1);

            System.out.println("metodo agendar desde el controller");
=======
>>>>>>> origin/Daniel

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datos,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
<<<<<<< HEAD
                        System.out.println("exito: "+response);
                        volleyResponseListener.onResponse(response);
                    }catch(Exception e){
                        System.out.println("excepcion: "+e.toString());
=======
                        volleyResponseListener.onResponse(response);
                    }catch(Exception e){
>>>>>>> origin/Daniel
                        Toast.makeText(context, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
<<<<<<< HEAD
                    System.out.println("responseerror: "+error.toString());
=======
>>>>>>> origin/Daniel
                    volleyResponseListener.onError("Hubo un error al realizar la petición.");
                }

            });
            ConexionModel.getInstance(context).addToRequestQueue(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void verCita(CitasModel cita, VolleyResponseListener volleyResponseListener){
        url = "https://udbdsmapi.000webhostapp.com/api/cita/getCitaById";

        JSONObject datos = new JSONObject();
        try{

            datos.put("idcita",cita.getId());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datos,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        volleyResponseListener.onResponse(response);
                    }catch(Exception e){
                        System.out.println("catch del response controlador");
                        Toast.makeText(context, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("metodo errorresponse controlador");
                    System.out.println("error: "+error.toString());
                    volleyResponseListener.onError("Hubo un error al realizar la petición.");
                }

            });
            ConexionModel.getInstance(context).addToRequestQueue(request);
        }catch(JSONException e){
            e.printStackTrace();
        }



    }

    public void historialCitas(CitasModel cita, VolleyResponseListener volleyResponseListener){
        url = "https://udbdsmapi.000webhostapp.com/api/cita/getCitasByUserId";

        JSONObject datos = new JSONObject();
        try{

            datos.put("userId",cita.getIdCliente());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datos,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        volleyResponseListener.onResponse(response);
                    }catch(Exception e){
                        Toast.makeText(context, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyResponseListener.onError("Hubo un error al realizar la petición.");
                }

            });
            ConexionModel.getInstance(context).addToRequestQueue(request);
        }catch(JSONException e){
            e.printStackTrace();
        }



    }
}
