package com.bignerdranch.android.criminalintent;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "criminalintent.CRIME_ID";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    Crime mCrime;
    EditText mTitleField;
    Button mDateButton;
    Button deleteButton;
    CheckBox mSolvedCheckBox;
    ImageButton mPhotoButton;

    ImageView[] imageViews = new ImageView[Crime.MAX_PHOTOS];
    BitmapDrawable[] drawables = new BitmapDrawable[Crime.MAX_PHOTOS];

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

        setHasOptionsMenu(true);
    }

    public void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    @Override
    @TargetApi(11)
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);
 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        deleteButton = (Button)v.findViewById(R.id.delete_button);

        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                    .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearPhotos();
                mCrime.deletePhotos();
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });

        mPhotoButton = (ImageButton)v.findViewById(R.id.crime_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch the camera activity
                Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });
        
        // if camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            mPhotoButton.setEnabled(false);
        }

        imageViews[0] = (ImageView)v.findViewById(R.id.crime_imageView);
        imageViews[1] = (ImageView)v.findViewById(R.id.imageView1);
        imageViews[2] = (ImageView)v.findViewById(R.id.imageView2);
        imageViews[3] = (ImageView)v.findViewById(R.id.imageView3);

        showAllPhotos();

        for(int i = 0; i < Crime.MAX_PHOTOS; i++) {
            final int index = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Photo p = mCrime.getPhoto(index);
                    if (p == null)
                        return;

                    FragmentManager fm = getActivity()
                            .getSupportFragmentManager();
                    String path = getActivity()
                            .getFileStreamPath(p.getFilename()).getAbsolutePath();
                    ImageFragment.createInstance(path)
                            .show(fm, DIALOG_IMAGE);
                }
            });
        }
        
        return v; 
    }
    
    private void showPhoto(Photo p, int i) {
        // (re)set the image button's image based on our photo
        String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
        imageViews[i].setImageURI(Uri.fromFile(new File(path)));
    }

    private void showAllPhotos() {
        String path;
        Photo p;
        for(int i = 0; i < mCrime.getNumPhotos(); i++) {
            p = mCrime.getPhoto(i);
            path = getActivity()
                    .getFileStreamPath(p.getFilename()).getAbsolutePath();

            imageViews[i].setImageURI(Uri.fromFile(new File(path)));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void clearPhotos() {
        for(int i = 0; i < mCrime.getNumPhotos(); i++) {
            imageViews[i].setImageURI(null);
            drawables[i] = null;
        }
    }
    
    @Override
    public void onStop() {
        super.onStop();
        //clearPhotos();
    }

    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            // create a new Photo object and attach it to the crime
            String filename = data
                .getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Photo p = new Photo(filename);
                int i = mCrime.setPhoto(p);
                showPhoto(p, i);
            }
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }
}
