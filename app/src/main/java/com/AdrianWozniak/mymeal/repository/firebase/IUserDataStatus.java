package com.AdrianWozniak.mymeal.repository.firebase;

import com.AdrianWozniak.mymeal.model.UserData;

public interface IUserDataStatus {
    void status(String message, boolean isDataReadCorrectly, UserData userData);
}
