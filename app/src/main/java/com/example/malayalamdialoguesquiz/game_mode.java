package com.example.malayalamdialoguesquiz;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link game_mode#newInstance} factory method to
 * create an instance of this fragment.
 */
public class game_mode extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button amal_neerad_btn;
    private Button saadaa_btn;
    private Button shaji_kailas_btn;
    private Button santosh_pandit_btn;
    private ImageButton back_button;

    public game_mode() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment game_mode.
     */
    // TODO: Rename and change types and number of parameters
    public static game_mode newInstance(String param1, String param2) {
        game_mode fragment = new game_mode();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_mode, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),"manjari_regular.ttf");
        super.onViewCreated(view, savedInstanceState);
        if(view == null){
            System.out.println("view is null");
        }
        else if (view.findViewById(R.id.play_button) == null)
            System.out.println("play_button id is null");

        amal_neerad_btn = view.findViewById(R.id.amal_neerad);
        amal_neerad_btn.setTypeface(typeFace);
        Animation RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option4);
        amal_neerad_btn.startAnimation(RightSwipe);
        amal_neerad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main_act = (MainActivity) getActivity();
                main_act.time_for_round = 30000;
                NavHostFragment.findNavController(game_mode.this)
                        .navigate(R.id.action_game_mode_to_FirstFragment);
            }
        });

        saadaa_btn = view.findViewById(R.id.saadaaa);
        saadaa_btn.setTypeface(typeFace);
        RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option3);
        saadaa_btn.startAnimation(RightSwipe);
        saadaa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main_act = (MainActivity) getActivity();
                main_act.time_for_round = 15000;
                NavHostFragment.findNavController(game_mode.this)
                        .navigate(R.id.action_game_mode_to_FirstFragment);
            }
        });

        shaji_kailas_btn = view.findViewById(R.id.shaji_kailas);
        shaji_kailas_btn.setTypeface(typeFace);
        RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option2);
        shaji_kailas_btn.startAnimation(RightSwipe);
        shaji_kailas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main_act = (MainActivity) getActivity();
                main_act.time_for_round = 5000;
                NavHostFragment.findNavController(game_mode.this)
                        .navigate(R.id.action_game_mode_to_FirstFragment);
            }
        });

        santosh_pandit_btn = view.findViewById(R.id.santosh_pandit);
        santosh_pandit_btn.setTypeface(typeFace);
        RightSwipe = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_option1);
        santosh_pandit_btn.startAnimation(RightSwipe);
        santosh_pandit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity main_act = (MainActivity) getActivity();
                main_act.time_for_round = 2000;
                NavHostFragment.findNavController(game_mode.this)
                        .navigate(R.id.action_game_mode_to_FirstFragment);
            }
        });

        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(game_mode.this)
                        .navigate(R.id.action_game_mode_to_menu);
            }
        });

    }
}
