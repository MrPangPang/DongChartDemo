package com.example.dongtest.dongchartdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Swift_Dong on 2017/4/27.
 * 微博 http://blog.sina.com.cn/u/2620141053
 *
 *
 * 感谢  http://blog.csdn.net/nugongahou110/article/details/49517725
 *       http://blog.csdn.net/zly921112/article/details/50401976
 */
public class MainActivity extends AppCompatActivity {

    private ChartBar mBarGraph;
    private ArrayList<Integer> respectTarget;
    private ArrayList<String> respName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        respectTarget = new ArrayList<Integer>();
        respName = new ArrayList<String>();
        respectTarget.add(130);
        respectTarget.add(20);
        respectTarget.add(18);
        respectTarget.add(29);
        respectTarget.add(10);
        respectTarget.add(8);
        respectTarget.add(5);
        respectTarget.add(5);
        respectTarget.add(5);
        respName.add("8日");
        respName.add("9日");
        respName.add("10日");
        respName.add("11日");
        respName.add("12日");
        respName.add("13日");
        respName.add("14日");
        respName.add("14日");
        respName.add("14日");
        respName.add("14日");
        mBarGraph = (ChartBar) findViewById(R.id.char_bar);
        mBarGraph.setRespectTargetNum(respectTarget);
        mBarGraph.setRespectName(respName);
        mBarGraph.setTotalBarNum(9);
        mBarGraph.setMax(140);
        mBarGraph.setHorizentalLineNum(5);
        mBarGraph.setMaxLength("140");
        mBarGraph.setBarWidth(40);
    }
}
