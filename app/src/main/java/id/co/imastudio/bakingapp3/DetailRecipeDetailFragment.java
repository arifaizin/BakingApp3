package id.co.imastudio.bakingapp3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import id.co.imastudio.bakingapp3.dummy.DummyContent;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;
import static id.co.imastudio.bakingapp3.MainActivity.POSISIRESEP;
import static id.co.imastudio.bakingapp3.MainActivity.POSISISTEP;
import static id.co.imastudio.bakingapp3.MainActivity.SELECTED_RECIPE;

/**
 * A fragment representing a single DetailRecipe detail screen.
 * This fragment is either contained in a {@link DetailRecipeListActivity}
 * in two-pane mode (on tablets) or a {@link DetailRecipeDetailActivity}
 * on handsets.
 */
public class DetailRecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final String VIDEO_URL = "VIDEOURL";
    private static final String DESCRIPTION = "DESC";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private ArrayList<RecipeModel> recipeList;
    private int posisiResep, posisiStep;

    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;

    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition;

    private String videoURL;
    private String description;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DetailRecipeDetailFragment() {
    }

//    public static DetailRecipeDetailFragment newInstance(String videoURL, String description){
//        DetailRecipeDetailFragment f = new DetailRecipeDetailFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(VIDEO_URL, videoURL);
//        bundle.putString(DESCRIPTION, description);
//        f.setArguments(bundle);
//        return f;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            recipeList = getArguments().getParcelableArrayList(SELECTED_RECIPE);
            posisiResep = getArguments().getInt(POSISIRESEP);
            posisiStep = getArguments().getInt(POSISISTEP);
            videoURL = recipeList.get(posisiResep).getSteps().get(posisiStep).getVideoURL();
            description = recipeList.get(posisiResep).getSteps().get(posisiStep).getDescription();
            Log.d(TAG, "VideoURL : " + videoURL);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Step " + posisiStep);
            }

//            if (getArguments() != null && getArguments().containsKey(VIDEO_URL) && getArguments().containsKey(DESCRIPTION)){
//                Bundle bundle = getArguments();
//                videoURL = bundle.getString(VIDEO_URL);
//                description = bundle.getString(DESCRIPTION);
//            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detailrecipe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.detailrecipe_detail)).setText(description);
        }

        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_view);

        return rootView;
    }

    private void initializePlayer() {
        if (videoURL.isEmpty()) {
            playerView.setVisibility(View.GONE);
        }

//        TrackSelection.Factory adaptiveTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(3000);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(videoURL);
//        Toast.makeText(getActivity(), "UREL"+uri.toString(), Toast.LENGTH_SHORT).show();

        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
