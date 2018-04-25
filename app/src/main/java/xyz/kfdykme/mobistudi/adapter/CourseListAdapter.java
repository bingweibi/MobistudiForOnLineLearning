package xyz.kfdykme.mobistudi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.structmap.StructMapActivity;
import xyz.kfdykme.mobistudi.bean.StudyModule;
import xyz.kfdykme.mobistudi.bean.StudySubject;
import xyz.kfdykme.mobistudi.eventbus.CourseUnitMapEvent;

/**
 * @author cxf
 * @date 2017/5/26
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private Context context;
    private List<StudyModule> studyModules;
    private LayoutInflater inflater;
    private static final int TYPE_MODULE = 0;
    private static final int TYPE_SUBJECT = 1;

    private List<Data> mData = new ArrayList<>();
    private List<ViewHolder> holders = new ArrayList<>();

    public CourseListAdapter(Context context, List<StudyModule> studyModules) {
        this.context = context;
        this.studyModules = studyModules;
        inflater = LayoutInflater.from(context);

        //initData;
        for( StudyModule studyModule : studyModules){

            Data data = new Data(studyModule.getId(),studyModule.getTitle(),TYPE_MODULE);
            mData.add(data);
            if(studyModule.getStudySubjects() != null){
                for (StudySubject studySubject: studyModule.getStudySubjects()){
                    Data data2 = new Data(studySubject.getId(),studySubject.getTitle(),TYPE_SUBJECT);
                    mData.add(data2);
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;

        switch (viewType) {
            case TYPE_MODULE:
                view = inflater.inflate(R.layout.rv_item_course_list_module,parent,false);
                ModuleViewHolder moduleHolder = new ModuleViewHolder(view);
                holders.add(moduleHolder);
                return moduleHolder;
            case TYPE_SUBJECT:
                view = inflater.inflate(R.layout.rv_item_course_list_subject,parent,false);
                SubjectViewHolder subjectHolder = new SubjectViewHolder(view);
                holders.add(subjectHolder);
                return subjectHolder;
                default:
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        TextView textView;
        if (holder instanceof ModuleViewHolder){
            textView = ((ModuleViewHolder) holder).mTv;
            textView.setText(mData.get(position).getTitle());

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int removeNumber = 0;
                    for (int i =getItemCount()-1; i >=0;i--){
                        if(mData.get(i).getType() == TYPE_SUBJECT) {
                            mData.remove(i);
                            removeNumber++;
                        }
                    }

                    StudyModule studyModule = findModuleByDataId(position == 0 ? position: position-removeNumber);
                    for (int i =0; i < studyModule.getStudySubjects().size();i++){
                        StudySubject s = studyModule.getStudySubjects().get(i);
                        mData.add(position+1+i,new Data(s.getId(),s.getTitle(),TYPE_SUBJECT));
                    }
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof  SubjectViewHolder){
            textView = ((SubjectViewHolder) holder).mTv;
            textView.setText(mData.get(position).getTitle());
            if(((SubjectViewHolder) holder).firstBind){
                textView.setHeight((int) (200 + (Math.random() * 300)));
            }
            ((SubjectViewHolder) holder).firstBind = false;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    StudySubject subject = null;
                    for( StudyModule studyModule : studyModules) {
                        for (StudySubject studySubject : studyModule.getStudySubjects()) {

                            if(studySubject.getId() == mData.get(position).getId()){
                                subject = studySubject;
                            }
                        }
                    }

                    if(subject == null){
                        return;
                    }
                    Intent i = new Intent(context, StructMapActivity.class);
                    context.startActivity(i);

                    // findSubject by title and id
                    EventBus.getDefault().postSticky(new CourseUnitMapEvent(subject));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    private StudyModule findModuleByDataId(int pos){
        for(StudyModule studyModule:studyModules){
            if(mData.get(pos).getId().equals(studyModule.getId())){
                return studyModule;
            }
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ModuleViewHolder extends  ViewHolder{

        TextView mTv;
        ImageView mIv;
        ModuleViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv_title);
            mIv = (ImageView) itemView.findViewById(R.id.imgv_title);
            mTv.setBackgroundColor(Color.rgb((int)(Math.random()*250),(int)(Math.random()*250),(int)(Math.random()*250)));
        }
    }

    public  class SubjectViewHolder extends  ViewHolder{

        TextView mTv;
        boolean firstBind = true;
        SubjectViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.rv_item_course_list_subject_textview);
            mTv.setBackgroundColor(Color.rgb((int)((Math.random()*4)*50)+50,(int)((Math.random()*4)*50)+50,(int)((Math.random()*4)*50)+50));
        }
    }

    public class Data{

        String id;
        String title;
        int type;

        public Data(String id, String title, int type) {
            this.id = id;
            this.title = title;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
