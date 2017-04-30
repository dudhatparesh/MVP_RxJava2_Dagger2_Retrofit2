package com.crossover.conferencemanagement.ui.main.invites;

import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.data.model.Invite;
import com.crossover.conferencemanagement.ui.base.MvpView;

import java.util.List;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public interface InvitesMvpView extends MvpView {
    void displayEmptyList();

    void displayError(String message);

    void displayError(int message);

    void displayInvites(List<Conference> conferences);

    void displayToast(String message);
    void displayToast(int messageID);
}
