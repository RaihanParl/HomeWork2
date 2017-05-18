package com.bidjidevelops.hd;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.bidjidevelops.hd.gambar.Upload;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.senab.photoview.PhotoView;


public class Profile extends AppCompatActivity {
    ArrayList<muser> data;
    PhotoView pvcomment;
    @BindView(R.id.txtSekolah)
    TextView txtSekolah;
    @BindView(R.id.txtDesknya)
    TextView txtDesknya;
    @BindView(R.id.txtDesk)
    TextView txtDesk;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.btnEditImage)
    Button btnEditImage;
    @BindView(R.id.btnEditImagesam)
    Button btnEditImagesam;
    @BindView(R.id.coverimgpro)
    ImageView coverimgpro;

    private LayoutInflater inflater;

    ImageView imgprof;
    TextView txtnamanya;
    TextView txtEmailnya;
    TextView txtEmail;
    TextView txtSekolahnya;
    FrameLayout frameProfile;
    String email, username, sekolah, desk;
    String getemail, getusername, getsekolah, getuserImager, getDesk,getCoverimg;
    String Spassword, Semail, Remail, userImager, getIdUser, coverimg;
    public AQuery aQuery;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    public NavigationView navigationView;
    MainActivity mainActivity;
    SessionManager sessionManager;
    String id;
    String iduserses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getIdUser = getIntent().getStringExtra("iduser");
        getusername = getIntent().getStringExtra("usernam");
        getuserImager = getIntent().getStringExtra("imageuser");
        getemail = getIntent().getStringExtra("email");
        getDesk = getIntent().getStringExtra("desk");
        getsekolah = getIntent().getStringExtra("sekolah");
        getIdUser = getIntent().getStringExtra("iduser");
        getCoverimg = getIntent().getStringExtra("coverimg");
        pvcomment = (PhotoView) findViewById(R.id.pvcomment);
        imgprof = (ImageView) findViewById(R.id.imgprof);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmailnya = (TextView) findViewById(R.id.txtEmailnya);
        txtSekolahnya = (TextView) findViewById(R.id.txtSekolahnya);
        txtnamanya = (TextView) findViewById(R.id.txtnamanya);
        data = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = sessionManager.getUserDetails();
        frameProfile = (FrameLayout) findViewById(R.id.frameProfile);
        aQuery = new AQuery(getApplicationContext());
        Semail = user.get(SessionManager.kunci_email);
        Spassword = user.get(SessionManager.kunci_password);
        iduserses = user.get(SessionManager.idusers);
        if (iduserses.equals(getIdUser)) {
            btnEdit.setVisibility(View.VISIBLE);
            btnEditImage.setVisibility(View.VISIBLE);
            btnEditImagesam.setVisibility(View.VISIBLE);
            getdata();
            getdata();
        } else {
            txtnamanya.setText(getusername);
            txtEmailnya.setText(getemail);
            txtDesknya.setText(getDesk);
            txtSekolahnya.setText(getsekolah);
            Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + getuserImager).placeholder(R.drawable.student).into(imgprof);
            Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + getCoverimg).placeholder(R.drawable.logoreal).into(coverimgpro);

        }


        popupimg();

    }

    public void getdata() {

        data.clear();
        String url = Helper.BASE_URL + "login.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("result");
                    String pesan = json.getString("msg");
                    if (result.equalsIgnoreCase("true")) {
                        JSONArray jsonArray = json.getJSONArray("user");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject object = jsonArray.getJSONObject(a);
                            muser d = new muser();
                            String deskripsi;
                            d.setId_user(object.getString("iduser"));
                            d.setEmail(object.getString("email"));
                            d.setPassword(object.getString("password"));
                            d.setSekolah(object.getString("School"));
                            d.setUsername(object.getString("Username"));
                            d.setUserimage(object.getString("Image"));
                            email = object.getString("email");
                            username = object.getString("Username");
                            id = object.getString("iduser");
                            userImager = object.getString("Image");
                            desk = object.getString("desk");
                            sekolah = object.getString("School");
                            deskripsi = object.getString("desk");
                            coverimg = object.getString("coverimg");
                            data.add(d);
                            txtnamanya.setText(username);
                            txtEmailnya.setText(email);
                            txtSekolahnya.setText(sekolah);
                            txtDesknya.setText(deskripsi);
                            //Toast.makeText(MainActivity.this, userImager, Toast.LENGTH_SHORT).show();
                            Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + userImager).placeholder(R.drawable.student).into(imgprof);
                            Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + coverimg).placeholder(R.drawable.logoreal).into(coverimgpro);
                        }
                    } else {
//                        elseToast.makeText(getApplicationContext(), pesan, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error parsing data", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramuserlogin = new HashMap<>();
                paramuserlogin.put("email", Semail);
                paramuserlogin.put("password", Spassword);
                return paramuserlogin;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void Edit(View v) {
        inflater = Profile.this.getLayoutInflater();
        View content = inflater.inflate(R.layout.pop_profile, null);
        final EditText edNama, edEmail, edSekolah, edDesk;
        CircleImageView imgProfPo;
        Button btnImage;
        getdata();
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        edNama = (EditText) content.findViewById(R.id.ednamaProf);
        edEmail = (EditText) content.findViewById(R.id.edemailprof);
        edDesk = (EditText) content.findViewById(R.id.edDeskProf);
        edSekolah = (EditText) content.findViewById(R.id.edSekolahProf);
        imgProfPo = (CircleImageView) content.findViewById(R.id.imgprof);
        Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + getuserImager).placeholder(R.drawable.student).into(imgProfPo);
        builder.setView(content)
                .setTitle("Edit profile")
                .setPositiveButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String URL = Helper.BASE_URL + "editprofile.php";
                        Map<String, String> paramsendcomment = new HashMap<>();
                        paramsendcomment.put("email", edEmail.getText().toString());
                        paramsendcomment.put("username", edNama.getText().toString());
                        paramsendcomment.put("school", edSekolah.getText().toString());
                        paramsendcomment.put("desk", edDesk.getText().toString());
                        paramsendcomment.put("school", edSekolah.getText().toString());
                        paramsendcomment.put("id", id);
                        sessionManager.createSession(edEmail.getText().toString(), Spassword);
            /*menampilkan progressbar saat mengirim data*/
                        ProgressDialog pd = new ProgressDialog(getApplicationContext());
                        try {
                /*format ambil data*/
                            aQuery.progress(pd).ajax(URL, paramsendcomment, String.class, new AjaxCallback<String>() {
                                @Override
                                public void callback(String url, String object, AjaxStatus status) {
                        /*jika objek tidak kosong*/
                                    if (object != null) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(object);
                                            String result = jsonObject.getString("result");
                                            String msg = jsonObject.getString("msg");
                                /*jika result adalah benar, maka pindah ke activity login dan menampilkan pesan dari server,
                                serta mematikan activity*/
                                            if (result.equalsIgnoreCase("true")) {
                                                getdata2();

                                            } else {
                                                Helper.pesan(getApplicationContext(), msg);
                                            }

                                        } catch (JSONException e) {
                                            Helper.pesan(getApplicationContext(), "internet lambat");
                                        }
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Helper.pesan(getApplicationContext(), "Gagal mengambil data");
                        }
                    }
                });
        edEmail.setText(email);
        edNama.setText(username);
        edSekolah.setText(sekolah);
        edDesk.setText(desk);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void Image(View v) {
        Intent i = new Intent(getApplicationContext(), Upload.class);
        i.putExtra("id_user", id);
        startActivity(i);

    }
    public void Imagesamp(View v){
        Intent i = new Intent(getApplicationContext(), UpCover.class);
        i.putExtra("id_user", id);
        startActivity(i);
    }

    public void popupimg() {
        imgprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflater = Profile.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.popimg, null);
                PhotoView pv;
                pv = (PhotoView) content.findViewById(R.id.pvcomment);
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + getuserImager).placeholder(R.drawable.student).into(pv);
                builder.setView(content);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    public void reveral() {
        //        imgprof.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imgprof.setVisibility(View.INVISIBLE);
//                getdata();
//                Toast.makeText(getApplicationContext(), "" + userImager, Toast.LENGTH_SHORT).show();
//                Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + userImager).placeholder(R.drawable.student).into(pvcomment);
//                Revealator.reveal(frameProfile)
//                        .from(imgprof)
//                        .withCurvedTranslation()
//                        .withChildsAnimation()
//                        .start();
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

    public void getdata2() {
        sessionManager = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = sessionManager.getUserDetails();
        Semail = user.get(SessionManager.kunci_email);
        Spassword = user.get(SessionManager.kunci_password);
        iduserses = user.get(SessionManager.idusers);
        data.clear();
        String url = Helper.BASE_URL + "login.php";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("result");
                    String pesan = json.getString("msg");
                    if (result.equalsIgnoreCase("true")) {
                        JSONArray jsonArray = json.getJSONArray("user");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject object = jsonArray.getJSONObject(a);
                            muser d = new muser();
                            String deskripsi;
                            d.setId_user(object.getString("iduser"));
                            d.setEmail(object.getString("email"));
                            d.setPassword(object.getString("password"));
                            d.setSekolah(object.getString("School"));
                            d.setUsername(object.getString("Username"));
                            d.setUserimage(object.getString("Image"));
                            email = object.getString("email");
                            username = object.getString("Username");
                            id = object.getString("iduser");
                            userImager = object.getString("Image");
                            desk = object.getString("desk");
                            sekolah = object.getString("School");
                            deskripsi = object.getString("desk");
                            coverimg = object.getString("coverimg");
                            data.add(d);
                            txtnamanya.setText(username);
                            txtEmailnya.setText(email);
                            txtSekolahnya.setText(sekolah);
                            txtDesknya.setText(deskripsi);
                            //Toast.makeText(MainActivity.this, userImager, Toast.LENGTH_SHORT).show();
                            Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + userImager).into(imgprof);
                            Glide.with(getApplicationContext()).load(Helper.BASE_IMGUS + coverimg).into(coverimgpro);
                        }
                    } else {
//                        elseToast.makeText(getApplicationContext(), pesan, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error parsing data", Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramuserlogin = new HashMap<>();
                paramuserlogin.put("email", Semail);
                paramuserlogin.put("password", Spassword);
                return paramuserlogin;
            }
        };
        requestQueue.add(stringRequest);
    }

}
