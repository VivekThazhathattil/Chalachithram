package ml.vivekthazhathattil.chalachithram;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malayalamdialoguesquiz.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class menu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton play_btn, settings_btn, exit_btn, share_btn, rate_btn, credits_btn;
    public menu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menu.
     */
    // TODO: Rename and change types and number of parameters
    public static menu newInstance(String param1, String param2) {
        menu fragment = new menu();
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
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation swing, swing_slow;
        swing = AnimationUtils.loadAnimation(getContext(), R.anim.pendulum);
        swing_slow = AnimationUtils.loadAnimation(getContext(), R.anim.pendulum_slow);

        if (((MainActivity) getActivity()).is_sound_on(0))
            ((MainActivity) getActivity()).backgroundPlayer.startBackgroundMusic();
        else
            ((MainActivity) getActivity()).backgroundPlayer.stopBackgroundMusic();

        if(view == null){
            System.out.println("view is null");
        }
        else if (view.findViewById(R.id.play_button) == null)
            System.out.println("play_button id is null");

        // change menu_title if game won

        play_btn = view.findViewById(R.id.play_button);
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(menu.this)
                        .navigate(R.id.action_menu_to_game_mode);
            }
        });
        play_btn.startAnimation(swing);

        exit_btn = view.findViewById(R.id.exit_button);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().moveTaskToBack(true);
                getActivity().finish();
            }
        });

        credits_btn = view.findViewById(R.id.credits_button);
        credits_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(menu.this)
                        .navigate(R.id.action_menu_to_credits);
            }
        });

        share_btn = view.findViewById(R.id.share_button);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey! check out Malayalam Dialogue Quiz app on Google Playstore. I think you will like it!";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        settings_btn = view.findViewById(R.id.settings_button);
        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(menu.this)
                        .navigate(R.id.action_menu_to_settings);
            }
        });
        settings_btn.startAnimation(swing);

        rate_btn = view.findViewById(R.id.rate_button);
        rate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getContext(),"ആപ്പ് ഇഷ്ടമായെങ്കിൽ പ്ലേസ്റ്റോറിൽ റേറ്റ് ചെയ്യാൻ മറക്കരുത്. അവിടെ നിങ്ങൾക്ക് " +
                        "എന്നോട് ഈ ആപ്പിൽ ഇഷ്ടമില്ലാത്തതും ഇഷ്ടമുള്ളതുമായ കാര്യങ്ങൾ പങ്കുവെക്കാം, കൂടുതൽ ഫീച്ചേർസ് ആവശ്യപ്പെടാം. " +
                        "ഞാൻ അതിനനുസരിച്ച് ഇത് അപ്ഡേറ്റ് ചെയ്യുന്നതാണ്.", Toast.LENGTH_LONG);
                toast.setMargin(0,0);
                toast.show();
            }
        });


        //animate title
        TextView menu_title = view.findViewById(R.id.menu_title);
        MainActivity main_act = (MainActivity) getActivity();
        if(main_act.is_game_won)
            menu_title.setText("ങ്ങള് ഒരു വൻ സംഭവാ ട്ടോ! \uD83D\uDC51");
        menu_title.startAnimation(swing_slow);
    }

}
