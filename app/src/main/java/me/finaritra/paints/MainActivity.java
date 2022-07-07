package me.finaritra.paints;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import me.finaritra.paints.view.DrawZoneView;
import me.finaritra.paints.view.drawable.impl.Circle;
import me.finaritra.paints.view.drawable.impl.Line;
import me.finaritra.paints.view.drawable.impl.Rectangle;

public class MainActivity extends AppCompatActivity {

    DrawZoneView drawZoneView;
    ImageButton squareBtn;
    ImageButton circleBtn;
    ImageButton crayonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawZoneView = (DrawZoneView) findViewById(R.id.drawZoneView);
        squareBtn = (ImageButton) findViewById(R.id.squareBtn);
        circleBtn = (ImageButton) findViewById(R.id.circleBtn);
        crayonBtn = (ImageButton) findViewById(R.id.crayonBtn);
        this.setUpButtonListner();
    }

    public void setUpButtonListner() {
        squareBtn.setOnClickListener(squareBtnOnClick);
        crayonBtn.setOnClickListener(crayonBtnOnClick);
        circleBtn.setOnClickListener(circleBtnOnClick);
    }

    View.OnClickListener squareBtnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DrawZoneView.tmpDrawable = new Rectangle();
        }
    };
    View.OnClickListener crayonBtnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DrawZoneView.tmpDrawable = new Line();
        }
    };
    View.OnClickListener circleBtnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DrawZoneView.tmpDrawable = new Circle();
        }
    };


}