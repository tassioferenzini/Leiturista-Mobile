package br.eti.tassio.leiturista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Button btok = (Button) findViewById(R.id.btOk);
        btok.setOnClickListener(clickok);

        Button btnew = (Button) findViewById(R.id.btNewCapture);
        btnew.setOnClickListener(clicknew);
    }

    private View.OnClickListener clickok = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener clicknew = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent capIntent = new Intent(FinishActivity.this, CaptureActivity.class);

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String txtCodLecturer = bundle.getString("codLecturer");
            String txtCodMeter = bundle.getString("codMeter");
            String txtSituation = bundle.getString("codSituation");

            if (!txtCodLecturer.matches("") && !txtCodMeter.matches("") && !txtSituation.matches("")) {
                Bundle nbundle = new Bundle();
                nbundle.putString("codLecturer", txtCodLecturer.toUpperCase());
                nbundle.putString("codMeter", txtCodMeter.toUpperCase());
                nbundle.putString("codSituation", txtSituation.toUpperCase());
                capIntent.putExtras(nbundle);
                startActivity(capIntent);
                finish();
            }
        }
    };

}
