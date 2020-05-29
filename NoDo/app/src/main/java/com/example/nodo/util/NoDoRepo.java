package com.example.nodo.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.nodo.data.NoDoDao;
import com.example.nodo.data.NoDoDatabase;
import com.example.nodo.model.NoDO;

import java.util.List;

public class NoDoRepo {

    private NoDoDao noDoDao;
    private LiveData<List<NoDO>> allNoDos;

    public NoDoRepo(Application application){
        NoDoDatabase db = NoDoDatabase.getInstance(application);

        noDoDao = db.noDoDao();
        allNoDos = noDoDao.getAllNoDos();
    }

    public LiveData<List<NoDO>> getAllNoDos(){
        return allNoDos;
    }

    public void insert(NoDO noDO){
        new insertAsyncTask(noDoDao).execute(noDO);
    }

    private class insertAsyncTask extends AsyncTask<NoDO, Void, Void> {
        private NoDoDao asyncTaskDao;
        public insertAsyncTask(NoDoDao dao) {
            asyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(NoDO... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
