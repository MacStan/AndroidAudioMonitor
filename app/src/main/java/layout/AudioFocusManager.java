package layout;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pallax.audiomonitor.R;
import com.example.pallax.audiomonitor.databinding.FragmentStreamsMonitorBinding;

import java.util.ArrayList;

public class AudioFocusManager
        extends Fragment
        implements View.OnClickListener{
    //},        AudioManager.OnAudioFocusChangeListener{

    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<String> streamsListAdapter;
    private ArrayAdapter<String> audioFocusTypesListAdapter;
    public AudioManager am;

    public AudioFocusManager() {
        // Required empty public constructor
    }

    public static AudioFocusManager newInstance(String param1, String param2) {
        AudioFocusManager fragment = new AudioFocusManager();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_audio_focus_manager, container, false);

        Spinner spinner = (Spinner)v.findViewById(R.id.audioFocusStreamsSpinner);
        String[] streamsArray = new String[]{"Alarm","Dtmf","Music","Notification","Ring","System","Voice Call"};
        streamsListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,streamsArray);
        spinner.setAdapter(streamsListAdapter);

        spinner = (Spinner)v.findViewById(R.id.audioFocusTypeSpinner);
        streamsArray = new String[]{"AF_GAIN","AF_GAIN_TRANS_EX","AF_GAIN_TRANS","AF_GAIN_TRANS_DUCK","AF_LOSS","AF_LOSS_TRANS","AF_LOSS_TRANS_DUCK",};
        audioFocusTypesListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,streamsArray);
        spinner.setAdapter(audioFocusTypesListAdapter );

        OverrideButtonOnClickListener(v, R.id.abandonAudioFocusButton);
        OverrideButtonOnClickListener(v, R.id.requestAudioFocusButton);
        return v;
    }

    private void OverrideButtonOnClickListener(View v, int id)
    {
        Button b = (Button)v.findViewById(id);
        b.setOnClickListener(this);
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

    private int stringToStream(String stream){
        switch(stream) {
            case "Alarm":
                return AudioManager.STREAM_ALARM;
            case "Dtmf":
                return AudioManager.STREAM_DTMF;
            case "Music":
                return AudioManager.STREAM_MUSIC;
            case "Notification":
                return AudioManager.STREAM_NOTIFICATION;
            case "Ring":
                return AudioManager.STREAM_RING;
            case "System":
                return AudioManager.STREAM_SYSTEM;
            case "Voice Call":
                return AudioManager.STREAM_VOICE_CALL;
            default:
                return AudioManager.USE_DEFAULT_STREAM_TYPE;
        }

    }

    @Override
    public void onClick(View v) {
        android.util.Log.w("myApp","OnClick");
        switch(v.getId())
        {
            case R.id.requestAudioFocusButton:
                requestAudioFocus();
                break;
            case R.id.abandonAudioFocusButton:
                abandonAudioFocus();
                break;
        }
    }

    public void requestAudioFocus()
    {
        String stream = (String)((Spinner)getView().findViewById(R.id.audioFocusStreamsSpinner)).getSelectedItem();
        String aftype = (String)((Spinner)getView().findViewById(R.id.audioFocusTypeSpinner)).getSelectedItem();
        android.util.Log.w("myApp","RequestAUDIO " +stream + aftype);

        int result = am.requestAudioFocus(
                xxx,
                stringToStream(stream),
                stringToAudioFocus(aftype));

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.w("AudioFocus", "REQ Granted");
            TextView tv = (TextView)getView().findViewById(R.id.afgTextView);
            tv.setText("AF Granted: " + stream +" "+aftype);
        }
    }
    public void abandonAudioFocus()
    {
        am.abandonAudioFocus(xxx);
    }

    public int stringToAudioFocus(String af)
    {
        switch(af)
        {
            case"AF_GAIN":
                return AudioManager.AUDIOFOCUS_GAIN;
            case"AF_GAIN_TRANS_EX":
                return AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE;
            case"AF_GAIN_TRANS":
                return AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
            case"AF_GAIN_TRANS_DUCK":
                return AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
            case"AF_LOSS":
                return AudioManager.AUDIOFOCUS_LOSS;
            case"AF_LOSS_TRANS":
                return AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
            case"AF_LOSS_TRANS_DUCK":
                return AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
            default:
                return AudioManager.AUDIOFOCUS_REQUEST_FAILED;
        }

    }
    AudioManager.OnAudioFocusChangeListener xxx = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            TextView afInfoTextView = (TextView)getView().findViewById(R.id.aflTextView);
            Log.w("ON AUDIO FOCUS CHANGE", "xx");
            switch( focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    afInfoTextView.setText("AudioFocusChange to: AF_GAIN");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:
                    afInfoTextView.setText("AudioFocusChange to: AF_GAIN_TRANS_EX");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                    afInfoTextView.setText("AudioFocusChange to: AF_GAIN_TRANS");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    afInfoTextView.setText("AudioFocusChange to: AF_GAIN_TRANS_DUCK");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    afInfoTextView.setText("AudioFocusChange to: AF_LOSS");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    afInfoTextView.setText("AudioFocusChange to: AF_LOSS_TRANS");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    afInfoTextView.setText("AudioFocusChange to: AF_LOSS_TRANS_DUCK");
                    break;
                default:
                    afInfoTextView.setText("AudioFocusChange to: Unknown");
            }
        }
    };



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
