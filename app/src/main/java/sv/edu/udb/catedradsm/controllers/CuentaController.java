package sv.edu.udb.catedradsm.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

import sv.edu.udb.catedradsm.models.CuentaModel;

import sv.edu.udb.catedradsm.models.ConexionModel;

public class CuentaController {
    String url = "https://udbdsmapi.000webhostapp.com/api/user";
    Context context;
    String respuesta = "";
    String password;

    public CuentaController(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String mensaje);
        void onResponse(Object respuesta);
    }

    public void realizarPeticion(CuentaModel crearCuenta, VolleyResponseListener volleyResponseListener){
        JSONObject datos = new JSONObject();
        password = sha256(crearCuenta.getPassword());

        try{
            datos.put("nombre",crearCuenta.getNombre());
            datos.put("apellido",crearCuenta.getApellido());
            datos.put("correo",crearCuenta.getCorreo());
            datos.put("telefono",crearCuenta.getTelefono());
            datos.put("password", password);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datos, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        volleyResponseListener.onResponse(response);
                    }catch(Exception error){
                        error.printStackTrace();
                        Toast.makeText(context, "Hubo un error al realizar la petición.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyResponseListener.onError("se arruinó el programa");
                }

            });
            ConexionModel.getInstance(context).addToRequestQueue(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void verCuenta(CuentaModel cuenta, VolleyResponseListener volleyResponseListener){
        url = "https://udbdsmapi.000webhostapp.com/api/user/11";

        JSONObject datos = new JSONObject();
        try{

            datos.put("id",cuenta.getId());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, datos,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        volleyResponseListener.onResponse(response);
                    }catch(Exception e){
                        Toast.makeText(context, "Se arruinó", Toast.LENGTH_SHORT).show();
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

    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
