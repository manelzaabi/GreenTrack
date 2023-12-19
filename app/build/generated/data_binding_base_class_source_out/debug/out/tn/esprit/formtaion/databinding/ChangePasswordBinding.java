// Generated by view binder compiler. Do not edit!
package tn.esprit.formtaion.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import tn.esprit.formtaion.R;

public final class ChangePasswordBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText editTextCurrentPassword;

  @NonNull
  public final EditText editTextNewPassword;

  @NonNull
  public final EditText editTextNewPasswordConfirmation;

  @NonNull
  public final AppCompatButton saveButton;

  private ChangePasswordBinding(@NonNull ConstraintLayout rootView,
      @NonNull EditText editTextCurrentPassword, @NonNull EditText editTextNewPassword,
      @NonNull EditText editTextNewPasswordConfirmation, @NonNull AppCompatButton saveButton) {
    this.rootView = rootView;
    this.editTextCurrentPassword = editTextCurrentPassword;
    this.editTextNewPassword = editTextNewPassword;
    this.editTextNewPasswordConfirmation = editTextNewPasswordConfirmation;
    this.saveButton = saveButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ChangePasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ChangePasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.change_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ChangePasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.editTextCurrentPassword;
      EditText editTextCurrentPassword = ViewBindings.findChildViewById(rootView, id);
      if (editTextCurrentPassword == null) {
        break missingId;
      }

      id = R.id.editTextNewPassword;
      EditText editTextNewPassword = ViewBindings.findChildViewById(rootView, id);
      if (editTextNewPassword == null) {
        break missingId;
      }

      id = R.id.editTextNewPasswordConfirmation;
      EditText editTextNewPasswordConfirmation = ViewBindings.findChildViewById(rootView, id);
      if (editTextNewPasswordConfirmation == null) {
        break missingId;
      }

      id = R.id.saveButton;
      AppCompatButton saveButton = ViewBindings.findChildViewById(rootView, id);
      if (saveButton == null) {
        break missingId;
      }

      return new ChangePasswordBinding((ConstraintLayout) rootView, editTextCurrentPassword,
          editTextNewPassword, editTextNewPasswordConfirmation, saveButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}