package com.example.malayalamdialoguesquiz;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FirstFragment extends Fragment {

    MainActivity main_act = (MainActivity) getActivity();

    boolean is_counter_running = false;
    CountDownTimer countDownTimer;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }
    public class MovieData{
        public String dialogue;
        public String movie;

        public MovieData(String dialogue, String movie){
            this.dialogue = dialogue;
            this.movie = movie;
        }
        public void set_dialogue(String dialogue){
            this.dialogue = dialogue;
        }
        public void set_movie(String movie){
            this.movie = movie;
        }
    }

    private ArrayList<MovieData> get_csv_data(){
        // this 2d array will store each movie dialogue and the corresponding movie name
//        List<List> movie_items = new ArrayList<List>();
//        List<String> dialogue_and_movie_name = new ArrayList<String>();
        List<MovieData> movie_data = new ArrayList<MovieData>();
        // read the dialogue csv file
        InputStream is = getResources().openRawResource(R.raw.my_file);
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        StringBuffer stringBuffer = new StringBuffer("");
        // for reading one line
        String line = "";
        // keep reading till readLine returns null
        try {
            while (line != null) {
                line = reader.readLine();
                while (true) {
                    System.out.println("printed just inside while");
                    if(line == null)
                        System.out.print("line is null");
                    else if (line.contains("\t")){
                        System.out.println("line has tabbed character");
                        System.out.println("and that line is: " + line);
                    }
                    else
                    {
                        System.out.println("some other problem");
                    }
                    if (line != null && !line.contains("\t")) {
                        System.out.println("printed just inside if condition");
                        stringBuffer.append(line);
                        stringBuffer.append("\n");
                        System.out.println("line: " + stringBuffer.toString());
                        line = reader.readLine();
                    } else
                        break;
                }
                if (line == null) {
                    break;
                }
                String[] final_line = line.split("\t");
                stringBuffer.append(final_line[0]);
                System.out.println("stringBuffer: " + stringBuffer.toString());
                movie_data.add(new MovieData(stringBuffer.toString(), final_line[1]));
                stringBuffer.delete(0, stringBuffer.length());
            }

            return (ArrayList<MovieData>) movie_data;
        }
        catch (IOException e1) {
            Log.e("MainActivity", "Error", e1);
            e1.printStackTrace();
            return null;
        }
    }

    private ArrayList<String> get_movie_names(){
        // get a list of movies (for other options)
        final ArrayList<String> movie_list = new ArrayList<String>();
        try {
            InputStream is_movie_list = getResources().openRawResource(R.raw.movie_list);
            final BufferedReader reader_movie_list = new BufferedReader(
                    new InputStreamReader(is_movie_list, Charset.forName("UTF-8")));
            // buffer for storing file contents in memory
            String line_movie_list = "";
            // keep reading till readLine returns null
            while ((line_movie_list = reader_movie_list.readLine()) != null) {
                movie_list.add(line_movie_list);
            }
            return (ArrayList<String>) movie_list;
        }
        catch (IOException e1) {
            Log.e("MainActivity", "Error", e1);
            e1.printStackTrace();
            return null;
        }
    }

    private void set_question_text(View view, String dialogue){
        TextView dialogue_view =  view.findViewById(R.id.dialogue_text);
        if (dialogue_view == null){
            System.out.println("dialogue_view is null");
        }
        else {
            dialogue_view.setText(dialogue);
            // show slide animation for textview
            Animation RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
            ScrollView scrollView = view.findViewById(R.id.scrollView2);
            scrollView.startAnimation(RightSwipe);
        }
    }

    private Integer set_options_text(View view, ArrayList<String> movie_list, String correct_answer ){
        Integer ans_index;
        Random rand = new Random();
        final Button _option1 = view.findViewById(R.id.option1);
        final Button _option2 = view.findViewById(R.id.option2);
        final Button _option3 = view.findViewById(R.id.option3);
        final Button _option4 = view.findViewById(R.id.option4);

        //give animation to options
        Animation RightSwipe;
        RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option1);
        _option1.startAnimation(RightSwipe);
        RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option2);
        _option2.startAnimation(RightSwipe);
        RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option3);
        _option3.startAnimation(RightSwipe);
        RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option4);
        _option4.startAnimation(RightSwipe);

        Collections.shuffle(movie_list);
        for (int i=0, count = 0; count<4 && i < movie_list.size(); i++) {
            if (movie_list.get(i) == correct_answer)
                i++;
            else {
                if (count == 0) {
                    _option1.setText(movie_list.get(i));
                    i++;
                    count++;
                } else if (count == 1) {
                    _option2.setText(movie_list.get(i));
                    i++;
                    count++;
                } else if (count == 2) {
                    _option3.setText(movie_list.get(i));
                    i++;
                    count++;
                } else if (count == 3) {
                    _option4.setText(movie_list.get(i));
                    i++;
                    count++;
                }
            }
        }

        // set the correct option to a random button among the options
        ans_index = rand.nextInt(4);
        if (ans_index == 0)
            _option1.setText(correct_answer);
        else if (ans_index == 1)
            _option2.setText(correct_answer);
        else if (ans_index == 2)
            _option3.setText(correct_answer);
        else if(ans_index == 3)
            _option4.setText(correct_answer);
        else
            System.out.println("problem occurred in setting the correct answer option text (ans_index out of range");
        return ans_index;
    }

    private boolean check_ans_with_button(Integer[] ans_idx, Integer button_no) {
        if (ans_idx[0] == button_no-1)
            return true;
        else
            return false;
    }

    @SuppressLint("ResourceAsColor")
    private void color_the_answers(View view, Integer ans_idx) {
        view.findViewById(R.id.option1).setBackgroundColor(R.color.wrong_answer_red);
        view.findViewById(R.id.option2).setBackgroundColor(R.color.wrong_answer_red);
        view.findViewById(R.id.option3).setBackgroundColor(R.color.wrong_answer_red);
        view.findViewById(R.id.option4).setBackgroundColor(R.color.wrong_answer_red);

        if(ans_idx == 0)
            view.findViewById(R.id.option1).setBackgroundColor(R.color.correct_answer_green);
        else if(ans_idx == 1)
            view.findViewById(R.id.option2).setBackgroundColor(R.color.correct_answer_green);
        else if(ans_idx == 2)
            view.findViewById(R.id.option3).setBackgroundColor(R.color.correct_answer_green);
        else
            view.findViewById(R.id.option4).setBackgroundColor(R.color.correct_answer_green);
    }

    private void set_strike_color(View view, Integer[] strike_count){
        if( strike_count[0] == 1){
            ImageView img= (ImageView) view.findViewById(R.id.strike_signal1);
            img.setImageResource(R.drawable.strike);        }
        else if (strike_count[0] == 2){
            ImageView img= (ImageView) view.findViewById(R.id.strike_signal2);
            img.setImageResource(R.drawable.strike);        }
        else if (strike_count[0] ==3){
            ImageView img= (ImageView) view.findViewById(R.id.strike_signal3);
            img.setImageResource(R.drawable.strike);
        }
    }

    private void strike_count_three_event(){
        countDownTimer.cancel();
        is_counter_running = false;
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }

    public void next_qcard_event(final View view, final Integer[] count, final ArrayList<MovieData> movie_data, final ArrayList<String> movie_list, final Integer[] ans_idx, final Integer[] strike_count){
        if(is_counter_running && countDownTimer != null){
            countDownTimer.cancel();
            is_counter_running = false;
        }

        count[0]++;
        TextView q_count =  view.findViewById(R.id.q_count);
        q_count.setText(count[0].toString());


        if(view == null)
            System.out.println("view is null");
        else {
            set_question_text(view.getRootView(), movie_data.get(count[0] - 1).dialogue.toString());
            ans_idx[0] = set_options_text(view.getRootView(), movie_list, movie_data.get(count[0] - 1).movie.toString());
            view.findViewById(R.id.option1).setBackgroundResource(R.drawable.rounded_button);
            view.findViewById(R.id.option2).setBackgroundResource(R.drawable.rounded_button);
            view.findViewById(R.id.option3).setBackgroundResource(R.drawable.rounded_button);
            view.findViewById(R.id.option4).setBackgroundResource(R.drawable.rounded_button);


            is_counter_running = true;
            countDownTimer = new CountDownTimer(main_act.time_for_round + 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    TextView cdt = view.findViewById(R.id.count_down_timer);
                    if (view.findViewById(R.id.count_down_timer) == null) {
                        countDownTimer.cancel();
                    } else {
                        System.out.println("view cdt is not null");
                        cdt.setText("" + millisUntilFinished / 1000);
                    }
                }

                public void onFinish() {
                    is_counter_running = false;
                    TextView cdt = view.findViewById(R.id.count_down_timer);
                    cdt.setText("FIN");
                    strike_count[0]++;
                    set_strike_color(view, strike_count);
                    if (strike_count[0] == 3)
                        strike_count_three_event();
                    else if (view.findViewById(R.id.option1) != null) {
                        next_qcard_event(view, count, movie_data, movie_list, ans_idx, strike_count);
                    }
                }
            }.start();
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set a random bg_image
        Random rand = new Random();
        Integer rand_bg_idx = rand.nextInt(7);
        ImageView ingame_bg = view.findViewById(R.id.in_game_bg);
        switch (rand_bg_idx){
            case 0:
                ingame_bg.setImageResource(R.drawable.in_game_bg);
                break;
            case 1:
                ingame_bg.setImageResource(R.drawable.in_game_bg2);
                break;
            case 2:
                ingame_bg.setImageResource(R.drawable.in_game_bg3);
                break;
            case 3:
                ingame_bg.setImageResource(R.drawable.in_game_bg4);
                break;
            case 4:
                ingame_bg.setImageResource(R.drawable.in_game_bg5);
                break;
            case 5:
                ingame_bg.setImageResource(R.drawable.in_game_bg6);
                break;
            case 6:
                ingame_bg.setImageResource(R.drawable.in_game_bg7);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + rand_bg_idx);
        }

        final Integer[] ans_idx = new Integer[1];
        final Integer[] count = {0};
        final Integer[] strike_count = {0};

        final ArrayList<String> movie_list = get_movie_names();
        final ArrayList<MovieData> movie_data = get_csv_data();
        Collections.shuffle(movie_data);

        next_qcard_event(view, count, movie_data, movie_list, ans_idx, strike_count);

        view.findViewById(R.id.option1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_the_answers(view.getRootView(), ans_idx[0]);
               if (check_ans_with_button(ans_idx, 1)) {
                   next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);
               }
               else{
                    strike_count[0] += 1;
                    set_strike_color(view.getRootView(), strike_count);
                   if(strike_count[0] == 3)
                       strike_count_three_event();
                   else
                       next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);               }

            }
        });

        view.findViewById(R.id.option2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_the_answers(view.getRootView(), ans_idx[0]);
                if (check_ans_with_button(ans_idx, 2)) {
                    next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);

                }
                else{
                    strike_count[0] += 1;
                    set_strike_color(view.getRootView(), strike_count);
                    if(strike_count[0] == 3)
                        strike_count_three_event();
                    else
                        next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);                }

            }
        });

        view.findViewById(R.id.option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_the_answers(view.getRootView(), ans_idx[0]);
                if (check_ans_with_button(ans_idx, 3)) {
                    next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);
                }
                else{
                    strike_count[0] += 1;
                    set_strike_color(view.getRootView(), strike_count);
                    if(strike_count[0] == 3)
                        strike_count_three_event();
                    else
                        next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);
                }

            }
        });

        view.findViewById(R.id.option4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_the_answers(view.getRootView(), ans_idx[0]);
                if (check_ans_with_button(ans_idx, 4)) {
                    next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);
                }
                else{
                    strike_count[0] += 1;
                    set_strike_color(view.getRootView(), strike_count);
                    if(strike_count[0] == 3)
                        strike_count_three_event();
                    else
                        next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);                }

            }
        });

//        view.findViewById(R.id.count_down_timer).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                set_count_down_timer(view.getRootView(), strike_count);
//                next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);
//            }
//        });
    }
}
