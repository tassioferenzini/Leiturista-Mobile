package br.eti.tassio.leiturista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureActivity extends AppCompatActivity {

    private static final int REQUEST = 1;
    private LocationManager locationManager;
    private Location location;
    private String txtCodLecturer, txtCodMeter, txtCodSituation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        Button btcamera = (Button) findViewById(R.id.btCapture);
        Button btnextopt = (Button) findViewById(R.id.btNextOpt);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        txtCodLecturer = bundle.getString("codLecturer");
        TextView txtLecturer = (TextView) findViewById(R.id.txtLecturer);
        txtLecturer.setText(txtCodLecturer);
        txtCodMeter = bundle.getString("codMeter");
        TextView txtMeter = (TextView) findViewById(R.id.txtMeter);
        txtMeter.setText(txtCodMeter);
        txtCodSituation = bundle.getString("codSituation");
        TextView txtSituation = (TextView) findViewById(R.id.txtSituation);
        txtSituation.setText(txtCodSituation);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Leiturista");
                if (!photoDirectory.exists()) {
                    photoDirectory.mkdir();
                }
                String photoName = getPhotName();
                File imgFile = new File(photoDirectory, photoName);
                Uri uriPhoto = FileProvider.getUriForFile(CaptureActivity.this, BuildConfig.APPLICATION_ID + ".provider",imgFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
                startActivityForResult(cameraIntent, 0);
            }
        });


        btnextopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaptureActivity.this, FinishActivity.class);
                TextView codLecturer = (TextView) findViewById(R.id.txtLecturer);
                TextView codMeter = (TextView) findViewById(R.id.txtMeter);
                TextView optSituation = (TextView) findViewById(R.id.txtSituation);
                TextView location = (TextView) findViewById(R.id.txtlocation);
                String txtCodLecturer = codLecturer.getText().toString();
                String txtCodMeter = codMeter.getText().toString();
                String txtSituation = optSituation.getText().toString();
                String txtLocation = location.getText().toString();
                if (!txtCodLecturer.matches("") && !txtCodMeter.matches("") && !txtSituation.matches("") && !txtLocation.matches("")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("codLecturer", txtCodLecturer.toUpperCase());
                    bundle.putString("codMeter", txtCodMeter.toUpperCase());
                    bundle.putString("codSituation", txtSituation.toUpperCase());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private String getPhotName() {
        String location = getLocation();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return txtCodLecturer + "_" + txtCodMeter + "_" + txtCodSituation + "_" + timestamp + "_" + location + ".jpg";
    }

    private String getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST);
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    ((TextView) findViewById(R.id.txtlocation)).setText(latitude + " - " + longitude);
                    return latitude + "_" + longitude;
                } else {
                    ((TextView) findViewById(R.id.txtlocation)).setText("Erro no GPS");
                    return "ErroNoGPS";
                }
            } else {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                ((TextView) findViewById(R.id.txtlocation)).setText(latitude + " - " + longitude);
                return latitude + "_" + longitude;
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST:
                getLocation();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getPhotName();
        }
    }
}
