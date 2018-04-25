package czd.mobistudi.md.presenter.contract;

import android.support.annotation.NonNull;

import czd.mobistudi.md.model.entity.Tab;

public interface IMainPresenter {

    void switchTab(@NonNull Tab tab);

    void refreshTopicListAsyncTask();

    void loadMoreTopicListAsyncTask(int page);

    void getUserAsyncTask();

    void getMessageCountAsyncTask();

}
