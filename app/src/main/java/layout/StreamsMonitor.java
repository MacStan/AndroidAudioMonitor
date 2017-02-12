package layout;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pallax.audiomonitor.R;
import com.example.pallax.audiomonitor.databinding.FragmentStreamsMonitorBinding;

import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StreamsMonitor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StreamsMonitor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StreamsMonitor
        extends Fragment
        implements View.OnClickListener{


    private OnFragmentInteractionListener mListener;
    private ObservableInt alarmVolume       = new ObservableInt(-2);
    private ObservableInt dtmfVolume        = new ObservableInt(-2);
    private ObservableInt musicVolume       = new ObservableInt(-2);
    private ObservableInt notificationVolume  = new ObservableInt(-2);
    private ObservableInt ringVolume        = new ObservableInt(-2);
    private ObservableInt systemVolume      = new ObservableInt(-2);
    private ObservableInt voiceCallVolume   = new ObservableInt(-2);

    public AudioManager am;

    final Handler h = new Handler();
    final int delay = 250;

    public StreamsMonitor() {
        // Required empty public constructor
    }

    public static StreamsMonitor newInstance(String param1, String param2) {
        StreamsMonitor fragment = new StreamsMonitor();
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
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_streams_monitor, container, false);
        FragmentStreamsMonitorBinding fsmb = (FragmentStreamsMonitorBinding)FragmentStreamsMonitorBinding.inflate(inflater);

        fsmb.setAlarmVolume(alarmVolume);
        fsmb.setDtmfVolume(dtmfVolume);
        fsmb.setMusicVolume(musicVolume);
        fsmb.setNotificationVolume(notificationVolume);
        fsmb.setRingVolume(ringVolume);
        fsmb.setSystemVolume(systemVolume);
        fsmb.setVoiceCallVolume(voiceCallVolume);

        OverrideButtonOnClickListener(fsmb, R.id.alarmUpButton);
        OverrideButtonOnClickListener(fsmb, R.id.alarmDownbutton);
        OverrideButtonOnClickListener(fsmb, R.id.dtmfUpbutton);
        OverrideButtonOnClickListener(fsmb, R.id.dtmfDownbutton);
        OverrideButtonOnClickListener(fsmb, R.id.musicUpButton);
        OverrideButtonOnClickListener(fsmb, R.id.musicDownbutton);
        OverrideButtonOnClickListener(fsmb, R.id.notificationUpbutton);
        OverrideButtonOnClickListener(fsmb, R.id.notifDownButton);
        OverrideButtonOnClickListener(fsmb, R.id.ringUpButton);
        OverrideButtonOnClickListener(fsmb, R.id.ringDownButton);
        OverrideButtonOnClickListener(fsmb, R.id.systemUpButton);
        OverrideButtonOnClickListener(fsmb, R.id.systemDownButton);
        OverrideButtonOnClickListener(fsmb, R.id.voiceCallButton);
        OverrideButtonOnClickListener(fsmb, R.id.voiceCallUpButton);

        h.postDelayed(new Runnable() {
            public void run() {
                alarmVolume.set(am.getStreamVolume(AudioManager.STREAM_ALARM));
                dtmfVolume.set(am.getStreamVolume(AudioManager.STREAM_DTMF));
                musicVolume.set(am.getStreamVolume(AudioManager.STREAM_MUSIC));
                notificationVolume.set(am.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
                ringVolume.set(am.getStreamVolume(AudioManager.STREAM_RING));
                systemVolume.set(am.getStreamVolume(AudioManager.STREAM_SYSTEM));
                voiceCallVolume.set(am.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
                h.postDelayed(this,delay);
            }
        }, delay);
        return fsmb.getRoot();

    }
    private void OverrideButtonOnClickListener(FragmentStreamsMonitorBinding v, int id)
    {
        Button b = (Button)v.getRoot().findViewById(id);
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

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.alarmUpButton:  incStreamVolume(AudioManager.STREAM_ALARM);
                break;
            case R.id.alarmDownbutton: decStreamVolume(AudioManager.STREAM_ALARM);
                break;
            case R.id.dtmfUpbutton:incStreamVolume(AudioManager.STREAM_DTMF);
                break;
            case R.id.dtmfDownbutton: decStreamVolume(AudioManager.STREAM_DTMF);
                break;
            case R.id.musicUpButton: incStreamVolume(AudioManager.STREAM_MUSIC);
                break;
            case R.id.musicDownbutton: decStreamVolume(AudioManager.STREAM_MUSIC);
                break;
            case R.id.notificationUpbutton: incStreamVolume(AudioManager.STREAM_NOTIFICATION);
                break;
            case R.id.notifDownButton: decStreamVolume(AudioManager.STREAM_NOTIFICATION);
                break;
            case R.id.ringUpButton: incStreamVolume(AudioManager.STREAM_RING);
                break;
            case R.id.ringDownButton: decStreamVolume(AudioManager.STREAM_RING);
                break;
            case R.id.systemUpButton: incStreamVolume(AudioManager.STREAM_SYSTEM);
                break;
            case R.id.systemDownButton: decStreamVolume(AudioManager.STREAM_SYSTEM);
                break;
            case R.id.voiceCallUpButton: incStreamVolume(AudioManager.STREAM_VOICE_CALL);
                break;
            case R.id.voiceCallButton: decStreamVolume(AudioManager.STREAM_VOICE_CALL);
                break;
            default:
                Logger.getAnonymousLogger().warning("COUNDLNB FIND HANDLER");
        }
    }

    void incStreamVolume(int stream)
    {
        int newVolume = am.getStreamVolume(stream) + 1;
        am.setStreamVolume(stream,newVolume,0);
    }

    void decStreamVolume(int stream)
    {
        int newVolume = am.getStreamVolume(stream) - 1;
        am.setStreamVolume(stream,newVolume,0);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

