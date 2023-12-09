package declan.prjct.settings.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.animation.PathInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.settings.R;

public class DeclanBatteryProgress extends RelativeLayout {

	private ProgressBar batteryProgress;
	private int batteryStatus, batteryLevel;

	private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			batteryStatus = intent.getIntExtra("status", 1);
			batteryLevel = intent.getIntExtra("level", 0);
			setupBatteryProgress();
		}
	};

	public DeclanBatteryProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		context.registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		batteryProgress = findViewById(R.id.battery_progressbar);
	}

	private void setupBatteryProgress() {
		batteryProgress.setProgress(batteryLevel);
		batteryProgress.setInterpolator(new PathInterpolator(0.33f, 0.11f, 0.2f, 1.0f));
		if (batteryStatus != 2 || batteryLevel == 100) {
			batteryProgress.setIndeterminate(false);
		} else {
			batteryProgress.setIndeterminate(true);
		}
	}
}