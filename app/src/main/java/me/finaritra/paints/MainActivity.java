package me.finaritra.paints;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.finaritra.paints.view.DrawZoneView;
import me.finaritra.paints.view.drawable.impl.Circle;
import me.finaritra.paints.view.drawable.impl.EraserPoint;
import me.finaritra.paints.view.drawable.impl.Line;
import me.finaritra.paints.view.drawable.impl.Point;
import me.finaritra.paints.view.drawable.impl.Rectangle;
import yuku.ambilwarna.AmbilWarnaDialog;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    DrawZoneView drawZoneView;
    ImageButton squareBtn;
    ImageButton circleBtn;
    ImageButton lineBtn;
    ImageButton colorPickerButton;
    ImageButton undo;
    ImageButton redo;
    ImageButton crayonBtn;
    ImageButton eraserBtn;
    ImageButton cleanBtn;
    Slider slider;
    AlertDialog dialog;
    boolean isPressedUndo = false;
    boolean isPressedRedo = false;
    private final android.os.Handler handler = new Handler();
    private Runnable runnableUndo;
    private Runnable runnableRedo;
    private final long longClickDelay = 2000L;
    private final long updateDelay = 300L;

    final Logger Log = Logger.getLogger(this.getClass().getName());
    ImageButton saveButton;
    TextView textView;
    float strokeWidth = 10;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawZoneView = (DrawZoneView) findViewById(R.id.drawZoneView);
        squareBtn = (ImageButton) findViewById(R.id.squareBtn);
        circleBtn = (ImageButton) findViewById(R.id.circleBtn);
        lineBtn = (ImageButton) findViewById(R.id.lineBtn);
        crayonBtn = (ImageButton) findViewById(R.id.crayonBtn);
        saveButton = (ImageButton) findViewById(R.id.save);
        colorPickerButton = (ImageButton) findViewById(R.id.color_picker);
        undo = (ImageButton) findViewById(R.id.undo);
        redo = (ImageButton) findViewById(R.id.redo);
        textView = (TextView) findViewById(R.id.pixelWitdh);
        eraserBtn = (ImageButton) findViewById(R.id.eraser);
        slider = (Slider) findViewById(R.id.slider);
        cleanBtn = (ImageButton) findViewById(R.id.cleanBtn);
        slider.setValue(strokeWidth);
        textView.setText(new String(Math.floor(strokeWidth) + "px"));
        buildDialogClear();
        drawZoneView.tmpPaint.setStrokeWidth(strokeWidth);
        this.setUpButtonListner();

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {

            Log.log(Level.WARNING, "permission permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

            requestPermissions(permissions, PERMISSION_REQUEST_CODE);

        }
    }

    int selectedColor = 0;

    public void openColorPickerDialogue() {

        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, selectedColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        selectedColor = color;
                        drawZoneView.tmpPaint.setColor(selectedColor);
                    }
                });
        colorPickerDialogue.show();
    }

    final int REQUEST_DIRECTORY = 0;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveToFileRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            i.setType("image/jpeg");
            startActivityForResult(i, REQUEST_DIRECTORY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DIRECTORY) {
            try {
                saveToFile(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveToFile(Uri uri) throws FileNotFoundException {
        drawZoneView.setDrawingCacheEnabled(true);
        Log.info(String.format("Save to file %s", uri.getPath()));
        FileOutputStream file = (FileOutputStream) this.getContentResolver().openOutputStream(uri);
        Bitmap b = drawZoneView.getDrawingCache();
        b.compress(Bitmap.CompressFormat.JPEG, 95, file);
        try {
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void buildDialogClear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Nouvelle image");
        builder.setMessage("Voulez-vous vraiment sauvegarder avant de créer un nouveau?");
        builder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveToFileRequest();
                        drawZoneView.clean();
                    }
                });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                drawZoneView.clean();
            }
        });
        dialog = builder.create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setUpButtonListner() {
        squareBtn.setOnClickListener(squareBtnOnClick);
        lineBtn.setOnClickListener(lineBtnOnClick);
        circleBtn.setOnClickListener(circleBtnOnClick);
        colorPickerButton.setOnClickListener(colorPickerListener);
        slider.addOnChangeListener(sliderListner);
        undo.setOnTouchListener(undoClickListner);
        redo.setOnTouchListener(redoClickListner);
        crayonBtn.setOnClickListener(crayonBtnClick);
        eraserBtn.setOnClickListener(eraserBtnClick);
        saveButton.setOnClickListener(saveClickListner);
        cleanBtn.setOnClickListener((v) -> {
            dialog.show();
        });
        runnableUndo = new Runnable() {
            @Override
            public void run() {
                if (isPressedUndo) {
                    drawZoneView.undo();
                    handler.postDelayed(runnableUndo, updateDelay);
                }
            }
        };
        runnableRedo = new Runnable() {
            @Override
            public void run() {
                if (isPressedRedo) {
                    drawZoneView.redo();
                    handler.postDelayed(runnableRedo, updateDelay);
                }
            }
        };
    }

    View.OnClickListener colorPickerListener = (_v) -> openColorPickerDialogue();
    View.OnClickListener saveClickListner = (_v) -> this.saveToFileRequest();
    View.OnTouchListener undoClickListner = (View v, MotionEvent event) -> {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            isPressedUndo = true;
            drawZoneView.undo();
            handler.postDelayed(runnableUndo, longClickDelay);
        } else if (MotionEvent.ACTION_UP == event.getAction()) {
            isPressedUndo = false;
        }
        v.performClick();
        return true;
    };
    View.OnTouchListener redoClickListner = (View v, MotionEvent event) -> {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            isPressedRedo = true;
            drawZoneView.redo();
            handler.postDelayed(runnableRedo, longClickDelay);
        } else if (MotionEvent.ACTION_UP == event.getAction()) {
            isPressedRedo = false;
        }
        v.performClick();
        return true;
    };
    Slider.OnChangeListener sliderListner = (s, value, fromUser) -> {
        drawZoneView.tmpPaint.setStrokeWidth(value);
        strokeWidth = value;
        textView.setText(new String(Math.floor(strokeWidth) + "px"));
    };
    View.OnClickListener squareBtnOnClick = view -> DrawZoneView.tmpDrawable = new Rectangle();
    View.OnClickListener lineBtnOnClick = view -> DrawZoneView.tmpDrawable = new Line();
    View.OnClickListener crayonBtnClick = view -> DrawZoneView.tmpDrawable = new Point();
    View.OnClickListener eraserBtnClick = view -> DrawZoneView.tmpDrawable = new EraserPoint();
    View.OnClickListener circleBtnOnClick = view -> DrawZoneView.tmpDrawable = new Circle();


}