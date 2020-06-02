package com.sc.componentdemo.widget;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 2020/6/2.
 */

public abstract class LazyFragment extends Fragment {
//    FragmentDelegater mFragmentDelegater;

    View rootView;
    boolean isViewCreated = false;
    boolean currentVisableState = false;

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return super.getViewModelStore();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView = inflater.inflate( getLayoutRes(), container, false );
        }
        initview( rootView );
        isViewCreated = true;

        if(getUserVisibleHint()){
            dispatchUserVisibleHint( true );
        }
        return rootView;
    }

    protected abstract void initview(View rootView);

    protected  abstract int getLayoutRes();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint( isVisibleToUser );
        if(isViewCreated){
            if(isVisibleToUser && !currentVisableState){ //不可见 -》 可见  真正加载的时机
                dispatchUserVisibleHint( true ) ;
            }else if(!isVisibleToUser && currentVisableState){  //可见-》 不可见  真正停止加载的时机
                dispatchUserVisibleHint( false );
            }
        }

    }
     //分发事件：是否加载数据的事件
    private void dispatchUserVisibleHint(boolean isVisible){

        if(currentVisableState == isVisible){
            return;
        }
        currentVisableState = isVisible;
        if(isVisible){
            onFragmentLoad();
        }else{
            onFramentLoadStop();
        }
    }
    //子view停止加载数据
    protected void onFramentLoadStop(){

    }
    //子view加载数据
    protected void onFragmentLoad(){

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!currentVisableState && getUserVisibleHint()){
            dispatchUserVisibleHint( true ) ;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(currentVisableState && getUserVisibleHint()){
            dispatchUserVisibleHint( false ) ;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        currentVisableState = false;
    }

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }
}
