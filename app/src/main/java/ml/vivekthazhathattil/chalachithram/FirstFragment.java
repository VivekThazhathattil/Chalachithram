package ml.vivekthazhathattil.chalachithram;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.malayalamdialoguesquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FirstFragment extends Fragment {
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

    public void store_data_in_local_memory(){
        System.out.println("data saved to local memory");
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = settings.edit();
        if(((MainActivity) getActivity()).final_score >= 100 && ((MainActivity) getActivity()).time_for_round == 5000) {
            editor.putBoolean("is_santosh_pandit_round_unlocked", true);
            Toast toast = Toast.makeText(getContext(), "സന്തോഷ് പണ്ഡിറ്റ് റൗണ്ട് അൺലോക്ക് ചെയ്തിരിക്കുന്നു!", Toast.LENGTH_LONG);
            toast.setMargin(0, 0);
            toast.show();
        }
        else if (((MainActivity) getActivity()).final_score >= 200 && ((MainActivity) getActivity()).time_for_round == 2000) {
            editor.putBoolean("is_game_won", true);
            Toast toast = Toast.makeText(getContext(), "നിങ്ങൾ ഗെയിം വിജയിച്ചിരിക്കുന്നു!!!!", Toast.LENGTH_LONG);
            toast.setMargin(0, 0);
            toast.show();
        }
            // Apply the edits!
        editor.apply();
    }

    private void if_shaji_kailas_round_won(){
        if(((MainActivity) getActivity()).final_score >= 100 && ((MainActivity) getActivity()).time_for_round == 5000){
            store_data_in_local_memory();
        }
        else if (((MainActivity) getActivity()).final_score >= 200 && ((MainActivity) getActivity()).time_for_round == 2000){
            store_data_in_local_memory();
        }
    }

    private String load_JSON_from_resources(){
        String json = null;
        try{
            InputStream is_json = getResources().openRawResource(R.raw.dialogues);
            int size = is_json.available();
            byte[] buffer = new byte[size];
            is_json.read(buffer);
            is_json.close();
            json = new String(buffer, "UTF-8");
        }
        catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private ArrayList<MovieData> get_movie_data(){
        List<MovieData> movie_data = new ArrayList<MovieData>();
        try{
            JSONArray json_array = new JSONArray(load_JSON_from_resources());
            JSONObject movie;
            String movie_name = "";
            String dialogue = "";
            for(int i = 0; i < json_array.length(); ++i){
                movie = json_array.getJSONObject(i);
                movie_name = movie.getString("title");
                JSONArray json_dialogues = (JSONArray) movie.get("dialogues");
                for(int j = 0; j < json_dialogues.length(); ++j){
                    dialogue = json_dialogues.getString(j);
                    MovieData temp = new MovieData(dialogue, movie_name);
                    movie_data.add(temp);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return (ArrayList<MovieData>) movie_data;
    }

    private ArrayList<String> get_movie_names(){
        // get a list of movies (for other options)
        final ArrayList<String> movie_list = new ArrayList<String>();
        try {
            JSONArray json_array = new JSONArray(load_JSON_from_resources());
            JSONObject movie;
            for(int i = 0; i < json_array.length(); ++i){
                movie = json_array.getJSONObject(i);
                movie_list.add(movie.getString("title"));
                Log.println(1, "VivekHere!!!!", movie_list.get(i));
            }
            return (ArrayList<String>) movie_list;
        }
        catch (JSONException e1) {
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
            if (movie_list.get(i).equals(correct_answer))
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
            img.setImageResource(R.drawable.ic_strike);        }
        else if (strike_count[0] == 2){
            ImageView img= (ImageView) view.findViewById(R.id.strike_signal2);
            img.setImageResource(R.drawable.ic_strike);        }
        else if (strike_count[0] ==3){
            ImageView img= (ImageView) view.findViewById(R.id.strike_signal3);
            img.setImageResource(R.drawable.ic_strike);
        }
    }

    private void strike_count_three_event(Integer[] count){
        countDownTimer.cancel();
        is_counter_running = false;
        ((MainActivity) getActivity()).final_score = count[0] - 3;
        if_shaji_kailas_round_won();
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }

    public void next_qcard_event(final View view, final Integer[] count,
                                 final ArrayList<MovieData> movie_data,
                                 final ArrayList<String> movie_list,
                                 final Integer[] ans_idx,
                                 final Integer[] strike_count){
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
            countDownTimer = new CountDownTimer(((MainActivity) getActivity()).time_for_round + 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    TextView cdt = view.findViewById(R.id.count_down_timer);
                    if (view.findViewById(R.id.count_down_timer) == null) {
                        countDownTimer.cancel();
                    } else {
                        cdt.setText("" + millisUntilFinished / 1000);
                    }
                }

                public void onFinish() {
                    ((MainActivity) getActivity()).play_sfx(2);
                    is_counter_running = false;
                    TextView cdt = view.findViewById(R.id.count_down_timer);
                    cdt.setText("FIN");
                    strike_count[0]++;
                    set_strike_color(view, strike_count);
                    if (strike_count[0] == 3)
                        strike_count_three_event(count);
                    else if (view.findViewById(R.id.option1) != null) {
                        next_qcard_event(view, count, movie_data, movie_list, ans_idx, strike_count);
                    }
                }
            }.start();
        }
    }

    public void option_button_click_event(View view, Integer[] ans_idx, ArrayList<MovieData> movie_data,
                        ArrayList<String> movie_list, Integer[] count, Integer[] strike_count,
                        Integer option_clicked){
        color_the_answers(view.getRootView(), ans_idx[0]);
        if (check_ans_with_button(ans_idx, option_clicked)) {
            ((MainActivity) getActivity()).play_sfx(3);
            next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);
        }
        else{
            ((MainActivity) getActivity()).play_sfx(2);
            strike_count[0] += 1;
            set_strike_color(view.getRootView(), strike_count);
            if(strike_count[0] == 3)
                strike_count_three_event(count);
            else
                next_qcard_event(view.getRootView(), count, movie_data, movie_list, ans_idx, strike_count);               }
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
        final ArrayList<MovieData> movie_data = get_movie_data();
        Collections.shuffle(movie_data);

        next_qcard_event(view, count, movie_data, movie_list, ans_idx, strike_count);

        view.findViewById(R.id.option1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option_button_click_event(view, ans_idx, movie_data, movie_list, count, strike_count, 1);
            }
        });

        view.findViewById(R.id.option2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option_button_click_event(view, ans_idx, movie_data, movie_list, count, strike_count, 2);
            }
        });

        view.findViewById(R.id.option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option_button_click_event(view, ans_idx, movie_data, movie_list, count, strike_count, 3);
            }
        });

        view.findViewById(R.id.option4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option_button_click_event(view, ans_idx, movie_data, movie_list, count, strike_count, 4);
            }
        });
    }
}
