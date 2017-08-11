package baltamon.mx.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String currentPlayer = "X"; //it can change to O
    String[][] matrix = new String[3][3];
    Button[] buttons = new Button[9];
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        getButtons();
        cleanMatrix(null);
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tic Tac Toe");
        }
    }

    public void onCellClicked(View view) {
        if (isGameCompleted())
            Toast.makeText(this, "No more possible moves", Toast.LENGTH_SHORT).show();
        else
            playerMove(view);
    }

    public void playerMove(View view) {
        String[] coordinates = view.getTag().toString().split(",");
        Log.i("Message", coordinates[0] + "," + coordinates[1]);
        int x = Integer.valueOf(coordinates[0]);
        int y = Integer.valueOf(coordinates[1]);

        Button button = (Button) view;

        if (button.getText().toString().isEmpty()) {
            count++;
            button.setText(currentPlayer);
            fillMatrix(x, y);
        }
    }

    public void fillMatrix(int x, int y) {
        matrix[y][x] = currentPlayer;
        if (evaluateGame(x, y))
            showWinner();
        else
            changeCurrentPlayer();
    }

    public boolean evaluateGame(int x, int y) {
        int v1 = 0, v2 = 0, v3 = 0, v4 = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][x].equals(currentPlayer)) v1++; //evaluate column
            if (matrix[y][i].equals(currentPlayer)) v2++; //evaluate line
            if (matrix[i][i].equals(currentPlayer)) v3++; //evaluate diagonal
            if (matrix[i][matrix.length - (1 + i)].equals(currentPlayer)) v4++; //evaluate reverse diagonal
        }

        return (v1 == 3 || v2 == 3 || v3 == 3 || v4 == 3);
    }

    public void showWinner() {
        TextView textView = (TextView) findViewById(R.id.tv_winner);
        textView.setText("Winner is " + currentPlayer);
        count = 9;
    }

    public void changeCurrentPlayer() {
        if (currentPlayer.equals("X")) currentPlayer = "O";
        else currentPlayer = "X";
    }

    public boolean isGameCompleted() {
        return count == 9;
    }

    public void cleanMatrix(View view) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = "-";
            }
        }

        for (Button button : buttons) {
            button.setText("");
        }

        count = 0;

        TextView textView = (TextView) findViewById(R.id.tv_winner);
        textView.setText("Winner");

        showToast("Game Started");
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void getButtons(){
        buttons[0] = (Button) findViewById(R.id.btn_01);
        buttons[1] = (Button) findViewById(R.id.btn_02);
        buttons[2] = (Button) findViewById(R.id.btn_03);
        buttons[3] = (Button) findViewById(R.id.btn_04);
        buttons[4] = (Button) findViewById(R.id.btn_05);
        buttons[5] = (Button) findViewById(R.id.btn_06);
        buttons[6] = (Button) findViewById(R.id.btn_07);
        buttons[7] = (Button) findViewById(R.id.btn_08);
        buttons[8] = (Button) findViewById(R.id.btn_09);
    }
}