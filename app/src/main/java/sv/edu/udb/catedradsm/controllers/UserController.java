package sv.edu.udb.catedradsm.controllers;

import android.content.Context;
import android.util.Base64;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sv.edu.udb.catedradsm.models.ConexionModel;

public class UserController {
    String url = "https://udbdsmapi.000webhostapp.com/api/user";
    Context context;
    String respuesta = "";

    public UserController(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String mensaje);
        void onResponse(Object respuesta);
    }

    /*
    public void realizarPeticion(VolleyResponseListener volleyResponseListener){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
    }
    */

    public void iniciarSesion(String correo, String pass, VolleyResponseListener volleyResponseListener){
        String apiUrl ="https://udbdsmapi.000webhostapp.com/login";
        pass = sha256(pass);

        JSONObject datos = new JSONObject();
        try {
            datos.put("correo", correo);
            datos.put("pass", pass);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, apiUrl, datos,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        System.out.println(response.toString());
                        volleyResponseListener.onResponse(response);
                    }catch(Exception e){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyResponseListener.onError("Hubo un error al realizar la petición.");
                }

            });
            ConexionModel.getInstance(context).addToRequestQueue(request);

        } catch (JSONException e) {
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