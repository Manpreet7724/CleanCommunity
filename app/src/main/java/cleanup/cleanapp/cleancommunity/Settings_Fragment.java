package cleanup.cleanapp.cleancommunity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class Settings_Fragment extends Fragment implements View.OnClickListener {

    Context context;
    Button signoutButton, passUpdate;
    EditText editPassUpdate;
    String newPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings_, container, false);
        signoutButton = rootView.findViewById(R.id.logout);
        signoutButton.setOnClickListener(this);
        passUpdate = rootView.findViewById(R.id.passUpdate);
        passUpdate.setOnClickListener(this);
        editPassUpdate = rootView.findViewById(R.id.editPassUpdate);
        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout) {
            signout();
        }
        if (v.getId() == R.id.passUpdate){
            passUpdate();
        }
    }

    public void signout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                final Intent intent = new Intent(getActivity(), Startup2.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a = builder.create();
        a.show();
    }

    public void passUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        newPassword = editPassUpdate.getText().toString().trim();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Password successfully updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void onDestroy() {

        super.onDestroy();

    }
}