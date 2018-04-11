/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.t2dbabz.tictactoe;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class VsComputer extends AppCompatActivity {

    //Declaring the variables as class variables(fields)
    private boolean turn = false;
    private char table[][] = new char[3][3];
    int count = 0;
    int winC = 0;

    int scoreX = 0;
    int scoreO = 0;


    String first = "";
    String second = "";

    private Button setX;
    private Button setO;

    private char firstC;
    private char secondC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_3x3);

        //Casting and referencing views by their Ids

        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        playAgain(findViewById(R.id.tableLayout));

        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow r = (TableRow) T.getChildAt(y);
                for (int x = 0; x < r.getChildCount(); x++) {
                    View V = r.getChildAt(x);
                    V.setOnClickListener(new playGame(x, y));
                    disableAllButtons();
                }
            }
        }
        //Button set for new game
        Button c = (Button) findViewById(R.id.playAgain);
        c.setOnClickListener(new OnClickListener() {
            public void onClick(View n) {
                playAgain(n);
            }

        });

        //Links to the current Activity
        Button three = (Button) findViewById(R.id.three);
        three.setEnabled(false);

        //Links to the VSComputer5x5 Activity
        Button five = (Button) findViewById(R.id.five);
        five.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VsComputer5x5.class));
            }
        });



        //select X to play with
        //Computer play with O
        setX = (Button) findViewById(R.id.setX);
        setX.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                first = "X";
                second = "O";

                firstC = 'X';
                secondC = 'O';

                setX.setEnabled(false);

                enableAllButtons();

            }
        });

        //select O to play with
        //Computer play with X
        setO = (Button) findViewById(R.id.setO);
        setO.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                first = "O";
                second = "X";

                firstC = 'O';
                secondC = 'X';
                setO.setEnabled(false);

                enableAllButtons();
            }
        });

    }

    //the class handles the game
    private class playGame implements OnClickListener {

        private int x = 0;
        private int y = 0;

        playGame(int x, int y) {
            this.x = x;
            this.y = y;

        }

        //Coordinating how computer plays
        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                Button B = (Button) view;
                table[x][y] = firstC;
                B.setText(first);
                B.setEnabled(false);
                turn = !turn;
                count++;
                if (count == 1) {
                    firstMove();
                }
                if (count == 3) {
                    defend();
                }
                declareWinner();

                if (count > 4) {
                    goforwin();
                    declareWinner();
                }

            }
        }
    }

    //new game
    public void playAgain(View view) {
        count = 0;
        turn = false;
        table = new char[3][3];

        resetButtons();

    }

    //reset the buttons
    private void resetButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow Re = (TableRow) T.getChildAt(y);
                for (int x = 0; x < Re.getChildCount(); x++) {
                    if (Re.getChildAt(x) instanceof Button) {
                        Button B = (Button) Re.getChildAt(x);
                        B.setText(" ");
                        B.setEnabled(true);
                        B.setTextColor(Color.parseColor("#000080"));
                        count = 0;
                        winC = 0;

                    }
                }
            }
        }
    }

    //firstMove the computer is to make
    public void firstMove() {
        if (table[1][1] != firstC) {
            Button B = (Button) findViewById(R.id.b2);
            B.setText(second);
            count++;
            table[1][1] = secondC;
        } else {
            Button B = (Button) findViewById(R.id.c1);
            B.setText(second);
            table[2][0] = secondC;
            count++;

        }

    }

    //defend moves the computer is to make
    private void defend() {
        Button B;

        //checking for X
        //diagonal '\'
        //xx-


        if (table[0][0] == firstC) {
            if ((table[1][1] == firstC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }


        //x-x
        if (table[0][0] == firstC) {
            if ((table[2][2] == firstC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][1] == firstC) {
            if ((table[2][2] == firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //diagonal '/'
        //xx-
        if (table[2][0] == firstC) {
            if ((table[1][1] == firstC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //x-x
        if (table[2][0] == firstC) {
            if ((table[0][2] == firstC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == firstC) {
            if ((table[1][1] == firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal1
        //condition1 xx-
        if (table[0][0] == firstC) {
            if ((table[1][0] == firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //condition1 x-x
        if (table[0][0] == firstC) {
            if ((table[2][0] == firstC) && (table[1][0] != firstC && table[1][0] != secondC)) {
                B = (Button) findViewById(R.id.b1);
                B.setText(second);
                table[1][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //condition1 -xx
        if (table[2][0] == firstC) {
            if ((table[1][0] == firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //horizontal2
        //xx-
        if (table[0][1] == firstC) {
            if ((table[1][1] == firstC) && (table[2][1] != firstC && table[2][1] != secondC)) {
                B = (Button) findViewById(R.id.c2);
                B.setText(second);
                table[2][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][1] == firstC) {
            if ((table[2][1] == firstC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][1] == firstC) {
            if ((table[1][1] == firstC) && (table[0][1] != firstC && table[0][1] != secondC)) {
                B = (Button) findViewById(R.id.a2);
                B.setText(second);
                table[0][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal3
        //xx-
        if (table[0][2] == firstC) {
            if ((table[1][2] == firstC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][2] == firstC) {
            if ((table[2][2] == firstC) && (table[1][2] != firstC && table[1][2] != secondC)) {
                B = (Button) findViewById(R.id.b3);
                B.setText(second);
                table[1][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == firstC) {
            if ((table[1][2] == firstC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical1
        //xx-
        if (table[0][0] == firstC) {
            if ((table[0][1] == firstC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][0] == firstC) {
            if ((table[0][2] == firstC) && (table[0][1] != firstC && table[0][1] != secondC)) {
                B = (Button) findViewById(R.id.a2);
                B.setText(second);
                table[0][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == firstC) {
            if ((table[0][1] == firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical2
        //xx-
        if (table[1][0] == firstC) {
            if ((table[1][1] == firstC) && (table[1][2] != firstC && table[1][2] != secondC)) {
                B = (Button) findViewById(R.id.b3);
                B.setText(second);
                table[1][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[1][0] == firstC) {
            if ((table[1][2] == firstC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][2] == firstC) {
            if ((table[1][1] == firstC) && (table[1][0] != firstC && table[1][0] != secondC)) {
                B = (Button) findViewById(R.id.b1);
                B.setText(second);
                table[1][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical3
        //xx-
        if (table[2][0] == firstC) {
            if ((table[2][1] == firstC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[2][0] == firstC) {
            if ((table[2][2] == firstC) && (table[2][1] != firstC && table[2][1] != secondC)) {
                B = (Button) findViewById(R.id.c2);
                B.setText(second);
                table[2][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == firstC) {
            if ((table[2][1] == firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        tryForWin();


    }

    private void goforwin() {
        Button B;


        //diagonal '\'
        //xx-
        if (table[0][0] == secondC) {
            if ((table[1][1] == secondC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][0] == secondC) {
            if ((table[2][2] == secondC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][1] == secondC) {
            if ((table[2][2] == secondC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //diagonal '/'
        //xx-
        if (table[2][0] == secondC) {
            if ((table[1][1] == secondC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[2][0] == secondC) {
            if ((table[0][2] == secondC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == secondC) {
            if ((table[1][1] == secondC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal1
        //condition1 xx-
        if (table[0][0] == secondC) {
            if ((table[1][0] == secondC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //condition1 x-x
        if (table[0][0] == secondC) {
            if ((table[2][0] == secondC) && (table[1][0] != firstC && table[1][0] != secondC)) {
                B = (Button) findViewById(R.id.b1);
                B.setText(second);
                table[1][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //condition1 -xx
        if (table[2][0] == secondC) {
            if ((table[1][0] == secondC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }

        //horizontal2
        //xx-
        if (table[0][1] == secondC) {
            if ((table[1][1] == secondC) && (table[2][1] != firstC && table[2][1] != secondC)) {
                B = (Button) findViewById(R.id.c2);
                B.setText(second);
                table[2][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][1] == secondC) {
            if ((table[2][1] == secondC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][1] == secondC) {
            if ((table[1][1] == secondC) && (table[0][1] != firstC && table[0][1] != secondC)) {
                B = (Button) findViewById(R.id.a2);
                B.setText(second);
                table[0][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //horizontal3
        //xx-
        if (table[0][2] == secondC) {
            if ((table[1][2] == secondC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][2] == secondC) {
            if ((table[2][2] == secondC) && (table[1][2] != firstC && table[1][2] != secondC)) {
                B = (Button) findViewById(R.id.b3);
                B.setText(second);
                table[1][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == secondC) {
            if ((table[1][2] == secondC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical1
        //xx-
        if (table[0][0] == secondC) {
            if ((table[0][1] == secondC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[0][0] == secondC) {
            if ((table[0][2] == secondC) && (table[0][1] != firstC && table[0][1] != secondC)) {
                B = (Button) findViewById(R.id.a2);
                B.setText(second);
                table[0][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[0][2] == secondC) {
            if ((table[0][1] == secondC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical2
        //xx-
        if (table[1][0] == secondC) {
            if ((table[1][1] == secondC) && (table[1][2] != firstC && table[1][2] != secondC)) {
                B = (Button) findViewById(R.id.b3);
                B.setText(second);
                table[1][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[1][0] == secondC) {
            if ((table[1][2] == secondC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[1][2] == secondC) {
            if ((table[1][1] == secondC) && (table[1][0] != firstC && table[1][0] != secondC)) {
                B = (Button) findViewById(R.id.b1);
                B.setText(second);
                table[1][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //vertical3
        //xx-
        if (table[2][0] == secondC) {
            if ((table[2][1] == secondC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //x-x
        if (table[2][0] == secondC) {
            if ((table[2][2] == secondC) && (table[2][1] != firstC && table[2][1] != secondC)) {
                B = (Button) findViewById(R.id.c2);
                B.setText(second);
                table[2][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //-xx
        if (table[2][2] == secondC) {
            if ((table[2][1] == secondC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        defend();


    }

    //Computer should look whether there is a win and go for it
    private void tryForWin() {
        Button B;
        //00
        //diagonal /
        if (table[0][0] == secondC) {
            if ((table[1][1] != secondC && table[1][1] != firstC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][0] == secondC) {
            if ((table[1][0] != secondC && table[1][0] != firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][0] == secondC) {
            if ((table[0][1] != secondC && table[0][1] != firstC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //22
        if (table[2][2] == secondC) {
            if ((table[0][0] != secondC && table[0][0] != firstC) && (table[1][1] != firstC && table[1][1] != secondC)) {
                B = (Button) findViewById(R.id.b2);
                B.setText(second);
                table[1][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][2] == secondC) {
            if ((table[1][2] != secondC && table[1][2] != firstC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][2] == secondC) {
            if ((table[2][1] != secondC && table[2][1] != firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }


        //11
        if (table[1][1] == secondC) {
            if ((table[2][2] != secondC && table[2][2] != firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][1] == secondC) {
            if ((table[0][2] != secondC && table[0][2] != firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][1] == secondC) {
            if ((table[2][1] != secondC && table[2][1] != firstC) && (table[0][1] != firstC && table[0][1] != secondC)) {
                B = (Button) findViewById(R.id.c2);
                B.setText(second);
                table[2][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][1] == secondC) {
            if ((table[1][2] != secondC && table[1][2] != secondC) && (table[1][0] != firstC && table[1][0] != secondC)) {
                B = (Button) findViewById(R.id.b1);
                B.setText(second);
                table[1][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //20
        if (table[2][0] == secondC) {
            if ((table[1][1] != secondC && table[1][1] != firstC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][0] == secondC) {
            if ((table[1][0] != secondC && table[1][0] != firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][0] == secondC) {
            if ((table[2][1] != secondC && table[2][1] != firstC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //02
        if (table[0][2] == secondC) {
            if ((table[1][1] != secondC && table[1][1] != firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c1);
                B.setText(second);
                table[2][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][2] == secondC) {
            if ((table[1][2] != secondC && table[1][2] != firstC) && (table[2][2] != firstC && table[2][2] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][2] == secondC) {
            if ((table[0][1] != secondC && table[0][1] != firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //10
        if (table[1][0] == secondC) {
            if ((table[2][0] != secondC && table[2][0] != firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][0] == secondC) {
            if ((table[1][1] != secondC && table[1][1] != firstC) && (table[1][2] != firstC && table[1][2] != secondC)) {
                B = (Button) findViewById(R.id.b3);
                B.setText(second);
                table[1][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //01
        if (table[0][1] == secondC) {
            if ((table[1][1] != secondC && table[1][1] != firstC) && (table[2][1] != firstC && table[2][1] != secondC)) {
                B = (Button) findViewById(R.id.c2);
                B.setText(second);
                table[2][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[0][1] == secondC) {
            if ((table[0][2] != secondC && table[0][2] != firstC) && (table[0][0] != firstC && table[0][0] != secondC)) {
                B = (Button) findViewById(R.id.a1);
                B.setText(second);
                table[0][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //21
        if (table[2][1] == secondC) {
            if ((table[1][1] != secondC && table[1][1] != firstC) && (table[0][1] != firstC && table[0][1] != secondC)) {
                B = (Button) findViewById(R.id.a2);
                B.setText(second);
                table[0][1] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[2][1] == secondC) {
            if ((table[2][2] != secondC && table[2][2] != firstC) && (table[2][0] != firstC && table[2][0] != secondC)) {
                B = (Button) findViewById(R.id.c3);
                B.setText(second);
                table[2][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        //12
        if (table[1][2] == secondC) {
            if ((table[2][2] != secondC && table[2][2] != firstC) && (table[0][2] != firstC && table[0][2] != secondC)) {
                B = (Button) findViewById(R.id.a3);
                B.setText(second);
                table[0][2] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
                return;
            }
        }
        if (table[1][2] == secondC) {
            if ((table[1][1] != secondC && table[1][1] != firstC) && (table[1][0] != firstC && table[1][0] != secondC)) {
                B = (Button) findViewById(R.id.b1);
                B.setText(second);
                table[1][0] = secondC;
                turn = !turn;
                count++;
                B.setEnabled(false);
            }
        }
    }

    //Check if there is a winner
    //If there is a winner, display a toast message indicating who wins
    //change to color of the winning pieces
    //Add 1 to the score of the winner
    private void declareWinner() {

        TextView scoreXTextX = (TextView) findViewById(R.id.xResult);
        TextView scoreXTextO = (TextView) findViewById(R.id.oResult);

        //checking for X
        //diagonal '\'
        if (table[0][0] == firstC) {
            if ((table[1][1] == firstC) && (table[2][2] == firstC)) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //diagonal '/'
        if (table[2][0] == firstC) {
            if ((table[1][1] == firstC) && (table[0][2] == firstC)) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //horizontal1
        if (table[0][0] == firstC) {
            if (table[1][0] == firstC && table[2][0] == firstC) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //horizontal2
        if (table[0][1] == firstC) {
            if (table[1][1] == firstC && table[2][1] == firstC) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //horizontal3
        if (table[0][2] == firstC) {
            if (table[1][2] == firstC && table[2][2] == firstC) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //vertical1
        if (table[0][0] == firstC) {
            if (table[0][1] == firstC && table[0][2] == firstC) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //vertical2
        if (table[1][0] == firstC) {
            if (table[1][1] == firstC && table[1][2] == firstC) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //vertical3
        if (table[2][0] == firstC) {
            if (table[2][1] == firstC && table[2][2] == firstC) {
                scoreX++;
                scoreXTextX.setText(" " + scoreX);
                Toast.makeText(getApplicationContext(), "X wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //checking secondC
        //diagonal '\'
        if (table[0][0] == secondC) {
            if ((table[1][1] == secondC) && (table[2][2] == secondC)) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //diagonal '/'
        if (table[2][0] == secondC) {
            if ((table[1][1] == secondC) && (table[0][2] == secondC)) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //horizontal1
        if (table[0][0] == secondC) {
            if (table[1][0] == secondC && table[2][0] == secondC) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //horizontal2
        if (table[0][1] == secondC) {
            if (table[1][1] == secondC && table[2][1] == secondC) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //horizontal3
        if (table[0][2] == secondC) {
            if (table[1][2] == secondC && table[2][2] == secondC) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //vertical1
        if (table[0][0] == secondC) {
            if (table[0][1] == secondC && table[0][2] == secondC) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //vertical2
        if (table[1][0] == secondC) {
            if (table[1][1] == secondC && table[1][2] == secondC) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        //vertical3
        if (table[2][0] == secondC) {
            if (table[2][1] == secondC && table[2][2] == secondC) {
                scoreO++;
                scoreXTextO.setText(" " + scoreO);
                Toast.makeText(getApplicationContext(), "O wins", Toast.LENGTH_LONG).show();
                disableAllButtons();
                count = 0;
            }
        }
        if (count == 9 && winC == 0) {
            Toast.makeText(getApplicationContext(), "Match Draw", Toast.LENGTH_LONG).show();
            count = 0;
        }


    }


    //disable the buttons when there is a win
    private void disableAllButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
    }

    //enable the buttons when there is a win
    private void enableAllButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setEnabled(true);
                    }
                }
            }
        }
    }

    //resets the game and including the scoreboard

    public void reset(View view) {
        onRestart();
    }

    //restart method called the reset method
    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
    }



}
