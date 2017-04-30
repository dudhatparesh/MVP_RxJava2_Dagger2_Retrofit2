package com.crossover.conferencemanagement.ui.main.editconference;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.data.model.Doctor;
import com.crossover.conferencemanagement.data.model.Invite;
import com.crossover.conferencemanagement.ui.base.BaseFragment;
import com.crossover.conferencemanagement.util.CommonUtils;
import com.crossover.conferencemanagement.util.Constant;
import com.crossover.conferencemanagement.util.DialogListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static android.view.View.GONE;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class EditConferenceFragment extends BaseFragment implements EditConferenceMvpView {

    View view;
    @BindView(R.id.et_topic)
    EditText etTopic;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.tv_invited_doctors)
    TextView tvInvitedDoctors;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.tv_conference_id)
    TextView tvConferenceId;
    @BindView(R.id.ll_conference_id_container)
    LinearLayout llConferenceIdContainer;
    @Inject
    EditConferencePresenter mPresenter;
    private AlertDialog invitedDoctorsDialog;
    private boolean[] selectedDoctors;
    private boolean[] prevSelectedDocs;
    private List<Doctor> doctors;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_edit_conference,
                    container, false);
            ButterKnife.bind(this, view);
            etDate.setInputType(InputType.TYPE_NULL);
            if (getArguments() != null &&
                    getArguments().getParcelable(Constant.Key.CONFERENCE) != null) {
                Conference conference = getArguments().getParcelable(Constant.Key.CONFERENCE);
                loadData(conference);
                btnUpdate.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(GONE);
                llConferenceIdContainer.setVisibility(View.VISIBLE);
            } else {
                btnCancel.setVisibility(GONE);
                btnUpdate.setVisibility(GONE);
                btnAdd.setVisibility(View.VISIBLE);
                llConferenceIdContainer.setVisibility(GONE);
            }
            getComponent().inject(this);
            mPresenter.attachView(this);
        }
        return view;
    }

    private void loadData(Conference conference) {
        etTopic.setText(conference.getTopic());
        etDetail.setText(conference.getDetail());
        etLocation.setText(conference.getLocationName());
        etDate.setText(conference.getDate());
        tvConferenceId.setText(String.valueOf(conference.getId()));
    }

    @OnTouch(R.id.et_date)
    boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {

            case R.id.et_date:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    CommonUtils.showDatePicker(etDate.getText().toString(), getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month++;
                            etDate.setText(String.format("%s-%s-%s", year, month < 10 ? "0"
                                    + month : "" + month, dayOfMonth < 10 ? "0" + dayOfMonth :
                                    "" + dayOfMonth));
                        }
                    });
                break;
        }
        return true;
    }

    @OnClick({R.id.btn_invite_doctors, R.id.btn_cancel, R.id.btn_add, R.id.btn_update})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_invite_doctors:
                mPresenter.loadDoctors();
                break;
            case R.id.btn_update:
                mPresenter.updateConference(
                        tvConferenceId.getText().toString(),
                        etTopic.getText().toString(),
                        etDetail.getText().toString(),
                        etLocation.getText().toString(),
                        etDate.getText().toString(),
                        getSelectedDoctors()
                );
                break;
            case R.id.btn_cancel:
                mPresenter.cancelConference(tvConferenceId.getText().toString());
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.btn_add:
                mPresenter.addConference(
                        etTopic.getText().toString(),
                        etDetail.getText().toString(),
                        etLocation.getText().toString(),
                        etDate.getText().toString(),
                        getSelectedDoctors()
                );
                break;
        }
    }

    private List<Invite> getSelectedDoctors() {
        List<Invite> invites = new ArrayList<>();
        if (selectedDoctors != null) {
            for (int i = 1; i < selectedDoctors.length; i++) {
                if (selectedDoctors[i]) {
                    Invite invite = new Invite();
                    invite.setDoctorId(doctors.get(i - 1).getId());
                    invite.setAdminId(1);
                    invite.setConferenceId(Long.parseLong(tvConferenceId.getText().toString()));
                    invite.setStatus(Constant.InviteStatus.INVITED);
                    invites.add(invite);
                }
            }
        }
        return invites;
    }

    private void showInvitedDoctors() {

        List<String> doctorNames = new ArrayList<>();
        doctorNames.add("Select All");
        for (Doctor doctor : doctors) {
            doctorNames.add(doctor.getName());
        }
        if (selectedDoctors == null) {
            selectedDoctors = new boolean[doctorNames.size()];
        }
        prevSelectedDocs = Arrays.copyOf(selectedDoctors, selectedDoctors.length);
        invitedDoctorsDialog = CommonUtils.getListDialog(getActivity(),
                "Select doctors",
                doctorNames.toArray(new String[doctorNames.size()]),
                selectedDoctors, "Cancel", new DialogListener() {
                    @Override
                    public void onDialogItemSelected(int index) {

                    }

                    @Override
                    public void onDialogCancel() {
                        selectedDoctors = Arrays.copyOf(prevSelectedDocs,
                                prevSelectedDocs.length);
                    }

                    @Override
                    public void onDialogOk() {
                        String tempString = "";
                        for (int i = 1; i < selectedDoctors.length; i++) {
                            if (selectedDoctors[i]) {
                                tempString += doctors.get(i - 1).getName() + "\n";
                            }
                        }
                        tvInvitedDoctors.setText(tempString);
                    }

                    @Override
                    public void onDialogMultiItemSelected(int which, boolean isChecked) {

                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {

                    }
                }).create();
        invitedDoctorsDialog.show();
    }


    @Override
    public void conferenceSaved() {
        Toast.makeText(getContext(), R.string.conference_saved, Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void conferenceUpdated() {
        Toast.makeText(getContext(), R.string.conference_updated, Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void conferenceCancelled() {
        Toast.makeText(getContext(), R.string.conference_cancelled, Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void displayError(Throwable e) {
        Toast.makeText(getContext(), e.getLocalizedMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
        showInvitedDoctors();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
