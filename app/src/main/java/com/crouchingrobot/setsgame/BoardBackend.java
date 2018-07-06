package com.crouchingrobot.setsgame;

import android.graphics.Color;
import android.util.Log;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;


public class BoardBackend {
    private Spot[][] spotsInitial = null;
    private Spot[][] spotsTemp = null;
    private int hight = 0;
    private int width = 0;
    private char currentColor = 'X';
    private int numInSet = 0;
    //Point the furthest down square that exists
    private Spot[] columnHeads = null;
    private boolean boardClear = false;

    //Use this constructor to read in a file as the board
    public BoardBackend(InputStream inIS, int inHight, int inWidth) {
        hight = inHight;
        width = inWidth;
        spotsInitial = new Spot[width][hight];
        spotsTemp = new Spot[width][hight];
        columnHeads = new Spot[width];

        Scanner inFile = new Scanner(inIS);
        //Read in the spaces from the file
        for(int i = 0; i<hight; i++){
            String inTempLine = inFile.nextLine();
            for(int j = 0; j<width; j++){
                //Declare the spots in the spots array
                if(i!=0){
                    spotsInitial[j][i]=new Spot(spotsInitial[j][i-1],inTempLine.charAt(j));
                }else {
                    spotsInitial[j][i] = new Spot(null, inTempLine.charAt(j));
                }
            }
        }
        copyBoard(spotsTemp, spotsInitial);
    }

    //Use this constructor to generate a random game on the fly
    public BoardBackend(int inHight, int inWidth) {
        hight = inHight;
        width = inWidth;
        spotsInitial = new Spot[width][hight];
        spotsTemp = new Spot[width][hight];
        columnHeads = new Spot[width];

        generateAndFillBoard();

        copyBoard(spotsTemp, spotsInitial);
    }

    private void generateAndFillBoard(){
        char [][] randBoard = new char [width][hight];
        Random rand = new Random(System.nanoTime());
        for (int p = 0; p < hight; p++) {
            for (int j = 0; j < width; j++) {
                randBoard[j][p] = 'X';
            }
        }

        boolean hitBot = false;
        int totalSets = 0;
        while (hitBot == false && totalSets < 15) {
            totalSets++;
            int color = rand.nextInt(5);
            int[] xMem = new int[3];
            int[] yMem = new int[3];
            for (int j = 0; j < 3; j++) {

                int index = rand.nextInt(5);
                int row = 11;
                boolean stop = false;
                while (!stop && randBoard[index][row] == 'X') {
                    row--;
                    if (row == -1) {
                        stop = true;
                    }
                }
                if (row < 11) {
                    switch (color) {
                        case 0:
                            randBoard[index][row + 1] = 'R';
                            break;
                        case 1:
                            randBoard[index][row + 1] = 'G';
                            break;
                        case 2:
                            randBoard[index][row + 1] = 'B';
                            break;
                        case 3:
                            randBoard[index][row + 1] = 'Y';
                            break;
                        case 4:
                            randBoard[index][row + 1] = 'P';
                            break;
                    }
                    xMem[j] = index;
                    yMem[j] = row + 1;
                } else {
                    hitBot = true;
                    for (int k = 0; k < j; k++) {
                        randBoard[xMem[k]][yMem[k]] = 'X';
                    }
                }
            }

        }

        for(int i = 0; i<hight; i++){
            for(int j = 0; j<width; j++){
                //Declare the spots in the spots array
                if(i!=0){
                    spotsInitial[j][i]=new Spot(spotsInitial[j][i-1],randBoard[j][i]);
                }else {
                    spotsInitial[j][i] = new Spot(null, randBoard[j][i]);
                }
            }
        }
    }

    public int getSpotColor(int inX, int inY){
        int temp = 0;
        switch (spotsTemp[inX][inY].getColor()){
            case 'P':
                temp = Color.rgb(120,0,120);
                break;
            case 'B':
                temp = Color.rgb(0,0,255);
                break;
            case 'R':
                temp = Color.rgb(255,0,0);
                break;
            case 'G':
                temp = Color.rgb(0,255,0);
                break;
            case 'Y':
                temp = Color.rgb(120,120,0);
                break;
            default:
                temp = Color.rgb(0,0,0);
                break;

        }
        return temp;
    }

    public int getHight(){
        return this.hight;
    }

    public int getWidth(){
        return this.width;
    }

    private void copyBoard(Spot[][] temp, Spot[][] saved){
        for(int i = 0; i<hight; i++){
            for(int j =0; j<width; j++){
                if(i!=0){
                    temp[j][i] = new Spot(saved[j][i],temp[j][i-1]);
                }else{
                    temp[j][i] = new Spot(saved[j][i],null);
                }
                //Update the columnHeads if not a blank spot
                if(temp[j][i].getColor() != 'X'){
                    columnHeads[j]= temp[j][i];
                }
            }
        }
    }

    public void columnClickHandler(int column){
        if(columnHeads[column] != null){
            if(numInSet == 0){
                //set is starting so loss is not possible
                currentColor=columnHeads[column].getColor();
                numInSet++;
                //set the peice at the head of the column to X and set the columnHead to the parent
                columnHeads[column]=columnHeads[column].removePeice();
            }else if(columnHeads[column].getColor() == currentColor) {
                if(numInSet == 2){
                    numInSet = 0;
                    currentColor = 'X';
                    //set the peice at the head of the column to X and set the columnHead to the parent
                    columnHeads[column]=columnHeads[column].removePeice();
                    boardClear = true;
                    for(int i=0;i<columnHeads.length;i++) {
                        if (columnHeads[i] != null) {
                            boardClear = false;
                        }
                    }
                }else{
                    numInSet++;
                    //set the peice at the head of the column to X and set the columnHead to the parent
                    columnHeads[column]=columnHeads[column].removePeice();
                }

            }else{
                boardReset();
            }
            Log.i("ColumnClick","current color = "+currentColor + "num in set = "+numInSet);
        }
    }

    public void boardReset(){
        copyBoard(spotsTemp, spotsInitial);
        currentColor = 'X';
        numInSet = 0;
    }

    public boolean isClear(){
        return boardClear;
    }


}
