package com.example.sumankhatiwada.vehiclebazzar.mvp.view;

import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.CarPostResponses;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.MessageDTO;
import com.example.sumankhatiwada.vehiclebazzar.mvp.model.dbmodels.RegisterRequestAndProfileResponses;

/**
 * Created by sumankhatiwada on 4/18/18.
 */

public interface DashBoardView extends BaseView {

    void onLogoutSuccess();
    void onShowDialog(String message);
    void onHideDialog();
    void onShowToast(String message);
    void onViewSuccess(RegisterRequestAndProfileResponses registerRequestAndProfileResponses);
    void onNotifiedSuccess(MessageDTO messageDTO);

    void onCommentSuccess();
    void onAddPostSuccess();
}
