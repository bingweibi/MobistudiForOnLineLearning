package czd.mobistudi.md.presenter.contract;

import android.support.annotation.NonNull;

import czd.mobistudi.md.model.entity.Tab;

public interface ICreateTopicPresenter {

    void createTopicAsyncTask(@NonNull Tab tab, String title, String content);

}
