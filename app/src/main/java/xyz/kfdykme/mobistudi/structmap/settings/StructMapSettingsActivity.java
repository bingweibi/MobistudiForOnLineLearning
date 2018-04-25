package xyz.kfdykme.mobistudi.structmap.settings;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.common.MobiActivity;
import xyz.kfdykme.mobistudi.structmap.StructMapConstant;
import xyz.kfdykme.mobistudi.tool.FileHelper;

public class StructMapSettingsActivity extends MobiActivity {

    Map<String,Integer> colors = new HashMap<>();


    int colorBackGrpund;
    int colorLine;
    int colorNode;
    int colorText;
    int colorStartNode;
    int colorArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_struct_map_settings,null);
        setContentView(root);
        initToolbar((Toolbar)findViewById(R.id.toolbar));

        String colorString ="";
        try {
            colorString= FileHelper.readFile(StructMapConstant.RDIR_MAP,StructMapConstant.COLOR_FILE_NAME,StructMapConstant.COLOR_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(colorString.equals(FileHelper.NOT_FIND)){


            colorBackGrpund = Color.parseColor("#00343f");
            colorText = Color.parseColor("#1db0b8");
            colorLine = Color.parseColor("#37c6c0");
            colorNode = Color.parseColor("#d0e9ff");
            colorStartNode = Color.parseColor("#33aabb");
            colorArc = Color.parseColor("#eeeeee");
        } else{
            colors = new Gson().fromJson(colorString,new TypeToken<Map<String,Integer>>(){}.getType());
            colorBackGrpund = colors.get(StructMapConstant.KEY_BACKGROUND_COLOR);
            colorText = colors.get(StructMapConstant.KEY_TEXT_COLOR);
            colorLine = colors.get(StructMapConstant.KEY_LINE_COLOR);
            colorNode = colors.get(StructMapConstant.KEY_NODE_COLOR);
            colorStartNode = colors.get(StructMapConstant.KEY_START_COLOR);
            colorArc = colors.get(StructMapConstant.KEY_ARC_COLOR);
        }

//
//        TextView tv_bg = (TextView) findViewById(R.id.tv_bg_color);
//        TextView tv_arc = (TextView) findViewById(R.id.tv_arc_color);
//        TextView tv_line = (TextView) findViewById(R.id.tv_line_color);
//        TextView tv_node = (TextView) findViewById(R.id.tv_node_color);
//        TextView tv_start = (TextView) findViewById(R.id.tv_start_color);
//        TextView tv_text = (TextView) findViewById(R.id.tv_text_color);
//
//        tv_arc.setBackgroundColor(colorArc);
//        tv_bg.setBackgroundColor(colorBackGrpund);
//        tv_line.setBackgroundColor(colorLine);
//        tv_node.setBackgroundColor(colorNode);
//        tv_start.setBackgroundColor(colorStartNode);
//        tv_text.setBackgroundColor(colorText);
//
//
//        //r
//        SeekBar sb_bg1 = (SeekBar) findViewById(R.id.sb1_bg_color);
//        SeekBar sb_arc1 = (SeekBar) findViewById(R.id.sb1_arc_color);
//        SeekBar sb_line1 = (SeekBar) findViewById(R.id.sb1_line_color);
//        SeekBar sb_node1 = (SeekBar) findViewById(R.id.sb1_node_color);
//        SeekBar sb_start1 = (SeekBar) findViewById(R.id.sb1_start_color);
//        SeekBar sb_text1 = (SeekBar) findViewById(R.id.sb1_text_color);
//
//        sb_bg1.setProgress(color2progress(Color.red(colorBackGrpund)));
//        sb_arc1.setProgress(color2progress(Color.red(colorBackGrpund)));
//        sb_line1.setProgress(color2progress(Color.red(colorBackGrpund)));
//        sb_node1.setProgress(color2progress(Color.red(colorBackGrpund)));
//        sb_start1.setProgress(color2progress(Color.red(colorBackGrpund)));
//        sb_text1.setProgress(color2progress(Color.red(colorBackGrpund)));
//
//        //g
//        SeekBar sb_bg2 = (SeekBar) findViewById(R.id.sb2_bg_color);
//        SeekBar sb_arc2 = (SeekBar) findViewById(R.id.sb2_arc_color);
//        SeekBar sb_line2 = (SeekBar) findViewById(R.id.sb2_line_color);
//        SeekBar sb_node2 = (SeekBar) findViewById(R.id.sb2_node_color);
//        SeekBar sb_start2 = (SeekBar) findViewById(R.id.sb2_start_color);
//        SeekBar sb_text2 = (SeekBar) findViewById(R.id.sb2_text_color);
//
//
//        //b
//        SeekBar sb_bg3 = (SeekBar) findViewById(R.id.sb3_bg_color);
//        SeekBar sb_arc3 = (SeekBar) findViewById(R.id.sb3_arc_color);
//        SeekBar sb_line3 = (SeekBar) findViewById(R.id.sb3_line_color);
//        SeekBar sb_node3 = (SeekBar) findViewById(R.id.sb3_node_color);
//        SeekBar sb_start3 = (SeekBar) findViewById(R.id.sb3_start_color);
//        SeekBar sb_text3 = (SeekBar) findViewById(R.id.sb3_text_color);
//
//
//        for (int i =0; i < root.getChildCount();i++){
//            View v = root.getChildAt(i);
//            if(v.getClass().equals(sb_bg1.getClass())){
//                SeekBar sbv = (SeekBar)v;
//                sbv.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
//            }
//        }
    }

    public int progress2color(int pro){
        return 255 * pro /100;
    }

    public int color2progress(int color){
        return color/255;
    }




}
