package br.eti.tassio.leiturista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnext = (Button) findViewById(R.id.btnext);
        btnext.setOnClickListener(clicknext);
    }

    private View.OnClickListener clicknext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent situationIntent = new Intent(HomeActivity.this, SituationActivity.class);
            EditText cod = (EditText) findViewById(R.id.cod);
            String txtCod = cod.getText().toString();
            if (!txtCod.matches("")) {
                Bundle bundle = new Bundle();
                bundle.putString("cod", txtCod.toUpperCase());
                situationIntent.putExtras(bundle);
                startActivity(situationIntent);
                finish();
            }
        }
    };

}
