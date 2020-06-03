package com.example.malayalamdialoguesquiz;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings.
     */
    // TODO: Rename and change types and number of parameters
    public static settings newInstance(String param1, String param2) {
        settings fragment = new settings();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button go_back = view.findViewById(R.id.go_back_button2);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(settings.this)
                        .navigate(R.id.action_settings_to_menu);
            }
        });
        final Switch music_toggle = view.findViewById(R.id.switch_music);
        final Switch sfx_toggle = view.findViewById(R.id.switch_sfx);

        if (((MainActivity) getActivity()).is_sound_on(0))
            music_toggle.setChecked(true);
        else
            music_toggle.setChecked(false);

        if (((MainActivity) getActivity()).is_sound_on(1))
            sfx_toggle.setChecked(true);
        else
            sfx_toggle.setChecked(false);


        music_toggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!music_toggle.isChecked()) {
                        ((MainActivity) getActivity()).backgroundPlayer.stopBackgroundMusic();
                        ((MainActivity) getActivity()).store_music_preferences(1, false); // store toggle preferences

                }
                else {

                        ((MainActivity) getActivity()).backgroundPlayer.startBackgroundMusic();
                        ((MainActivity) getActivity()).store_music_preferences(1, true); // store toggle preferences
                }
            }
        });

        sfx_toggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity) getActivity()).store_music_preferences(2, sfx_toggle.isChecked());
            }
        });


    }
}
