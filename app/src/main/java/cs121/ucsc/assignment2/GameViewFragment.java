package cs121.ucsc.assignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class GameViewFragment extends Fragment implements View.OnClickListener{
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    int turncounter = 0;
    private int winner = 0;
    Model m = new Model();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_game_view, container, false);
        RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.gameviewxml);
        //button on click
        Button zero =  rootView.findViewById(R.id.button9);
        zero.setOnClickListener(this);
        Button one =  rootView.findViewById(R.id.button4);
        one.setOnClickListener(this);
        Button two =  rootView.findViewById(R.id.button5);
        two.setOnClickListener(this);
        Button three =  rootView.findViewById(R.id.button6);
        three.setOnClickListener(this);
        Button four =  rootView.findViewById(R.id.button7);
        four.setOnClickListener(this);
        Button five =  rootView.findViewById(R.id.button8);
        five.setOnClickListener(this);
        Button six =  rootView.findViewById(R.id.button10);
        six.setOnClickListener(this);

        Button exit = rootView.findViewById(R.id.SaveNExit);
        exit.setOnClickListener(this);

        Button back = rootView.findViewById(R.id.back);
        back.setOnClickListener(this);

        TextView tv = (TextView) rootView.findViewById(R.id.greenwin);
        TextView tv2 = (TextView) rootView.findViewById(R.id.redwin);
        TextView tv3 = (TextView) rootView.findViewById(R.id.draw);
        tv.setVisibility(rootView.INVISIBLE);
        tv2.setVisibility(rootView.INVISIBLE);
        tv3.setVisibility(rootView.INVISIBLE);


        rl.addView(new DrawGreenCircView(getActivity()));
        rl.addView(new DrawGridView(getActivity()));
        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button9:
                turncounter++;
                m.putDisk( 0, turncounter%2);
                winner = m.getWinner();
                setWinner();
                break;
            case R.id.button4:
                turncounter++;
                m.putDisk( 1, turncounter%2);
                winner = m.getWinner();
                setWinner();
                break;
            case R.id.button5:
                turncounter++;
                m.putDisk( 2, turncounter%2);
                winner = m.getWinner();
                setWinner();
                break;
            case R.id.button6:
                turncounter++;
                m.putDisk( 3, turncounter%2);
                winner = m.getWinner();
                setWinner();
                break;
            case R.id.button7:
                turncounter++;
                m.putDisk( 4, turncounter%2);
                winner = m.getWinner();
                setWinner();
                break;
            case R.id.button8:
                turncounter++;
                m.putDisk( 5, turncounter%2);
                winner = m.getWinner();
                setWinner();
                break;
            case R.id.button10:
                turncounter++;
                m.putDisk( 6, turncounter%2);
                winner = m.getWinner();
                setWinner();
                break;
            case R.id.SaveNExit:
                ExitActivity();
                break;
            case R.id.back:
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateGameView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateGameView(mCurrentPosition);
        }
    }
    //position == 0 = new game
    //1 = load
    //2 = exit
    public void clearGrid(){
        for(int i = 0; i<=5;i++){
            for(int j=0;j<=6;j++){
                Model.board[i][j] = 0;
            }
        }
    }
    public void updateGameView(int position) {
        if(position == 1){
            Model.board = getSavedMatrix();
            turncounter = getSavedTurn();
        }
        else if(position == 0){
            clearGrid();
        }

    }
    public int getDrawXCord(int j, int width, int cellsize){
        int xCord = 0;
        switch(j){
            case 0:
                xCord= (width/7)-(cellsize/2);
                break;
            case 1:
                xCord= (width/7)*2-(cellsize/2);
                break;
            case 2:
                xCord= (width/7)*3-(cellsize/2);
                break;
            case 3:
                xCord= (width/7)*4-(cellsize/2);
                break;
            case 4:
                xCord= (width/7)*5-(cellsize/2);
                break;
            case 5:
                xCord= (width/7)*6-(cellsize/2);
                break;
            case 6:
                xCord= (width/7)*7-(cellsize/2);
                break;
        }
        return xCord;
    }
    public int getDrawYCord(int i, int height, int cellsize){
        int yCord = 0;

        switch(i){
            case 0:
                yCord = height-(cellsize*5 +(cellsize/2));
                break;
            case 1:
                yCord = height-(cellsize*4 +(cellsize/2));
                break;
            case 2:
                yCord = height-(cellsize*3 +(cellsize/2));
                break;
            case 3:
                yCord = height-(cellsize*2 +(cellsize/2));
                break;
            case 4:
                yCord = height-(cellsize +(cellsize/2));
                break;
            case 5:
                yCord = height-(cellsize/2);
                break;

        }
        return yCord;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public class DrawGreenCircView extends View {
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        public DrawGreenCircView(Context context) {
            super(context);
        }
        @Override
        public void onDraw(Canvas canvas) {
            paint.setColor(Color.GREEN);
            paint2.setColor(Color.RED);
            float cellsize = (getWidth()*1.0f) / Model.num_col;
            for(int i=0;i<=5;i++){
                for(int j = 0; j<=6; j++){
                    if(m.board[i][j]==1){
                        canvas.drawCircle(getDrawXCord(j, getWidth(),(int)cellsize), getDrawYCord(i,getHeight(),(int)cellsize), 55,paint);
                    }
                    if(m.board[i][j] == 2){
                        canvas.drawCircle(getDrawXCord(j,getWidth(),(int)cellsize), getDrawYCord(i,getHeight(),(int)cellsize), 55,paint2);
                    }
                }
            }
            invalidate();

        }
    }

    public class DrawGridView extends View {
        Paint paint = new Paint();

        public DrawGridView(Context context) {
            super(context);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(10);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            float cellsize = (getWidth()*1.0f) / Model.num_col;
            System.out.println(cellsize);
            float yTop = getHeight() - cellsize*6;
            for(int i = 0; i<Model.num_col;i++){
                float xAxis = i*cellsize;
                canvas.drawLine(xAxis, yTop, xAxis,  getHeight(), paint);
            }
            for(int i = 0; i<Model.num_row;i++){
                float yAxis =  yTop+cellsize*i;
                canvas.drawLine(0, yAxis, getWidth(), yAxis, paint);
            }


        }


    }

    public void ExitActivity(){
        saveMatrix(m.board);
        saveTurn(turncounter);
        getActivity().finish();
        System.exit(0);
    }
    //Save user input
    private void saveMatrix(int[][] mat){
        try{
            FileOutputStream fileOutputStream = getActivity().openFileOutput("Data.txt", getActivity().MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(mat);
            out.close();
            fileOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
    private void saveTurn(int turn){
        try{
            FileOutputStream fileOutputStream = getActivity().openFileOutput("Data1.txt", getActivity().MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(turn);
            out.close();
            fileOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
    private int getSavedTurn(){
        int t =0;
        try{
            FileInputStream inputStream = getActivity().openFileInput("Data1.txt");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            t = (int) in.readObject();
            in.close();
            inputStream.close();
        }
        catch(IOException|ClassNotFoundException e){
            e.printStackTrace();}
        return t;
    }
    //Fetch user input
    private int[][] getSavedMatrix(){
        int[][] mat = new int[6][7];
        try{
        FileInputStream inputStream = getActivity().openFileInput("Data.txt");
        ObjectInputStream in = new ObjectInputStream(inputStream);
        mat = (int[][]) in.readObject();
        in.close();
        inputStream.close();
    }
        catch(IOException|ClassNotFoundException e){
        e.printStackTrace();}
        return mat;
    }
    private void setWinner(){
        TextView tv1 = (TextView) getView().findViewById(R.id.greenwin);
        TextView tv2 = (TextView) getView().findViewById(R.id.redwin);
        TextView tv3 = (TextView) getView().findViewById(R.id.draw);
        if(winner ==1){
            tv1.setVisibility(getView().VISIBLE);
            clearGrid();
        }
        else if(winner==2){
            tv2.setVisibility(getView().VISIBLE);
            clearGrid();
        }
        else if(winner==3){
            tv3.setVisibility(getView().VISIBLE);
            clearGrid();
        }

    }






}
