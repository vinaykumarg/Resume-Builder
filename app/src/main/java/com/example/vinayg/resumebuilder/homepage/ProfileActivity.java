package com.example.vinayg.resumebuilder.homepage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vinayg.resumebuilder.Summary.SummaryActivity;
import com.example.vinayg.resumebuilder.R;
import com.example.vinayg.resumebuilder.adapters.EducationListViewAdapter;
import com.example.vinayg.resumebuilder.adapters.InterestsListViewAdapter;
import com.example.vinayg.resumebuilder.adapters.ProjectListViewAdapter;
import com.example.vinayg.resumebuilder.adapters.Utility;
import com.example.vinayg.resumebuilder.database.Education;
import com.example.vinayg.resumebuilder.database.Interest;
import com.example.vinayg.resumebuilder.database.MyAppDb;
import com.example.vinayg.resumebuilder.database.Projects;
import com.example.vinayg.resumebuilder.database.Summary;
import com.example.vinayg.resumebuilder.education.EducationActivity;
import com.example.vinayg.resumebuilder.interests.InterestsActivity;
import com.example.vinayg.resumebuilder.projects.ProjectsActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{

    private static final String TAG = ProfileActivity.class.getName();
    private static final int REFRESH = 222;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int PICK_IMAGE = 2;
    private SessionManager session;
    private TextView username;
    private TextView Emailid;
    private ImageView profilepic;
    private ImageButton mProfilePicButton;
    private MyAppDb mMyAppDb;
    private ListView mProjectsListView;
    private LinearLayout mSummaryLinearLayout;
    private TextView mUserName;
    private TextView mEmail;
    private TextView mSummary;
    private Summary summary;
    private List<Projects> projects;
    private List<Education> mEducationList;
    private List<Interest> interestlist;
    private  String uid ;
    private HashMap<String, String> user;
    private ListView mEducationListView,mInterestListView;
    private LinearLayout mProjectsLinearLayout;
    private LinearLayout mEducationLinearLayout;
    private LinearLayout mInterestLinearLayout;
    private ProjectListViewAdapter projectListViewAdapter;
    private EducationListViewAdapter educationListViewAdapter;
    private InterestsListViewAdapter interestsListViewAdapter;
    private String mCurrentPhotoPath;
    private AlertDialog dialog;
    private TextView mExperience;
    private AlertDialog experienceDialog;
    private EditText experience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mMyAppDb = new MyAppDb(this);
        mMyAppDb.open();
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            session.checkLogin();
        }
        user = session.getUserDetails();
        uid = user.get(SessionManager.KEY_ID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        Emailid = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailid);
        profilepic = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        setResumeHeader();
        AddListViews();


    }

    private void setResumeHeader() {
        mProfilePicButton = (ImageButton) findViewById(R.id.user_profile_photo);
        mProfilePicButton.setOnClickListener(this);
        mUserName = (TextView) findViewById(R.id.user_profile_name);
        mEmail = (TextView)findViewById(R.id.user_profile_email);
        mExperience = (TextView) findViewById(R.id.user_profile_experience);
        mExperience.setOnClickListener(this);
        mSummary = (TextView)findViewById(R.id.aboutMe);

    }


    private void AddListViews(){
        mSummaryLinearLayout = (LinearLayout) findViewById(R.id.Summary);
        mProjectsLinearLayout = (LinearLayout) findViewById(R.id.projects);
        mEducationLinearLayout = (LinearLayout) findViewById(R.id.educations);
        mInterestLinearLayout = (LinearLayout) findViewById(R.id.interests);
        mSummaryLinearLayout.setOnClickListener(this);
        mProjectsLinearLayout.setOnClickListener(this);
        mEducationLinearLayout.setOnClickListener(this);
        mInterestLinearLayout.setOnClickListener(this);
        mProjectsListView = (ListView)findViewById(R.id.project_list);
        mEducationListView = (ListView) findViewById(R.id.education_list);
        mInterestListView = (ListView) findViewById(R.id.interest_list);



    }

    @Override
    protected void onResume() {
        super.onResume();
        session.checkLogin();
        updateUi();
    }

    private void swapadapters() {
        projectListViewAdapter.swapList(mMyAppDb.gettAllProjects(uid));
        projectListViewAdapter.notifyDataSetChanged();
        educationListViewAdapter.swapList(mMyAppDb.gettAllEducation(uid));
        educationListViewAdapter.notifyDataSetChanged();
        interestsListViewAdapter.swap(mMyAppDb.gettAllInterest(uid));
        interestsListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the profile action
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
     public void updateUi(){
        if (session.isLoggedIn()) {
            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // name
            String name = user.get(SessionManager.KEY_NAME);
            username.setText(name);
            mUserName.setText(name);
            summary = mMyAppDb.getSummary(user.get(SessionManager.KEY_ID));
            if (summary != null) {

                mSummary.setText(summary.getSummary());
            }


            // email
            String email = user.get(SessionManager.KEY_EMAIL);
            Emailid.setText(email);
            mEmail.setText(email);

            String photouri = user.get(SessionManager.KEY_PHOTO);
            Glide
                    .with(this)
                    .load(photouri)
                    .centerCrop()
                    .into(profilepic);
            Glide
                    .with(this)
                    .load(photouri)
                    .centerCrop()
                    .into(mProfilePicButton);
            String experience = mMyAppDb.getExperience(uid);
            List<String> strings = mMyAppDb.selectAllUsers();
            if (!experience.equals(null)) {
                mExperience.setText(experience+" years");
            }
            projects = mMyAppDb.gettAllProjects(uid);
            projectListViewAdapter = new ProjectListViewAdapter(projects,this);
            mProjectsListView.setAdapter(projectListViewAdapter);
            mEducationList = mMyAppDb.gettAllEducation(uid);
            educationListViewAdapter = new EducationListViewAdapter(mEducationList,this);
            mEducationListView.setAdapter(educationListViewAdapter);
            interestlist = mMyAppDb.gettAllInterest(uid);
            interestsListViewAdapter = new InterestsListViewAdapter(interestlist,this);
            mInterestListView.setAdapter(interestsListViewAdapter);
            Utility.setListViewHeightBasedOnChildren(mProjectsListView);
            Utility.setListViewHeightBasedOnChildren(mEducationListView);
            Utility.setListViewHeightBasedOnChildren(mInterestListView);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();
        switch (id) {
            case R.id.Summary:
                intent = new Intent(this, SummaryActivity.class);
                startActivityForResult(intent,REFRESH);
                break;
            case R.id.projects:
                intent = new Intent(this, ProjectsActivity.class);
                startActivityForResult(intent,REFRESH);
                break;
            case R.id.educations:
                intent = new Intent(this, EducationActivity.class);
                startActivityForResult(intent,REFRESH);
                break;
            case R.id.interests:
                intent = new Intent(this, InterestsActivity.class);
                startActivityForResult(intent,REFRESH);
                break;
            case R.id.user_profile_photo:
                showDialog();
                break;
            case R.id.user_profile_experience:
                enterExperienceDialog();
                break;
            default:break;

        }
    }

    private void enterExperienceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.experience, null);

        builder.setView(inflater.inflate(R.layout.experience, null))
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
                        experience = (EditText) d.findViewById(R.id.experience);
                        String years = experience.getText().toString();
                        if (!years.equals("")) {
                            mMyAppDb.updateExperience(uid, years);
                            updateUi();
                        } else {
                            Toast.makeText(getApplicationContext(),"enter details",Toast.LENGTH_SHORT).show();
                            enterExperienceDialog();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });
        experienceDialog = builder.create();
        experienceDialog.show();
    }

    private DialogInterface getDialog() {
        return experienceDialog;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title));
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.profilepicdialog, null);
        TextView CameraButton = (TextView) view.findViewById(R.id.camera) ;
        CameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                dismissDialog();

            }
        });
        TextView GalleryButton = (TextView) view.findViewById(R.id.gallery);
        GalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchImageFromGallery();
                dismissDialog();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REFRESH) {
            swapadapters();
        }else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            session.changeImageuri(mCurrentPhotoPath);
            updateUi();
        }
        else if (requestCode==PICK_IMAGE&& resultCode==Activity.RESULT_OK) {
            Uri uri = data.getData();
            session.changeImageuri(uri.toString());
            updateUi();
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d(TAG,"image created");
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d(TAG,"dispatchTakePictureIntent");
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void dispatchImageFromGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAppDb.close();
    }
}
