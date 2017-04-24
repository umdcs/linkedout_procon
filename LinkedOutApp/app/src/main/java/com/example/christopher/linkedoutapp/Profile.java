package com.example.christopher.linkedoutapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import com.example.christopher.linkedoutapp.QuizModel.ExpandableListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

    ProfilePic pic;

    public final static String STUDENT_PREFS = "Student Preferences";
    SharedPreferences prefs;

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

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

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

        fillInData(view);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        prepareListData();
        listAdapter = new ExpandableListAdapter(view.getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return view;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        List<String> skill1 = new ArrayList<String>();
        if(prefs.getString("skillName1", "ERROR") != "") {
            listDataHeader.add(prefs.getString("skillName1", "ERROR"));
            skill1.add(prefs.getString("skillHow1", "ERROR"));
            skill1.add(prefs.getString("skillDesc1", "ERROR"));
        }

        List<String> skill2 = new ArrayList<String>();
        if(prefs.getString("skillName2", "ERROR") != "") {
            listDataHeader.add(prefs.getString("skillName2", "ERROR"));
            skill2.add(prefs.getString("skillHow2", "ERROR"));
            skill2.add(prefs.getString("skillDesc2", "ERROR"));
        }

        List<String> skill3 = new ArrayList<String>();
        if(prefs.getString("skillName3", "ERROR") != "") {
            listDataHeader.add(prefs.getString("skillName3", "ERROR"));
            skill3.add(prefs.getString("skillHow3", "ERROR"));
            skill3.add(prefs.getString("skillDesc3", "ERROR"));
        }

        List<String> skill4 = new ArrayList<String>();
        if(prefs.getString("skillName4", "ERROR") != "") {
            listDataHeader.add(prefs.getString("skillName4", "ERROR"));
            skill4.add(prefs.getString("skillHow4", "ERROR"));
            skill4.add(prefs.getString("skillDesc4", "ERROR"));
        }

        List<String> skill5 = new ArrayList<String>();
        if(prefs.getString("skillName5", "ERROR") != "") {
            listDataHeader.add(prefs.getString("skillName5", "ERROR"));
            skill5.add(prefs.getString("skillHow5", "ERROR"));
            skill5.add(prefs.getString("skillDesc5", "ERROR"));
        }

        if(prefs.getString("skillName1", "ERROR") != "") {
            listDataChild.put(listDataHeader.get(0), skill1);
        }

        if(prefs.getString("skillName2", "ERROR") != "") {
            listDataChild.put(listDataHeader.get(1), skill2);
        }

        if(prefs.getString("skillName3", "ERROR") != "") {
            listDataChild.put(listDataHeader.get(2), skill3);
        }

        if(prefs.getString("skillName4", "ERROR") != "") {
            listDataChild.put(listDataHeader.get(3), skill4);
        }

        if(prefs.getString("skillName5", "ERROR") != "") {
            listDataChild.put(listDataHeader.get(4), skill5);
        }
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

        //Add profile picture
        pic = new ProfilePic(null);
        ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
        String imgString = prefs.getString("profilePic", "ERROR");
        if(prefs.getString("profilePic", "ERROR") == ""){
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
        }
        else {
            Bitmap bm = pic.getDecodedBitmap(imgString);
            pic.setBitmap(bm);
            profilePic.setImageBitmap(pic.getResizedBitmap());
        }

        //Add onClick action to AddSkill button
        Button addSkillAction = (Button)view.findViewById(R.id.buttonAddSkill);
        addSkillAction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudentAddSkillActivity.class);
                startActivity(intent);
            }
        });

        //Add onClick action to Settings button
        Button modSettings = (Button)view.findViewById(R.id.buttonProfileSettings);
        modSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudentModSettings.class);
                startActivity(intent);
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
