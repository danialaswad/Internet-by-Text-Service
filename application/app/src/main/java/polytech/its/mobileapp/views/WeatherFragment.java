package polytech.its.mobileapp.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import polytech.its.mobileapp.R;
import polytech.its.mobileapp.weather.WeatherData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WeatherFragment.
     */
    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        return v;
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

    public void setDisplay(String weatherInfo) {
        WeatherData wd = WeatherData.getInstance(weatherInfo);

        TextView city = (TextView) getView().findViewById(R.id.textView_city);
        city.setText(wd.getCity());
        TextView temperature = (TextView) getView().findViewById(R.id.textView_temperature);
        temperature.setText(wd.getTemperature());
        TextView description = (TextView) getView().findViewById(R.id.textview_description);
        description.setText(wd.getDescription());
        TextView wind = (TextView) getView().findViewById(R.id.textview_wind);
        wind.setText(wd.getWind());
        TextView humidity = (TextView) getView().findViewById(R.id.textview_humidity);
        humidity.setText(wd.getHumidity());
        TextView sunset = (TextView) getView().findViewById(R.id.textview_sunset);
        sunset.setText(wd.getSunset());
        TextView sunrise = (TextView) getView().findViewById(R.id.textview_sunrise);
        sunrise.setText(wd.getSunrise());

        String iconId = wd.getIconName();

        ImageView icon = (ImageView) getView().findViewById(R.id.imageView);

        changeIcon(iconId, icon);
    }

    private void changeIcon(String iconId, ImageView icon) {
        int iconValue = Integer.parseInt(iconId);
        if (iconValue != 800) {
            iconValue /= 100;

            switch (iconValue) {
                case 2:
                    icon.setImageResource(R.drawable.thunder);
                    break;
                case 3:
                    icon.setImageResource(R.drawable.drizzle);
                    break;
                case 7:
                    icon.setImageResource(R.drawable.foggy);
                    break;
                case 8:
                    icon.setImageResource(R.drawable.clouds);
                    break;
                case 6:
                    icon.setImageResource(R.drawable.winter);
                    break;
                case 5:
                    icon.setImageResource(R.drawable.rain);
                    break;
                default:
                    icon.setImageResource(R.drawable.default_weather);
                    break;
            }
        } else {
            icon.setImageResource(R.drawable.sun);
        }
        ImageView wind = (ImageView) getView().findViewById(R.id.imageView_wind);
        ImageView humidity = (ImageView) getView().findViewById(R.id.imageView_humidity);
        ImageView sunset = (ImageView) getView().findViewById(R.id.imageView_sunset);
        ImageView sunrise = (ImageView) getView().findViewById(R.id.imageView_sunrise);

        icon.setVisibility(View.VISIBLE);
        wind.setVisibility(View.VISIBLE);
        humidity.setVisibility(View.VISIBLE);
        sunset.setVisibility(View.VISIBLE);
        sunrise.setVisibility(View.VISIBLE);
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
