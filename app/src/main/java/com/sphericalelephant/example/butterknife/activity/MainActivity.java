package com.sphericalelephant.example.butterknife.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sphericalelephant.example.butterknife.R;

import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * This is an example illustrating the use of ButterKnife, a very simple dependency injection (view injection) framework for android.
 * ButterKnife can be found here (https://github.com/JakeWharton/butterknife)
 * <p/>
 * We are using AppCompatActivity to stay compatible with future releases of Android. Originally, AppCompatActivity
 * was used to provide Actionbar functionality (now known as the toolbar) to older Android systems, but starting from
 * 5.x.x it is also required to implement Material design features.
 */
public class MainActivity extends AppCompatActivity {
	/**
	 * We are binding the view instance to the view here, this is equivalent to calling
	 * <p/>
	 * testButton = (Button) findViewById(R.id.activity_mainactivity_b_test);
	 * <p/>
	 * after setContentView(...)
	 */
	@Bind(R.id.activity_mainactivity_b_test)
	Button testButton;

	/**
	 * Similar to the above binding, but is more fault tolerant: if the view cannot be found, no exception will be thrown
	 */
	@Nullable
	@Bind(R.id.activity_mainactivity_tv_mightnotbehere)
	TextView textView;

	@Bind(R.id.activity_mainactivity_iv_image)
	ImageView image;

	/**
	 * There are many more resource binding annotations, try @Bind<autocomplete>, here is a small sample
	 */
	@BindDrawable(R.drawable.activity_mainactivity_sellogo)
	Drawable sphericalElephantLogo;

	/**
	 * You can also use bind to bind views to lists and arrays
	 */
	@Bind({R.id.activity_mainactivity_b_test, R.id.activity_mainactivity_iv_image, R.id.activity_mainactivity_tv_mightnotbehere})
	List<View> viewsToHideOrShow;

	/**
	 * As a point of reference, these two buttons are bound without using ButterKnife
	 */
	private Button hide, show;

	private ButterKnife.Action hideAction = new ButterKnife.Action() {
		@Override
		public void apply(View view, int index) {
			view.setVisibility(View.INVISIBLE);
		}
	};

	private ButterKnife.Action showAction = new ButterKnife.Action() {
		@Override
		public void apply(View view, int index) {
			view.setVisibility(View.VISIBLE);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainactivity);

		// binding button and setting onClick listener manually
		hide = (Button) findViewById(R.id.activity_mainactivity_b_hide);
		hide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// pseudo lambda gets applied to all views in viewToHideOrShow
				ButterKnife.apply(viewsToHideOrShow, hideAction);
			}
		});

		// binding button and setting onClick listener manually
		show = (Button) findViewById(R.id.activity_mainactivity_b_show);
		show.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// pseudo lambda gets applied to all views in viewToHideOrShow
				ButterKnife.apply(viewsToHideOrShow, showAction);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		ButterKnife.bind(this); // takes a view as second parameter to allow binding to arbitrary views (uses view as parent)
		image.setImageDrawable(sphericalElephantLogo);
	}

	@Override
	protected void onPause() {
		super.onPause();
		ButterKnife.unbind(this); // unbind!
	}

	/**
	 * This annotation sets an onClick listener. You can provide a parameter here (instance of View) that will automatically casted
	 * to the type of the View in question.
	 */
	@OnClick(R.id.activity_mainactivity_b_test)
	public void testButtonClicked(Button testButton) {
		Toast.makeText(getApplicationContext(), R.string.activity_mainactivity_testpressed, Toast.LENGTH_SHORT).show();
	}
}
