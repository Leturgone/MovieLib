package com.example.cursework2_2;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HelpFragment extends Fragment {


    TextView admin_text1, admin_text2, admin_text3, admin_text4, admin_text5, admin_text6;
    public HelpFragment() {
    }

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
        admin_text1 = view.findViewById(R.id.help_for_admin1);
        admin_text2 = view.findViewById(R.id.help_for_admin2);
        admin_text3 = view.findViewById(R.id.help_for_admin3);
        admin_text4 = view.findViewById(R.id.help_for_admin4);
        admin_text5 = view.findViewById(R.id.help_for_admin5);
        admin_text6 = view.findViewById(R.id.help_for_admin6);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPref", MODE_PRIVATE);
        //Получаем роль
        String username = sharedPreferences.getString("username", "");
        if(myDB.getRole(username).equals("viewer")){
            admin_text1.setVisibility(View.GONE);
            admin_text2.setVisibility(View.GONE);
            admin_text3.setVisibility(View.GONE);
            admin_text4.setVisibility(View.GONE);
            admin_text5.setVisibility(View.GONE);
            admin_text6.setVisibility(View.GONE);
        }
    }
}