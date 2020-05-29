package com.example.nodo.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nodo.data.NoDoDatabase;
import com.example.nodo.util.NoDoRepo;

import java.util.List;

public class NoDoViewModel extends AndroidViewModel {
    private NoDoRepo noDoRepo;
    private LiveData<List<NoDO>> allNoDos;

    public NoDoViewModel(@NonNull Application application) {
        super(application);
        noDoRepo = new NoDoRepo(application);
        allNoDos = noDoRepo.getAllNoDos();
    }

    public LiveData<List<NoDO>> getAllNoDos() {
        return allNoDos;
    }

    public void insert(NoDO noDO){
        noDoRepo.insert(noDO);
    }
}
