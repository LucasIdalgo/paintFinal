package br.com.progiv.paintfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.mukesh.DrawingView;

import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Button penColorButtonVerde,penColorButtonAzul,
            penColorButtonVermelho,penColorButtonPreto,penColorButtonCiano,penColorButtonAmarelo,
            penColorButtonMagenta, clearButton;
    private ImageButton penButton, eraserButton, saveButton;
    private DrawingView drawingView;
    private SeekBar penSizeSeekBar, eraserSizeSeekBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        setListeners();
    }

    private void setListeners() {
        saveButton.setOnClickListener(this);
        penButton.setOnClickListener(this);
        eraserButton.setOnClickListener(this);
        penColorButtonVerde.setOnClickListener(this);
        penColorButtonAzul.setOnClickListener(this);
        penColorButtonCiano.setOnClickListener(this);
        penColorButtonAmarelo.setOnClickListener(this);
        penColorButtonMagenta.setOnClickListener(this);
        penColorButtonPreto.setOnClickListener(this);
        penColorButtonVermelho.setOnClickListener(this);
        penSizeSeekBar.setOnSeekBarChangeListener(this);
        eraserSizeSeekBar.setOnSeekBarChangeListener(this);
        clearButton.setOnClickListener(this);
    }

    private void initializeUI() {
        drawingView = findViewById(R.id.scratch_pad);
        saveButton = findViewById(R.id.save_button);
        penButton = findViewById(R.id.pen_button);
        eraserButton = findViewById(R.id.eraser_button);
        penColorButtonVerde = findViewById(R.id.pen_color_button_verde);
        penColorButtonVermelho = findViewById(R.id.pen_color_button_vermelho);
        penColorButtonAzul = findViewById(R.id.pen_color_button_azul);
        penColorButtonPreto = findViewById(R.id.pen_color_button_preto);
        penColorButtonAmarelo = findViewById(R.id.pen_color_button_amarelo);
        penColorButtonCiano = findViewById(R.id.pen_color_button_ciano);
        penColorButtonMagenta = findViewById(R.id.pen_color_button_magenta);
        penSizeSeekBar = findViewById(R.id.pen_size_seekbar);
        eraserSizeSeekBar = findViewById(R.id.eraser_size_seekbar);
        clearButton = findViewById(R.id.clear_button);
    }

    @Override public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button:
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
                saveDialog.setTitle("Deseja salvar?");
                saveDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawingView.setDrawingCacheEnabled(true);
                        String imgSaved = MediaStore.Images.Media.insertImage(
                                getContentResolver(), drawingView.getDrawingCache(),
                                UUID.randomUUID().toString()+".png","desenhando");
                        if(imgSaved!=null){
                            Toast saveToast = Toast.makeText(getApplicationContext(),
                                    "Salvo com sucesso", Toast.LENGTH_LONG);
                            saveToast.show();
                        }else{
                            Toast unSaved = Toast.makeText(getApplicationContext(),
                                    "Falha ao salvar, verifique se habilitou a permissão", Toast.LENGTH_LONG);
                            unSaved.show();
                        }
                        drawingView.destroyDrawingCache();
                    }
                });
                saveDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                saveDialog.show();
                break;
            case R.id.pen_button:
                drawingView.initializePen();
                break;
            case R.id.eraser_button:
                drawingView.initializeEraser();
                break;
            case R.id.clear_button:
                drawingView.clear();
                break;
            case R.id.pen_color_button_verde:
                drawingView.setPenColor(Color.GREEN);
                /*final ColorPicker colorPicker = new ColorPicker(MainActivity.this, 100, 100, 100);
                colorPicker.setCallback(
                        new ColorPickerCallback() {
                            @Override public void onColorChosen(int color) {
                                drawingView.setPenColor(color);
                                colorPicker.dismiss();
                            }
                        });
                colorPicker.show();*/
                break;
            case R.id.pen_color_button_azul:
                drawingView.setPenColor(Color.BLUE);
                break;
            case R.id.pen_color_button_preto:
                drawingView.setPenColor(Color.BLACK);
                break;
            case R.id.pen_color_button_vermelho:
                drawingView.setPenColor(Color.RED);
                break;
            case R.id.pen_color_button_ciano:
                drawingView.setPenColor(Color.CYAN);
                break;
            case R.id.pen_color_button_amarelo:
                drawingView.setPenColor(Color.YELLOW);
                break;
            case R.id.pen_color_button_magenta:
                drawingView.setPenColor(Color.MAGENTA);
                break;
            default:
                break;
        }
    }

    @Override public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int seekBarId = seekBar.getId();
        if (seekBarId == R.id.pen_size_seekbar) {
            drawingView.setPenSize(i);
        } else if (seekBarId == R.id.eraser_size_seekbar) {
            drawingView.setEraserSize(i);
        }
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {
        //Intentionally Empty
    }

    @Override public void onStopTrackingTouch(SeekBar seekBar) {
        //Intentionally Empty
    }
}