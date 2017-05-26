package com.movie.mvp.view;

import android.support.v7.app.AppCompatActivity;

import com.framework.base.mvp.BaseListView;
import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.BaseView;
import com.movie.mvp.model.MovieModel;

import java.util.List;

/**
 * by y on 2017/5/23.
 */

public interface ViewManager {
    interface Dy2018DetailView extends BaseView<MovieModel> {
    }

    interface Dy2018ListView extends BaseListView<List<MovieModel>> {
    }

    interface DyttChosenView extends BaseView<List<MovieModel>> {
    }

    interface DyttVideoDetailView extends BaseView<MovieModel> {
    }

    interface DyttNewView extends BaseView<List<MovieModel>> {
    }

    interface DyttVideoMoreView extends BaseListView<List<MovieModel>> {
    }

    interface DyttXLMoreView extends BaseView<List<MovieModel>> {
    }

    interface PiaoHuaListView extends BaseListView<List<MovieModel>> {
    }

    interface PiaoHuaDetailView extends BaseView<MovieModel> {
    }

    interface XiaoPianDetailView extends BaseView<MovieModel> {
    }

    interface XiaoPianListView extends BaseListView<List<MovieModel>> {
    }

    interface MainView extends BaseView<BaseModel> {

        AppCompatActivity getMainActivity();

        void selectMenuFirst();

        void onBack();

    }

}
