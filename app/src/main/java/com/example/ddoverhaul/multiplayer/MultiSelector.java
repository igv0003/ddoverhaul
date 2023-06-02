package com.example.ddoverhaul.multiplayer;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddoverhaul.navigation.Normal.BaseActivity;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;

import java.util.ArrayList;

public class MultiSelector extends BaseActivity {


    private String mainToken; // Token del dispositivo, sea cliente o master
    private String hostToken; // Token del máster en caso de ser cliente
    private String[] clientTokens; // Tokens de los clientes en caso de ser master
    private Personaje[] clientChars; // Personajes de los clientes que se unieron a la sala
    private int size;
    // Esta variable solo se inicializa cuando se elige ser máster o jugador
    private MultiConfig config;

    // Layouts para mostrar
    private LinearLayout layoutMultiplayer;
    private LinearLayout waitingClient;
    private LinearLayout waitingMaster;
    private LinearLayout selectMaster;
    private LinearLayout selectClient;

    // Variables de layoutMultiplayer
    private Button master;
    private Button client;

    // Variables de selectMaster
    private EditText masterPW;
    private Button selectMasterButton;

    // Variables de waitingMaster
    private TextView sizeLobby;
    private Button startGame;
    private Button cancelLobby;

    // Variables de selectClient
    private EditText clientPW;
    private Spinner spinnerChars;
    ArrayList<Personaje> charlist;
    private Button selectClientButton;
    private Personaje character;

    // Variables de waitingClient
    private Button cancelJoin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_selector);

        // Se recogen los layouts
        this.layoutMultiplayer = findViewById(R.id.layout_multiplayer);
        this.waitingMaster = findViewById(R.id.waiting_master);
        this.waitingClient = findViewById(R.id.waiting_client);
        this.selectMaster = findViewById(R.id.layoutSelectMaster);
        this.selectClient = findViewById(R.id.layoutSelectClient);

        // layoutMultiplayer
        this.master = findViewById(R.id.master_button);
        this.client = findViewById(R.id.client_button);

        // Al pulsar en el botón master
        this.master.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Esconde el layout de selectClient y elimina config
                selectClient.setVisibility(View.GONE);
                if (config != null) {
                    config = null;
                }
                // Muestra selectMaster
                selectMaster.setVisibility(View.VISIBLE);

                // Inicializa masterPw y el botón para crear la sala
                masterPW = findViewById(R.id.master_pw);
                selectMasterButton = findViewById(R.id.selectMaster_button);

                // Al pulsar el boton de crear sala y que haya datos en masterPw
                selectMasterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!masterPW.getText().toString().equals("")) {
                            // Se llama al método de crear la sala
                            createLobby(masterPW.getText().toString());
                            // Se esconde layoutMultiplayer y llama al método de mostrar waitingMaster
                            layoutMultiplayer.setVisibility(View.GONE);
                            showWaitingMaster();
                        }
                    }
                });
            }
        });

        // Al pulsar en el botón Unirse
        this.client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Esconde el layout de selectMaster y elimina config
                selectMaster.setVisibility(View.GONE);
                if (config != null) { config = null ;}

                // Muestra selectClient
                selectClient.setVisibility(View.VISIBLE);

                // Inizializa clientPW, el botón de unirse a sala y spinner de personajes
                clientPW = findViewById(R.id.client_pw);
                createSpinner();
                selectClientButton = findViewById(R.id.selectClient_button);

                // Al pulsar el boton de unirse a sala y que haya datos en masterPw y el spinner
                selectClientButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!clientPW.getText().toString().equals("") && spinnerChars.isSelected()) {
                            // Se llama al método de buscar la sala
                            findLobby(clientPW.getText().toString());
                            // Se esconde layoutMultiplayer y llama al método de mostrar waitingClient
                            layoutMultiplayer.setVisibility(View.GONE);
                            showWaitingClient();
                        }
                    }
                });

            }
        });


        // Se registra el receptor
        IntentFilter intentFilter = new IntentFilter("com.example.NOTIFICATION_DATA");
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationDataReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Desregistrar el receptor
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationDataReceiver);

        // Si existia master o cliente
        if (this.config != null) {
            // Si la sala era creada
            if (waitingMaster.getVisibility() == View.VISIBLE) {
                config.deleteLobby();
                // Si era una union a una sala
            } else {
                config.leftLobby();
            }

            config = null;
        }
    }

    // Método que prepara un Broadcast en caso de recibir notificaciones
    private BroadcastReceiver notificationDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Recoge el tipo de notificación que es
            String msg = intent.getStringExtra("message");
            // Si es una peticion de unirse al ser master, recoge los datos necesarios y llama al método
            if (msg.equals("join")) {
                String clientToken = intent.getStringExtra("clientToken");
                Personaje character = (Personaje) intent.getSerializableExtra("character");
                addClient(clientToken,character);
                size = intent.getIntExtra("size",-1);
                sizeLobby.setText(size+"/4");

            } else if (msg.equals("hostToken")) {
                // Si he recibido un hostToken significa que quiero unirme a una sala como cliente
                hostToken = intent.getStringExtra("hostToken");
            } else if (msg.equals("left")) {
                // Un cliente desea salir
                String clientToken = intent.getStringExtra("clientToken");
                deleteClient(clientToken);
                size--;
                sizeLobby.setText(size+"/4");
                config.lobbyDown(size);
            } else if (msg.equals("lobbyDelete")) {
                // El master ha borrado la sala
                waitingClient.setVisibility(View.GONE);
                selectClient.setVisibility(View.GONE);
                layoutMultiplayer.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "El master ha borrado la sala: "+clientPW.getText().toString(),Toast.LENGTH_SHORT).show();
            } else if (msg.equals("startGame")) {
                // El master ha empezado la sala
                clientChars = (Personaje[]) intent.getSerializableExtra("characters");
                startGameClient();
            }
        }
    };

    // Método que prepara la sala para jugar
    private void createLobby(String password){
        // Se inicializa la lista de clientes y personajes
        clientTokens = new String[4];
        clientChars = new Personaje[4];

        int pwd = Integer.parseInt(password);
        // Se crea el archivo de config
        this.config = new MultiConfig(pwd, getBaseContext());
        // Se obtiene el token del disposivo y se procede a crear la sala
        this.mainToken = config.getMainToken();
        this.config.createLobby();
    }

    // Método que muestra waitingMaster
    private void showWaitingMaster() {
        // Inicializa las variables del layout
        sizeLobby = findViewById(R.id.showSize);
        startGame = findViewById(R.id.startGame_button);
        cancelLobby = findViewById(R.id.cancelLobby_button);
        waitingMaster.setVisibility(View.VISIBLE);

        cancelLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.deleteLobby();
                config = null;
                waitingMaster.setVisibility(View.GONE);
                layoutMultiplayer.setVisibility(View.VISIBLE);
                selectMaster.setVisibility(View.GONE);
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.startGame(clientChars);
                startGameMaster();
            }
        });
    }

    // Método para comenzar la partida master
    private void startGameMaster() {

        Intent intent = new Intent(MultiSelector.this, Master.class);

        intent.putExtra("lobbyName",masterPW.getText().toString());
        intent.putExtra("mainToken",mainToken);
        intent.putExtra("clientTokens",clientTokens);
        intent.putExtra("player1",clientChars[0]);
        intent.putExtra("player2",clientChars[1]);
        intent.putExtra("player3",clientChars[2]);
        intent.putExtra("player4",clientChars[3]);

        Toast.makeText(getApplicationContext(), "Comienza la partida",Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

    // Método para comenzar la partida cliente
    private void startGameClient() {
        Intent intent = new Intent(MultiSelector.this, Client.class);

        intent.putExtra("lobbyName",clientPW.getText().toString());
        intent.putExtra("mainToken",mainToken);
        intent.putExtra("clientChars",clientChars);
        intent.putExtra("mainChar",character);

        Toast.makeText(getApplicationContext(), "Comienza la partida",Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }


    private void showWaitingClient() {
        // Inicializa las variables del layout
        cancelJoin = findViewById(R.id.cancelJoin_button);
        waitingClient.setVisibility(View.VISIBLE);

        cancelJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.leftLobby();
                config = null;
                waitingClient.setVisibility(View.GONE);
                selectClient.setVisibility(View.GONE);
                layoutMultiplayer.setVisibility(View.VISIBLE);
            }
        });
    }


    // Método que inizializa el spinner con la lista de personajes
    private void createSpinner() {
        spinnerChars = findViewById(R.id.spinner_characters);

        // Arraylist de personajes
        JSONHelper helper = new JSONHelper(getBaseContext());
        Personaje[] chars = helper.getChars();
        // Añade el array normal de Personajes a un Arraylist para el spinner
        charlist = new ArrayList<>();
        for (Personaje p: chars) {
            charlist.add(p);
        }
        ArrayList<String> charString = new ArrayList<>();
        for (Personaje p: charlist) {
            charString.add("Nombre: "+p.getNombre()+", Nivel: "+p.getNivel()+", Clase: "+p.getClase());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, charString);
        spinnerChars.setAdapter(adapter);


        spinnerChars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene el personaje seleccionado del adaptador
                character = charlist.get(position);
                spinnerChars.setSelected(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción a realizar cuando no se selecciona ningún elemento
                spinnerChars.setSelected(false);
            }
        });

    }

    private void findLobby(String password) {
        int pwd = Integer.parseInt(password);
        this.config = new MultiConfig(pwd, getBaseContext());
        this.mainToken = this.config.getMainToken();
        this.config.foundLobby(this.character);
    }

    // Método que añade el cliente a la sala
    private void addClient(String client, Personaje character) {
        // Recorre la lista de tokens de los clientes
        for (int i = 0; i < clientTokens.length; i++) {
            // Si hay un hueco en el espacio de la sala, añade el cliente
            if (clientTokens[i] == null) {
                clientTokens[i] = client;
                clientChars[i] = character;
                i = clientTokens.length;
            }
        }


    }

    // Método que elimina un cliente de la sala
    private void deleteClient(String client) {
        for (int i = 0; i < clientTokens.length; i++) {
            // Si el token del cliente que desea irse coincide con uno de los clientes de la sala
            if (clientTokens[i].equals(client)) {
                clientTokens[i] = null;
                clientChars[i] = null;
                i = clientTokens.length;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
