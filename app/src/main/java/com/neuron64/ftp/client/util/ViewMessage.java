package com.neuron64.ftp.client.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Neuron on 17.09.2017.
 */

public class ViewMessage {

    public static void initSnackBarLong(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void initSnackBarLong(View view, @StringRes int message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void initSnackBarShort(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void initSnackBarShort(View view, @StringRes int message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void initSnackBarIndefinite(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).show();
    }

    public static void initSnackBarIndefinite(View view, @StringRes int message){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).show();
    }

    public static void initSnackBarLongAction(View view, @StringRes int message, @StringRes int messageAction, View.OnClickListener onClickListener){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(messageAction, onClickListener).show();
    }

    public static void initToast(Context context, String message, boolean isLong){
        Toast.makeText(context, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
