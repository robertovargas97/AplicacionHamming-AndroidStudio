package com.example.robertovargas.aplicacion_hamming_v1;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    //Atributos Globales de la clase
    private char[] vec = new char[22];

    boolean permiso = false;
    private OutputStream outputStream;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setMessage("Desea salir de HammingApp");
        myBuild.setTitle("Salir");

        myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        AlertDialog salir = myBuild.create();
        salir.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder instrucciones = new AlertDialog.Builder(this);
        instrucciones.setTitle("Indicaciones");
        instrucciones.setMessage("1)Ingrese o genere una hilera de 17 bits de datos con 0s y 1s." + "\n" + "\n" +
                "2)Pulse el boton para Hammificar" + "\n" + "\n" + "3)Para conectar y enviar la hilera hamificada via " +
                "Bluetooth debe conocer el nombre del dispositivo con el que desea conectar e ingresarlo una vez que se haya hamificado.");

        instrucciones.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog in = instrucciones.create();
        in.show();
    }

    // ----------------------------------METODOS PARA LA HILERA A HAMMIFICAR--------------------------------------------//

    //MOD : vec[0] en *this , vectorParidad[4] en *this
    void P1() {
        int contador = 0;
        int i = 1;

        // Se necesita saber si los P impares tienen 1, pero es con posicion atrasada  debido el vector por eso se revisan los indices pares ya que el indice i=2 es
        // P3, i=4 es P5....
        while ((i * 2) < 22) {
            if (vec[i * 2] == '1') {
                contador++;
            }
            i++; // Se avanza de posicion
        }

        if (contador % 2 != 0) { // Si la cantidad de 1s encontrados es par
            vec[0] = '1';

        } else { // Si la cantidad de 1s encontrados es impar
            vec[0] = '0';

        }
    }

    //MOD : vec[1] en *this , vectorParidad[3] en *this
    void P2() {
        int contador = 0;
        int longitudNumero = 0;
        String numeroBinario = ""; // Representara el numero del cual se verificara el bit 2 encendido
        int i = 2;

        while (i < vec.length) { // Mientras el vector hammificado no termine
            numeroBinario = Integer.toBinaryString(i + 1); // i +1 ya que es con posicion atrasada y el i = 2 representa
            // el P3...
            longitudNumero = numeroBinario.length();

            switch (longitudNumero) {

                // Quiere decir que el numero binario es de dos bits por ejemplo el numero 2 y 3
                case 2: {
                    if ((numeroBinario.charAt(0) == '1') && (vec[i] == '1')) { // Si el bit del 2 esta encendido y hay un 1
                        // en el vector hammificado
                        contador++;
                    }
                    i++;
                    break;
                }

                // Quiere decir que el numero binario es de tres bits por ejemplo el numero 7 y
                // 5
                case 3: {
                    if ((numeroBinario.charAt(1) == '1') && (vec[i] == '1')) { // Si el bit del 2 esta encendido y hay un 1
                        // en el vector hammificado
                        contador++;
                    }
                    i++;
                    break;
                }

                // Quiere decir que el numero binario es de cinco bits por ejemplo el numero
                // 20,21
                case 4: {
                    if ((numeroBinario.charAt(2) == '1') && (vec[i] == '1')) {// Si el bit del 2 esta encendido y hay un 1
                        // en el vector hammificado
                        contador++;
                    }
                    i++;
                    break;
                }

                // Quiere decir que el numero binario es de cuatro bits por ejemplo el numero 15
                case 5: {
                    if ((numeroBinario.charAt(3) == '1') && (vec[i] == '1')) {// Si el bit del 2 esta encendido y hay un 1
                        // en el vector hammificado
                        contador++;
                    }
                    i++;
                    break;
                }
            }
        }
        if (contador % 2 != 0) { // Si la cantidad de 1s encontrados es par
            vec[1] = '1';

        } else { // Si la cantidad de 1s encontrados es impar
            vec[1] = '0';

        }

    }

    //MOD : vec[3] en *this , vectorParidad[2] en *this
    void P4() {
        int contador = 0;
        int longitudNumero = 0;
        String numeroBinario = ""; // Representara el numero del cual se verificara el bit 4 encendido
        int i = 4;

        while (i < vec.length) { // Mientras el vector hammificado no termine
            numeroBinario = Integer.toBinaryString(i + 1); // i +1 ya que es con posicion atrasada y el i = 4 representa
            // el P5...
            longitudNumero = numeroBinario.length();

            switch (longitudNumero) {

                // Quiere decir que el numero binario es de tres bits por ejemplo el numero 7 y 5
                case 3: {
                    if ((numeroBinario.charAt(0) == '1') && (vec[i] == '1')) { // Si el bit del 4 esta encendido y hay un 1 en el vector hammificado
                        contador++;
                    }
                    i++;
                    break;
                }

                // Quiere decir que el numero binario es de cinco bits por ejemplo el numero 20,21
                case 4: {
                    if ((numeroBinario.charAt(1) == '1') && (vec[i] == '1')) {// Si el bit del 4 esta encendido y hay un 1 en el vector hammificado
                        contador++;
                    }
                    i++;
                    break;
                }

                // Quiere decir que el numero binario es de cuatro bits por ejemplo el numero 15
                case 5: {
                    if ((numeroBinario.charAt(2) == '1') && (vec[i] == '1')) {// Si el bit del 4 esta encendido y hay un 1 en el vector hammificado
                        contador++;
                    }
                    i++;
                    break;
                }
            }
        }
        if (contador % 2 != 0) { // Si la cantidad de 1s encontrados es par
            vec[3] = '1';

        } else { // Si la cantidad de 1s encontrados es impar
            vec[3] = '0';

        }
    }

    //MOD : vec[7] en *this , vectorParidad[1] en *this
    void P8() {
        int contador = 0;
        for (int i = 8; i <= 14; i++) {
            if (vec[i] == '1') {
                contador++;
            }
        }
        if (contador % 2 != 0) {
            vec[7] = '1';

        } else {
            vec[7] = '0';

        }

    }

    //MOD : vec[15] en *this , vectorParidad[0] en *this
    void P16() {
        int contador = 0;
        for (int i = 16; i <= 21; i++) {
            if (vec[i] == '1') {
                contador++;
            }
        }
        if (contador % 2 != 0) {
            vec[15] = '1';

        } else {
            vec[15] = '0';

        }

    }

    //MOD : vec en *this llenandolo por completo
    void hileraHamming(String cadena) {
        int i = 0; //Indice para moverse sobre la hilera
        int j = 0;//Indice para moverse sobre el vector

        while (i < cadena.length()) { //Mientras la hilera no se termine

            if (j != 0 && j != 1 && j != 3 && j != 7 && j != 15) { //Para no llenar los campos de los bits de paridad

                vec[j] = cadena.charAt(i); //Coloca en el vector que representa la hilera Hammificada el valor de tomado desde la hilera
                i++;
                j++;
            } else {
                ++j;
            }

        }
        P1();
        P2();
        P4();
        P8();
        P16();
    }

    //EFE: Muestra en pantalla la hilera hammificada
    String imprimirHileraHamming() {
        String hileraHamming = "";
        for (int i = 0; i < vec.length; i++) {
            hileraHamming = hileraHamming + " " + vec[i];
        }
        return hileraHamming;
    }

    String hileraParaEnviar() {
        String hileraHammingParaEnviar = "";
        for (int i = 0; i < vec.length; i++) {
            hileraHammingParaEnviar = hileraHammingParaEnviar + " " + vec[i];
        }
        return hileraHammingParaEnviar;
    }

    //-----------------------------------METODOS DE ALERTA---------------------------------------------------------------//

    void alertaBits() {
        AlertDialog alerta;
        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("Error");
        alerta.setMessage("No ingresaste 17 bits de datos,vuelve a intentarlo.");
        alerta.show();
    }

    void alertaBitMal() {
        AlertDialog alerta;
        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("Error");
        alerta.setMessage("Ingresaste un digito distinto a 0 y 1 en la hilera,vuelve a ingresarla.");
        alerta.show();
    }

    void alertaBT() {
        AlertDialog alerta;
        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("Error");
        alerta.setMessage("Debes generar una hilera Hamificada antes de poder enviarla.");
        alerta.show();
    }

    void alertaBTDesactivado() {
        AlertDialog alerta;
        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("Error");
        alerta.setMessage("Bluetooth desactivado." + "\n" + "Activalo para poder enviar la hilera");
        alerta.show();
    }

    //--------------------------METODOS onClick----------------------------------------------------------------//

    public void hamming(View view) {
        String cadena = "";
        EditText texto;
        texto = (EditText) findViewById(R.id.editText);
        TextView ingreso;
        ingreso = (TextView) findViewById(R.id.textView1);
        TextView salida;
        salida = (TextView) findViewById(R.id.textView2);

        boolean mal = false;
        cadena = texto.getText().toString();

        if (cadena.length() == 17) {
            for (int i = 0; i < cadena.length() && mal == false; i++) {
                if ((cadena.charAt(i) != '1') && (cadena.charAt(i) != '0')) {
                    mal = true;
                }
            }

            if (mal == true) {
                alertaBitMal();
            } else {
                hileraHamming(cadena);
                salida.setText("Hilera Hammificada");
                ingreso.setText(imprimirHileraHamming());
                permiso = true;
            }
        } else {
            alertaBits();
            salida.setText("");
            ingreso.setText("");
        }
    }

    public void generarHileraAleatoria(View view) {
        EditText hilera;
        String cadena = "";
        Random aleatorio = new Random(System.currentTimeMillis()); //Inicializa la semilla del random para poder dar numero distintos
        int numeroAleatorio = 0;

        for (int i = 0; i < 17; i++) {
            numeroAleatorio = aleatorio.nextInt(10);
            if (numeroAleatorio < 5) {
                cadena += "1";
            } else {
                cadena += "0";
            }
        }
        hilera = (EditText) findViewById(R.id.editText);
        hilera.setText(cadena);
    }

    public void enviarPorBluetooth(View view) throws IOException {
        String h = hileraParaEnviar();

        if (permiso) {
            activarEnviarViaBT();
        } else {
            alertaBT();
        }
    }

//-------------------------------------Para enviar por bluetooth-------------------------------------//


    void activarEnviarViaBT() throws IOException {
        int encontro = 0;
        boolean find = false;
        String h = hileraParaEnviar();

        AlertDialog alerta;
        alerta = new AlertDialog.Builder(this).create();

        EditText nombreDelDispositivo;
        String celularDeseado = "";

        nombreDelDispositivo = (EditText) findViewById(R.id.editText1);
        celularDeseado = nombreDelDispositivo.getText().toString();

        try {

            BluetoothAdapter adaptadorBluethooth = BluetoothAdapter.getDefaultAdapter();
            if (adaptadorBluethooth != null) {

                if (adaptadorBluethooth.isEnabled()) {

                    Set<BluetoothDevice> dispositivosEmparejados = adaptadorBluethooth.getBondedDevices();
                    Object[] dispositivos = (Object[]) dispositivosEmparejados.toArray();

                    if (dispositivosEmparejados.size() > 0) {

                        for (int i = 0; i < dispositivos.length && (!find); i++) {

                            if (((BluetoothDevice) dispositivos[i]).getName().compareToIgnoreCase(celularDeseado) == 0) {
                                alerta.setTitle("Dispositivo Encontrado");
                                alerta.setMessage(((BluetoothDevice) dispositivos[i]).getName() + " " + "\n" + "Uuid : " + ((BluetoothDevice) dispositivos[i]).getAddress());
                                alerta.show();
                                encontro = i;
                                find = true;
                            }
                        }

                        BluetoothDevice dispositivo = (BluetoothDevice) dispositivos[encontro];
                        ParcelUuid[] uuids = dispositivo.getUuids();
                        BluetoothSocket socket = dispositivo.createRfcommSocketToServiceRecord(uuids[encontro].getUuid());
                        socket.connect();
                        outputStream = socket.getOutputStream();
                        enviar(h);
                    }

                    Log.e("error", "No appropriate paired devices.");
                }
                else {
                    Log.e("error", "Bluetooth is disabled.");
                    alertaBTDesactivado();
                }
            }
        } catch (Exception e) {

        }
    }

    void enviar(String s) throws IOException {
        outputStream.write(s.getBytes());
    }
}

