package com.jslee.restApiTools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.jslee.restApiTools.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBinding();
        setUpObserver();

    }
    private void setUpBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = obtainViewModel();
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        mViewModel.init();
    }

    private MainViewModel obtainViewModel() {
        ViewModelProvider.AndroidViewModelFactory viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        return new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
    }

    private void setUpObserver() {
        /* throw를 통해 발생시킨 에러(crashlytics에 보낼것)*/
        mViewModel.getGetBitmap().observe(this, (clickedBtnId -> {
            if (clickedBtnId.equals(Code.NETWORKING.UrlConnection)) {


            }else if(clickedBtnId.equals(Code.NETWORKING.OKHttp)){
                binding.imageView2.setImageBitmap(mViewModel.getBitmap());

            }else if(clickedBtnId.equals(Code.NETWORKING.Retrofit)){
                binding.imageView3.setImageBitmap(mViewModel.getBitmap());
            }
        }));

    }

}