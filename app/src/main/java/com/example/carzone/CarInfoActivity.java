package com.example.carzone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CarInfoActivity extends AppCompatActivity {

    private boolean isProfileVisible = false;  // Track visibility of profile info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        TextView profileEmailTextView, profileUsernameTextView;
        ImageView profileIconImageView;

        profileEmailTextView = findViewById(R.id.profileEmailTextView);
        profileUsernameTextView = findViewById(R.id.profileUsernameTextView);
        profileIconImageView = findViewById(R.id.profileIconImageView);
        View logoutButton = findViewById(R.id.logoutButton);

        // Initially hide the profile information
        profileEmailTextView.setVisibility(View.GONE);
        profileUsernameTextView.setVisibility(View.GONE);

        // Retrieve user data and display it
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            String username = user.getDisplayName();

            // Set email and username to TextViews
            profileEmailTextView.setText("Email: " + email);
            profileUsernameTextView.setText("Username: " + username);
        }

        // Handle profile icon click event (toggle visibility)
        profileIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isProfileVisible) {
                    // If profile info is visible, hide it
                    profileEmailTextView.setVisibility(View.GONE);
                    profileUsernameTextView.setVisibility(View.GONE);
                    Toast.makeText(CarInfoActivity.this, "Profile Info Hidden", Toast.LENGTH_SHORT).show();
                } else {
                    // If profile info is hidden, show it
                    profileEmailTextView.setVisibility(View.VISIBLE);
                    profileUsernameTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(CarInfoActivity.this, "Profile Info Displayed", Toast.LENGTH_SHORT).show();
                }

                // Toggle the flag
                isProfileVisible = !isProfileVisible;
            }
        });

        // Car details: Button IDs and messages
        int[] carButtonIds = {R.id.car1DetailsButton, R.id.car2DetailsButton, R.id.car3DetailsButton};
        String[] carDetailsMessages = {
                "Tesla Model 3: Range - 353 miles | Price - $39,990",
                "BMW X5: Engine - 3.0L | Price - $61,695",
                "Ford Mustang: Horsepower - 450 HP | Price - $55,300"
        };

        // Set click listeners for each car's "More Details" button
        for (int i = 0; i < carButtonIds.length; i++) {
            final String detailsMessage = carDetailsMessages[i];
            Button carDetailsButton = findViewById(carButtonIds[i]);
            carDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CarInfoActivity.this, detailsMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
        // Handle logout button click event
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out the user
                firebaseAuth.signOut();
                Toast.makeText(CarInfoActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                // Redirect to Login Activity (ensure you have created a LoginActivity)
                Intent intent = new Intent(CarInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // Close the current activity
            }
        });
    }
}
