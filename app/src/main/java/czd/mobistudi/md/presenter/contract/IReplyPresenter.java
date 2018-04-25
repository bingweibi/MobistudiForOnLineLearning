package czd.mobistudi.md.presenter.contract;

import android.support.annotation.NonNull;

import czd.mobistudi.md.model.entity.Reply;

public interface IReplyPresenter {

    void upReplyAsyncTask(@NonNull Reply reply);

}
