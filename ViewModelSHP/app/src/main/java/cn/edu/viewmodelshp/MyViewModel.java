package cn.edu.viewmodelshp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

@SuppressWarnings("ConstantConditions")
public class MyViewModel extends AndroidViewModel {

    private SavedStateHandle savedStateHandle;
    private String key = getApplication().getResources().getString(R.string.data_key);
    private String shpName = getApplication().getResources().getString(R.string.shp_name);

    public MyViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        this.savedStateHandle = savedStateHandle;

        if (!savedStateHandle.contains(key)) {
            load();
        }
    }

    public LiveData<Integer> getNumber() {
        return savedStateHandle.getLiveData(key);
    }

    private void load() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpName, Context.MODE_PRIVATE);
        int x = shp.getInt(key, 0);
        savedStateHandle.set(key, x);
    }

    void save() {
        SharedPreferences shp = getApplication().getSharedPreferences(shpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(key, getNumber().getValue());
        editor.apply();
    }

    public void add(int x) {
        savedStateHandle.set(key, getNumber().getValue() + x);
    }

    public void zero(){
        savedStateHandle.set(key, 0);
    }
}
