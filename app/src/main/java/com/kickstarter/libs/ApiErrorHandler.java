package com.kickstarter.libs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.kickstarter.R;
import com.kickstarter.services.ApiError;

import net.hockeyapp.android.ExceptionHandler;

import retrofit.RetrofitError;

public abstract class ApiErrorHandler {
  final Throwable e;
  final Context context;

  public ApiErrorHandler(@NonNull final Throwable e, @NonNull final Context context) {
    this.e = e;
    this.context = context;
  }

  public void handleError() {
    if (e instanceof ApiError) {
      handleApiError((ApiError) e);
    } else if (e instanceof RetrofitError) {
      final RetrofitError retrofitError = (RetrofitError) e;
      if (retrofitError.getKind() == RetrofitError.Kind.NETWORK) {
        displayError(R.string.Unable_to_connect);
      } else {
        ExceptionHandler.saveException(e, null);
      }
    } else {
      ExceptionHandler.saveException(e, null);
    }
  }

  public abstract void handleApiError(final ApiError apiError);

  public void displayError(@StringRes final int id) {
    // Toast by default, but this could be overridden
    final Toast toast = Toast.makeText(context, context.getString(id), Toast.LENGTH_LONG);
    toast.show();
  }
}
