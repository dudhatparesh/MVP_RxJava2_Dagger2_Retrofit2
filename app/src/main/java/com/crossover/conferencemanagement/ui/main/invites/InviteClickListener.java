package com.crossover.conferencemanagement.ui.main.invites;

import com.crossover.conferencemanagement.data.model.Conference;

/**
 * Created by PareshDudhat on 14-03-2017.
 */

interface InviteClickListener {
    void onClick(Conference conference);
    void accept(Conference conference);
    void reject(Conference conference);
}
