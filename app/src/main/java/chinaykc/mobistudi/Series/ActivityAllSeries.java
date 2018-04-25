package chinaykc.mobistudi.Series;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import chinaykc.mobistudi.R;

/**
 * @author ykc
 * 系列->数据结构->数据结构所有的系列
 */
public class ActivityAllSeries extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private Series[] series = {
            new Series("马的哈密尔顿链问题",
                    "在一个8×8的方格棋盘中，按照国际象棋中马的行走规则从棋盘上的某一方格出发，开始在棋盘上周游，如果能不重复地走遍棋盘上的每一个方格，这样的一条周游路线在数学上被称为国际象棋盘上马的哈密尔顿链。",
                    8,234),

            new Series("约瑟夫环",
                    "编号为1到n的n个人围成一个圈，从第1个人开始报数，报到m时停止报数，报m的人出圈，再从他的下一个人起重新报数，报到m时停止报数，报m的圈，如此下去，直到所有人全部出圈为止",
                    3,987),

            new Series("八皇后问题",
                    "在国际象棋棋盘上，每行放置一个皇后，且在竖方向，斜方向都没有冲突。",
                    2,2354),

            new Series("背包问题",
                    "从N件价值与重量不同的物品中选取部分物品，使选中物品总量不超过限制重量且价值和最大。",
                    4,234),

            new Series("汉诺塔问题",
                    "神创造世界的时候做了三根金刚石柱子，在一根柱子上从下往上按照大小顺序摞着64片黄金圆盘。神命令婆罗门把圆盘从下面开始按大小顺序重新摆放在另一根柱子上。并且规定，在小圆盘上不能放大圆盘，在三根柱子之间一次只能移动一个圆盘。请问要移动多少次。",
                    1,4567),

            new Series("迷宫问题",
                    "给定一个迷宫,入口为左上角,出口为右下角,是否有路径从入口到出口,若有则输出该路径。",
                    4,4356),

            new Series("DNA排序",
                    "以逆序数的方法分类DNA字符串，排序程度从好到差。所有字符串长度相同。",
                    2,908),

            new Series("电话号码查询问题",
                    "编一个查询某个城市或单位的私人电话号码的程序。要求对任意给出的一个姓名，若该人有电话号码，则迅速找到其电话号码；否则指出该人没有电话号码。",
                    6,245),

            new Series("股票经纪人谣言",
                    "股票经纪人在一定时间内将消息传给其他人，请选择第一个传递谣言的人，以及所花费的时间",
                    1,34),

            new Series("翻转游戏",
                    "在4*4的正方形里放有拥有黑白两面的棋子。每轮翻转3-5个棋子，把它从白变黑或从黑变白。翻转的规则如下：1.选择任意一个棋子。2.翻转选择的棋子和与它相临的前后左右的棋子（如果有的话）。",
                    1,2345),

            new Series("黄金平衡",
                    "约翰使用一个二进制长度为K的整数来表示奶牛的特性，约翰把N头奶牛排成一排，发现在有些连续区间里的奶牛，每种特性出现的次数是一样的，找出区间的最大长度",
                    2,45),

            new Series("有色木棍",
                    "给出n根木棍，每根木棍两端都有颜色，颜色为小于10的字符串，问能否将所有的木棍排成一排，使得每两根木棍衔接的地方颜色相同。",
                    1,67),

            new Series("差分约束系统",
                    "一个系统由n个变量和m个约束条件。约束条件形如xj-xi<=bk，其中i,j取值为1到n之间整数,k取值为1到m之间整数,则称其为差分约束系统",
                    1,435),

            new Series("免费馅饼",
                    "某一段时间内，馅饼落在0-10这11个位置。小明站在5这个位置，因此在第一秒，他只能接到4,5,6这三个位置中其中一个位置上的馅饼。问他最多可接到多少个馅饼",
                    5,280),

            new Series("考新郎",
                    "N个新娘打扮一样，N个新郎各自寻找新娘，每人只找一个且不能多人找一个，M个新郎找错，求找错的次数。",
                    4,28),

            new Series("糖果大战",
                    "两人玩24点，赢一局可以拿走对方一颗糖，直到拿完为止。一人能算出而对方不能则算赢。若双方都算出或者都不能则算平局，不拿糖果",
                    2,345),

            new Series("消防车问题",
                    "失火时，中央调度站向火警所在地消防站提供从消防站到火警地的可能路线列表",
                    5,45276247),

            new Series("最大连续子序列",
                    "K个整数的序列，其任意连续子序列可表示为{Ni,Ni+1,...,Nj}求其所有连续子序列中元素和最大的一个。",
                    3,4256),

            new Series("畅通工程",
                    "全省任两城镇都实现交通(只要互相间接通过道路可达即可)。问最少还需要建设多少条道路",
                    3,4554334),

            new Series("亲和数",
                    "两个数中任何一个数都是另一个数的真约数之和，则这两个数就是亲和数。编写一个程序，判断给定的两个数是否是亲和数。",
                    2,234),

            new Series("合并石子",
                    "n堆石子，现将石子有序地合并成一堆。每次只取相邻的两堆合并成新的一堆,并将新一堆的石子数,记为该次合并得分。",
                    1,67899),

            new Series("找零钱问题",
                    "用数目不限的25美分，10美分，5美分及1美分的零钱找67美分的钱，选择一个方案使得钱币数最少。",
                    2,345678),

            new Series("最接近点对问题",
                    "给定平面上n个点，找其中的一对点，使得在n个点的所有点对中，该点对的距离最小。",
                    2,34567),

            new Series("中缀表达式",
                    "操作符以中缀形式处于操作数中间(例:3+4),给一个前/后缀表达式请转成中缀表达式",
                    3,87654),

            new Series("银行家算法",
                    "系统进行资源分配之前，先计算此次分配安全性，若不会导致系统进入不安全状态，则分配，否则就等待",
                    3,34),

            new Series("生产者消费者问题",
                    "两个共享固定大小缓冲区的线程,保证生产者不会在缓冲区满时加入数据，消费者也不会在缓冲区中空时消耗数据",
                    2,345),

            new Series("哲学家就餐问题",
                    "五个哲学家围坐在一起，每两人之间放一只筷子。每个哲学家必须拿到两只筷子，并且只能直接从自己的左边或右边去取筷子。",
                    4,5665),

            new Series("Belady异常",
                    "采用FIFO置换算法，出现分配页面数增多但缺页率反而提高的现象",
                    2,567),

            new Series("元素出栈",
                    "有ABCD四个元素依次进栈，并且以各种顺序出栈，求出栈序列排列方式的个数",
                    2,457)
    };

    private List<Series> seriesList = new ArrayList<>();
    private SeriesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_series);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("所有系列");
        initSeries();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_series_recycler_view);
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SeriesAdapter(seriesList);
        recyclerView.setAdapter(adapter);
    }

    private void initSeries() {
        seriesList.clear();
        for (int i = 0; i < series.length; i++) {
            seriesList.add(series[i]);
        }
    }
}
