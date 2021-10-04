package com.harsh.admobrewardedad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AdsActivity extends AppCompatActivity implements RewardedVideoAdListener {

    public RewardedAd rewardedAd;
    private final String TAG = "MainActivity";
    private Button loadad;
    private RewardedVideoAd mAd;
    boolean pressed = true;
    private Handler mHandler = new Handler();

    private InterstitialAd mInterstitialAd;
    private ScheduledExecutorService scheduler;
    private boolean isVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);


        loadad=findViewById(R.id.loadad);


        RewardedVideoAd mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544/5224354917");

        rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");

        LOadAd();

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.d(TAG, "succesfuly Loaded");
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
                Log.d(TAG, adError.getMessage());
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadad.performClick();
//            }
//        }, 5000);

        LOadAd();


        loadad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LOadAd();

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(AdsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }, 10000);

    }

    private  void  LOadAd(){

        createAndLoadRewardedAd();
        if (rewardedAd.isLoaded()) {
            Activity activityContext = AdsActivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {





                    // Ad closed.
                    createAndLoadRewardedAd();
                    rewardedAd = new RewardedAd(AdsActivity.this,
                            "ca-app-pub-3940256099942544/5224354917");

                    RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                        @Override
                        public void onRewardedAdLoaded() {
                            // Ad successfully loaded.
                            Log.d(TAG, "succesfuly Loaded");
                        }

                        @Override
                        public void onRewardedAdFailedToLoad(LoadAdError adError) {
                            // Ad failed to load.
                            Log.d(TAG, adError.getMessage());
                        }
                    };

                    if (rewardedAd.isLoaded()) {
                        Activity activityContext = AdsActivity.this;
                        RewardedAdCallback adCallback = new RewardedAdCallback() {
                            @Override
                            public void onRewardedAdOpened() {
                                // Ad opened.
                            }

                            @Override
                            public void onRewardedAdClosed() {
                                // Ad closed.
                                createAndLoadRewardedAd();
                                rewardedAd = new RewardedAd(AdsActivity.this,
                                        "ca-app-pub-3940256099942544/5224354917");

                                RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                                    @Override
                                    public void onRewardedAdLoaded() {
                                        // Ad successfully loaded.
                                        Log.d(TAG, "succesfuly Loaded");
                                    }

                                    @Override
                                    public void onRewardedAdFailedToLoad(LoadAdError adError) {
                                        // Ad failed to load.
                                        Log.d(TAG, adError.getMessage());
                                    }
                                };
                                rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                            }

                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem reward) {
                                // User earned reward.
                            }

                            @Override
                            public void onRewardedAdFailedToShow(AdError adError) {
                                // Ad failed to display.
                            }
                        };
                        rewardedAd.show(activityContext, adCallback);


                    }
                    rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    // Ad failed to display.
                }
            };
            rewardedAd.show(activityContext, adCallback);


        }

    }

    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
                Log.e("sd","sdcsdcdcdfc");
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }

    public void onRewardedAdClosed() {
        this.rewardedAd = createAndLoadRewardedAd();
    }



    @Override
    protected void onRestart() {
        super.onRestart();

        LOadAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // LOadAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LOadAd();
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        LOadAd();
    }

    @Override
    public void onRewarded(com.google.android.gms.ads.reward.RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        LOadAd();
    }

    @Override
    protected void onStart(){
        super.onStart();
        isVisible = true;
        if(scheduler == null){
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    Log.i("hello", "world");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (rewardedAd.isLoaded() && isVisible) {
                                Activity activityContext = AdsActivity.this;
                                RewardedAdCallback adCallback = new RewardedAdCallback() {
                                    @Override
                                    public void onRewardedAdOpened() {
                                        // Ad opened.
                                    }

                                    @Override
                                    public void onRewardedAdClosed() {
                                        // Ad closed.
                                        createAndLoadRewardedAd();
                                        rewardedAd = new RewardedAd(AdsActivity.this,
                                                "ca-app-pub-3940256099942544/5224354917");

                                        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                                            @Override
                                            public void onRewardedAdLoaded() {
                                                // Ad successfully loaded.
                                                Log.d(TAG, "succesfuly Loaded");
                                            }

                                            @Override
                                            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                                                // Ad failed to load.
                                                Log.d(TAG, adError.getMessage());
                                            }
                                        };
                                        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                                    }

                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem reward) {
                                        // User earned reward.
                                    }

                                    @Override
                                    public void onRewardedAdFailedToShow(AdError adError) {
                                        // Ad failed to display.
                                    }
                                };
                            } else {
                                Log.d("TAG"," Interstitial not loaded");
                            }

                            LOadAd();
                        }
                    });
                }
            }, 10, 10, TimeUnit.SECONDS);

        }

    }
    //.. code

    @Override
    protected void onStop() {
        super.onStop();
        scheduler.shutdownNow();
        scheduler = null;
        isVisible =false;
    }

}