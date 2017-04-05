package com.example.christopher.linkedoutapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ProfilePic pic = new ProfilePic(null);

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs; // = getSharedPreferences(STUDENT_PREFS, 0); //Context.MODE_PRIVATE);

    //Individual skill textView
    private TextView skillNameText;

    //Skills: How and description
    private TextView skillHowText;
    private TextView skillDescriptionText;

    private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        prefs = getContext().getSharedPreferences(STUDENT_PREFS, 0);

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);


        skillNameText = (TextView) view.findViewById(R.id.displaySkillName);
        skillHowText = (TextView) view.findViewById(R.id.displaySkillHow);
        skillDescriptionText = (TextView) view.findViewById(R.id.displaySkillDescription);

        skillNameText.setVisibility(View.GONE);
        skillHowText.setVisibility(View.GONE);
        skillDescriptionText.setVisibility(View.GONE);

        fillInData(view);


        ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
        if(!prefs.getString("profilePic","").equals("")) {
            String imgString = prefs.getString("profilePic", "ERROR");
            Bitmap bm = pic.getDecodedBitmap(imgString);
            bm = pic.getResizedBitmap(bm);
            profilePic.setImageBitmap(bm);
        }
        // Fix this!!
        //else profilePic.setImageDrawable(--SOME DEFAULT VALUE--));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void fillInData(View view) {

     //Fill the profile display data (name, location, major, graduation date)
        TextView nameText = (TextView) view.findViewById(R.id.displayStudentName);
        nameText.setText(prefs.getString("fullName", "ERROR") ); // ("name of key value", "default value if key is not found")

        TextView majorText = (TextView) view.findViewById(R.id.displayMajor);
        majorText.setText(prefs.getString("major", "ERROR") + " Major");

        TextView graduationText = (TextView) view.findViewById(R.id.displayGraduation);
        graduationText.setText("Graduating " +
                prefs.getString("gradTerm", "ERROR") + " " +
                prefs.getString("gradYear", "ERROR"));

        TextView locationText = (TextView) view.findViewById(R.id.displayLocation);
        locationText.setText(prefs.getString("city", "ERROR") + ", " +
                prefs.getString("state", "ERROR"));

        TextView displaySkillText = (TextView) view.findViewById(R.id.displaySkillText);

            //Set the header "Skills"
            displaySkillText = (TextView) view.findViewById(R.id.displaySkillText);
            displaySkillText.setText("Skills");

            //Add skill name
            skillNameText.setText(prefs.getString("skillname", ""));

            //Add how skill was aquired
            skillHowText.setText("Skill obtained from: " + "\n" + prefs.getString("skillhow", ""));

            //Add skill description
            skillDescriptionText.setText("Description: " + "\n" + prefs.getString("skilldescription", ""));

        //Add profile picture
        ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
        String imgString = prefs.getString("profilePic", "ERROR");
        Bitmap bm = pic.getDecodedBitmap(imgString);
        bm = pic.getResizedBitmap(bm);
        profilePic.setImageBitmap(bm);

        //Add onClick action to AddSkill button
        Button addSkillAction = (Button)view.findViewById(R.id.buttonAddSkill);
        addSkillAction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudentAddSkillActivity.class);
                startActivity(intent);
            }
        });

        //Toggle display of skills names
        displaySkillText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillNameText.setVisibility(skillNameText.isShown()
                        ? View.GONE
                        : View.VISIBLE);
            }
        });

        //Toggle display of skills' descriptions
        skillNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillHowText.setVisibility( skillHowText.isShown()
                        ? View.GONE
                        : View.VISIBLE );

                skillDescriptionText.setVisibility( skillDescriptionText.isShown()
                        ? View.GONE
                        : View.VISIBLE );
            }
        });

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
