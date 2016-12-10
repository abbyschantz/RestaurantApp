package aschantz.restaurantapp;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.FirebaseDatabase;

import aschantz.restaurantapp.model.Post;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aschantz on 12/2/16.
 */
public class CreatePostActivity extends BaseActivity implements PlaceSelectionListener {

    private static final String LOG_TAG = "PlaceSelectionListener";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private TextView locationTextView;
    private TextView attributionsTextView;
    private String placeName;
    private String googleRating;
    private RatingBar userRatingBar;
    private String userRating;

    String priceRange;
    String res;
    String gluten;
    String veg;
    String wait;
    String alc;


    @BindView(R.id.etEthnicity)
    EditText etEthnicity;
    @BindView(R.id.etBody)
    EditText etBody;
    @BindView(R.id.etDishes)
    EditText etDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        ButterKnife.bind(this);

        locationTextView = (TextView) findViewById(R.id.txt_location);
        attributionsTextView = (TextView) findViewById(R.id.txt_attributions);

        userRatingBar = (RatingBar) findViewById(R.id.userRatingBar);
        userRating = Float.toString(userRatingBar.getRating());

//        spinnerItemType = (Spinner) findViewById(R.id.resAccepted);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.resAccepted_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerItemType.setAdapter(adapter);
//        resAcceptedString = spinnerItemType.getSelectedItem().toString();

        // PLACES AUTOCOMPLETE FRAGMENT
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint("Search a Location");
        //autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);
    }

    @OnClick(R.id.btnSend)
    void sendClick() {
        if(!isFormValid()) {
            return;
        }

        //get reference to the firebase database, then say we want to work with the post child
        //want to create new method under the posts
        String key = FirebaseDatabase.getInstance().getReference().child("posts").push().getKey();
        userRating = Float.toString(userRatingBar.getRating());
        Post newPost = new Post(getUid(), getUserName(), placeName, etEthnicity.getText().toString(), etBody.getText().toString(),
                userRating, googleRating, priceRange, etDishes.getText().toString(), res, veg, gluten, wait, alc);

        //makes branch in database for post, then the key, then the post data
        FirebaseDatabase.getInstance().getReference().child("posts").child(key).setValue(newPost);

        Toast.makeText(this, "Post created", Toast.LENGTH_SHORT).show();

        finish();
    }

    private boolean isFormValid() {
        boolean result = true;
        if (TextUtils.isEmpty(etEthnicity.getText().toString())) {
            etEthnicity.setError("Required");
            result = false;
        } else {
            etEthnicity.setError(null);
        }

        if (TextUtils.isEmpty(etBody.getText().toString())) {
            etBody.setError("Required");
            result = false;
        } else {
            etBody.setError(null);
        }

        return result;
    }

    @Override
    public void onPlaceSelected(Place place) {
        Log.i(LOG_TAG, "Place Selected: " + place.getName());
        locationTextView.setText(getString(R.string.place_data, place.getName(),
                place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri(),
                place.getRating()));
        placeName = getString(R.string.place_name, place.getName());
        googleRating = getString(R.string.place_rating, place.getRating());
        if (!TextUtils.isEmpty(place.getAttributions())){
            attributionsTextView.setText(Html.fromHtml(place.getAttributions().toString()));
        }
    }

    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            //PRICE
            case R.id.basicallyFree:
                if (checked)
                    priceRange = getResources().getString(R.string.basically_free);
                    break;
            case R.id.cheap:
                if (checked)
                    priceRange = getResources().getString(R.string.affordable);
                    break;
            case R.id.moderate:
                if (checked)
                    priceRange = getResources().getString(R.string.techy);
                    break;
            case R.id.expensive:
                if (checked)
                    priceRange = getResources().getString(R.string.paycheck);
                    break;
            case R.id.unknownPrice:
                if (checked)
                    priceRange = getResources().getString(R.string.unknown);
                break;
            //RESERVATIONS
        } switch (view.getId()) {
            case R.id.notAccepted:
                if (checked)
                    res = getResources().getString(R.string.not_accepted);
                break;
            case R.id.accepted:
                if (checked)
                    res = getResources().getString(R.string.accepted);
                break;
            case R.id.recommended:
                if (checked)
                    res = getResources().getString(R.string.recommended);
                break;
            case R.id.hardToGet:
                if (checked)
                    res = getResources().getString(R.string.hard_to_get);
                break;
            case R.id.unknownRes:
                if (checked)
                    res = getResources().getString(R.string.unknown);
                break;
            //VEGETARIAN OPTIONS
        } switch (view.getId()) {
            case R.id.veg_none:
                if (checked)
                    veg = getResources().getString(R.string.no_options);
                break;
            case R.id.veg_few:
                if (checked)
                    veg = getResources().getString(R.string.few_options);
                break;
            case R.id.veg_lots:
                if (checked)
                    veg = getResources().getString(R.string.lots_options);
                break;
            case R.id.veg_only:
                if (checked)
                    veg = getResources().getString(R.string.only_options);
                break;
            case R.id.unknownVeg:
                if (checked)
                    veg = getResources().getString(R.string.unknown);
                break;
            //GLUTEN
        } switch (view.getId()) {
            case R.id.gluten_none:
                if (checked)
                    gluten = getResources().getString(R.string.no_options);
                break;
            case R.id.gluten_few:
                if (checked)
                    gluten = getResources().getString(R.string.few_options);
                break;
            case R.id.gluten_lots:
                if (checked)
                    gluten = getResources().getString(R.string.lots_options);
                break;
            case R.id.gluten_only:
                if (checked)
                    gluten = getResources().getString(R.string.only_options);
                break;
            case R.id.unknownGluten:
                if (checked)
                    gluten = getResources().getString(R.string.unknown);
                break;
            //wait time
        } switch (view.getId()) {
            case R.id.no_wait:
                if (checked)
                    wait = getResources().getString(R.string.no_wait);
                break;
            case R.id.short_wait:
                if (checked)
                    wait = getResources().getString(R.string.short_wait);
                break;
            case R.id.long_wait:
                if (checked)
                    wait = getResources().getString(R.string.long_wait);
                break;
            case R.id.forever_wait:
                if (checked)
                    wait = getResources().getString(R.string.forever);
                break;
            case R.id.unknownWait:
                if (checked)
                    wait = getResources().getString(R.string.unknown);
                break;
            //alc
        } switch (view.getId()) {
            case R.id.no_alcohol:
                if (checked)
                    alc = getResources().getString(R.string.no_alcohol);
                break;
            case R.id.soft_alcohol:
                if (checked)
                    alc = getResources().getString(R.string.soft_alcohol);
                break;
            case R.id.hard_alcohol:
                if (checked)
                    alc = getResources().getString(R.string.hard_to_get);
                break;
            case R.id.all_alcohol:
                if (checked)
                    alc = getResources().getString(R.string.all_alcohol);
                break;
            case R.id.unknownAlcohol:
                if (checked)
                    alc = getResources().getString(R.string.unknown);
                break;
        }


    }



}
