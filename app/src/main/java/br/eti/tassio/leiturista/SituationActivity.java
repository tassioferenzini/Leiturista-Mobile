package br.eti.tassio.leiturista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SituationActivity extends AppCompatActivity {

    private Spinner spinner;
    private static final String[] paths = {"item 1", "item 2", "item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation);

        Spinner dynamicSpinner = (Spinner) findViewById(R.id.menusituation);

        String[] items = new String[]{"", "Leitura Implausível", "Releitura", "Situação de Risco", "Suspeita de Fraude", "Impedimento de leitura"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String txtCod = bundle.getString("cod");
        TextView txtResultado = (TextView) findViewById(R.id.txtLecturer);
        txtResultado.setText(txtCod);

        Button btReturn = (Button) findViewById(R.id.btReturn);
        btReturn.setOnClickListener(clickReturn);

        Button btNext = (Button) findViewById(R.id.btNext);
        btNext.setOnClickListener(clickConfirm);
    }

    private View.OnClickListener clickReturn = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent homeIntent = new Intent(SituationActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    };

    private View.OnClickListener clickConfirm = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent captureIntent = new Intent(SituationActivity.this, CaptureActivity.class);
            TextView codLecturer = (TextView) findViewById(R.id.txtLecturer);
            EditText codMeter = (EditText) findViewById(R.id.txtMeter);
            Spinner optSituation = (Spinner) findViewById(R.id.menusituation);
            String txtCodLecturer = codLecturer.getText().toString();
            String txtCodMeter = codMeter.getText().toString();
            String txtSituation = optSituation.getSelectedItem().toString();
            if (!txtCodLecturer.matches("") && !txtCodMeter.matches("") && !txtSituation.matches("")) {
                Bundle bundle = new Bundle();
                bundle.putString("codLecturer", txtCodLecturer.toUpperCase());
                bundle.putString("codMeter", txtCodMeter.toUpperCase());
                bundle.putString("codSituation", txtSituation.toUpperCase());
                captureIntent.putExtras(bundle);
                startActivity(captureIntent);
                finish();
            }
        }
    };

}
