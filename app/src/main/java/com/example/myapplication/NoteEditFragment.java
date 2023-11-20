package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class NoteEditFragment extends Fragment {
    EditText NameEditText;
    EditText UrlEditText;
    EditText LoginEditText;
    EditText PasswordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString(MainActivity.KEY_NAME);
            String url = bundle.getString(MainActivity.KEY_URL);
            String login = bundle.getString(MainActivity.KEY_LOGIN);
            String password = bundle.getString(MainActivity.KEY_PASSWORD);

            NameEditText = view.findViewById(R.id.nameEditText);
            NameEditText.setText(name);

            UrlEditText = view.findViewById(R.id.urlEditText);
            UrlEditText.setText(url);

            LoginEditText = view.findViewById(R.id.loginEditText);
            LoginEditText.setText(login);

            PasswordEditText = view.findViewById(R.id.passwordEditText);
            PasswordEditText.setText(password);

            Button backButton = view.findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteEditActivity activity = (NoteEditActivity) getActivity();
                    if (activity != null) {
                        activity.BackData(NameEditText.getText().toString(), UrlEditText.getText().toString(),LoginEditText.getText().toString(),PasswordEditText.getText().toString());
                    }
                }
            });
        }
        return view;
    }
}